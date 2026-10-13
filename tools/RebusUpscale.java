import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

/**
 * Upscales flat-colour rebus art: separates ink from the background (per-pixel ink colour +
 * coverage), upsamples the coverage with Catmull-Rom, re-sharpens edges and writes the ink on
 * a transparent background.
 *
 * Usage: java RebusUpscale.java <in.png> <out.png> <targetWidth>
 */
public class RebusUpscale {

    public static void main(String[] a) throws Exception {
        BufferedImage src = ImageIO.read(new File(a[0]));
        int target = Integer.parseInt(a[2]);
        int w = src.getWidth(), h = src.getHeight();

        float[][] px = new float[w * h][];
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                int c = src.getRGB(x, y);
                px[y * w + x] = new float[]{(c >> 16) & 255, (c >> 8) & 255, c & 255};
            }
        float[] bg = borderMedian(px, w, h);
        float[][] palette = palette(px, bg);

        // Ink colour: the palette colour closest to the most saturated pixel nearby.
        float[][] ink = new float[w * h][];
        float[] alpha = new float[w * h];
        int r = 2;
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                float best = -1;
                float[] bc = null;
                for (int dy = -r; dy <= r; dy++)
                    for (int dx = -r; dx <= r; dx++) {
                        int xx = Math.max(0, Math.min(w - 1, x + dx)), yy = Math.max(0, Math.min(h - 1, y + dy));
                        float[] c = px[yy * w + xx];
                        float d = dist2(c, bg);
                        if (d > best) { best = d; bc = c; }
                    }
                float[] c = px[y * w + x];
                if (dist2(bc, bg) < 30 * 30) { // only background around: no ink here
                    ink[y * w + x] = bg;
                    alpha[y * w + x] = 0;
                    continue;
                }
                float[] pc = nearest(palette, bc);
                float den = dist2(pc, bg);
                float dot = 0;
                for (int k = 0; k < 3; k++) dot += (c[k] - bg[k]) * (pc[k] - bg[k]);
                ink[y * w + x] = pc;
                // Drop the faint JPEG noise floor around strokes.
                alpha[y * w + x] = clamp((dot / den - 0.15f) / 0.85f);
            }
        alpha = blur(alpha, w, h, 0.5);
        alpha = boostThinLines(alpha, w, h);

        double s = (double) target / w;
        int ow = target, oh = (int) Math.round(h * s);
        BufferedImage out = new BufferedImage(ow, oh, BufferedImage.TYPE_INT_ARGB);
        // Edge transition about 1.2 output pixels wide; coverage ramps over ~s pixels after upsampling.
        float half = (float) Math.min(0.5, 0.6 / s * 1.0 + 0.08);
        float mid = 0.55f;
        for (int y = 0; y < oh; y++)
            for (int x = 0; x < ow; x++) {
                double sx = (x + 0.5) / s - 0.5, sy = (y + 0.5) / s - 0.5;
                float av = (float) bicubic(alpha, w, h, sx, sy);
                float aa = smoothstep(mid - half, mid + half, av);
                if (aa <= 0) { out.setRGB(x, y, 0); continue; }
                float[] ic = ink[clampI((int) Math.round(sy), h) * w + clampI((int) Math.round(sx), w)];
                // Average ink colour of the 2x2 neighbourhood to avoid blocky colour seams.
                float[] col = new float[3];
                int n = 0;
                for (int dy = 0; dy <= 1; dy++)
                    for (int dx = 0; dx <= 1; dx++) {
                        int xx = clampI((int) Math.floor(sx) + dx, w), yy = clampI((int) Math.floor(sy) + dy, h);
                        if (alpha[yy * w + xx] > 0.05f) {
                            float[] c = ink[yy * w + xx];
                            for (int k = 0; k < 3; k++) col[k] += c[k];
                            n++;
                        }
                    }
                if (n == 0) col = ic; else for (int k = 0; k < 3; k++) col[k] /= n;
                int A = Math.round(aa * 255);
                out.setRGB(x, y, (A << 24) | (b(col[0]) << 16) | (b(col[1]) << 8) | b(col[2]));
            }
        ImageIO.write(out, "png", new File(a[1]));
    }

    /** Dominant ink colours: k-means over clearly non-background pixels, close centres merged. */
    static float[][] palette(float[][] px, float[] bg) {
        java.util.List<float[]> solid = new java.util.ArrayList<>();
        float max = 0;
        for (float[] c : px) max = Math.max(max, dist2(c, bg));
        for (float[] c : px) if (dist2(c, bg) > 0.35f * max) solid.add(c);
        int k = Math.min(8, solid.size());
        float[][] cen = new float[k][];
        for (int i = 0; i < k; i++) cen[i] = solid.get(i * solid.size() / k).clone();
        for (int it = 0; it < 15; it++) {
            float[][] sum = new float[k][3];
            int[] cnt = new int[k];
            for (float[] c : solid) {
                int j = nearestIdx(cen, c);
                for (int q = 0; q < 3; q++) sum[j][q] += c[q];
                cnt[j]++;
            }
            for (int j = 0; j < k; j++) if (cnt[j] > 0) for (int q = 0; q < 3; q++) cen[j][q] = sum[j][q] / cnt[j];
        }
        int[] cnt = new int[k];
        for (float[] c : solid) cnt[nearestIdx(cen, c)]++;
        Integer[] order = new Integer[k];
        for (int i = 0; i < k; i++) order[i] = i;
        Arrays.sort(order, (x, y) -> cnt[y] - cnt[x]);
        // Most common colours first; skip rare clusters, near-duplicates, and edge blends
        // (colours lying on the line between the background and a kept ink colour).
        java.util.List<float[]> out = new java.util.ArrayList<>();
        for (int idx : order) {
            float[] c = cen[idx];
            if (!out.isEmpty() && cnt[idx] < 0.03 * solid.size()) continue;
            boolean skip = false;
            for (float[] o : out) {
                if (dist2(o, c) < 60 * 60 || isBlend(c, o, bg)) { skip = true; break; }
            }
            if (!skip) out.add(c);
        }
        return out.toArray(new float[0][]);
    }

    static boolean isBlend(float[] c, float[] ink, float[] bg) {
        float den = dist2(ink, bg), dot = 0;
        for (int q = 0; q < 3; q++) dot += (c[q] - bg[q]) * (ink[q] - bg[q]);
        float t = dot / den;
        if (t < 0.15f || t > 0.95f) return false;
        float res = 0;
        for (int q = 0; q < 3; q++) { float p = bg[q] + t * (ink[q] - bg[q]); res += (c[q] - p) * (c[q] - p); }
        return res < 30 * 30;
    }

    static int nearestIdx(float[][] p, float[] c) {
        int bi = 0;
        float bd = Float.MAX_VALUE;
        for (int i = 0; i < p.length; i++) { float d = dist2(p[i], c); if (d < bd) { bd = d; bi = i; } }
        return bi;
    }

    static float[] nearest(float[][] p, float[] c) { return p[nearestIdx(p, c)]; }

    /**
     * Thin or pale strokes never reach full coverage, so a fixed threshold would erase them.
     * Normalise coverage by the local 3x3 maximum wherever a real stroke is present.
     */
    static float[] boostThinLines(float[] a, int w, int h) {
        float[] o = new float[a.length];
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                float m = 0;
                for (int dy = -1; dy <= 1; dy++)
                    for (int dx = -1; dx <= 1; dx++)
                        m = Math.max(m, a[clampI(y + dy, h) * w + clampI(x + dx, w)]);
                float v = a[y * w + x];
                o[y * w + x] = m >= 0.15f ? Math.min(1f, v / m) : v;
            }
        return o;
    }

    static float[] blur(float[] v, int w, int h, double sigma) {
        int r = (int) Math.ceil(sigma * 2.5);
        double[] k = new double[2 * r + 1];
        double s = 0;
        for (int i = -r; i <= r; i++) s += k[i + r] = Math.exp(-i * i / (2 * sigma * sigma));
        for (int i = 0; i < k.length; i++) k[i] /= s;
        float[] t = new float[v.length], o = new float[v.length];
        for (int y = 0; y < h; y++) for (int x = 0; x < w; x++) {
            double a = 0; for (int i = -r; i <= r; i++) a += k[i + r] * v[y * w + clampI(x + i, w)]; t[y * w + x] = (float) a; }
        for (int y = 0; y < h; y++) for (int x = 0; x < w; x++) {
            double a = 0; for (int i = -r; i <= r; i++) a += k[i + r] * t[clampI(y + i, h) * w + x]; o[y * w + x] = (float) a; }
        return o;
    }

    static float[] borderMedian(float[][] px, int w, int h) {
        int n = 2 * (w + h);
        float[][] ch = new float[3][n];
        int i = 0;
        for (int x = 0; x < w; x++) { put(ch, i++, px[x]); put(ch, i++, px[(h - 1) * w + x]); }
        for (int y = 0; y < h; y++) { put(ch, i++, px[y * w]); put(ch, i++, px[y * w + w - 1]); }
        float[] m = new float[3];
        for (int k = 0; k < 3; k++) { Arrays.sort(ch[k], 0, i); m[k] = ch[k][i / 2]; }
        return m;
    }

    static void put(float[][] ch, int i, float[] c) { for (int k = 0; k < 3; k++) ch[k][i] = c[k]; }

    static float dist2(float[] a, float[] b) {
        float s = 0;
        for (int k = 0; k < 3; k++) s += (a[k] - b[k]) * (a[k] - b[k]);
        return s;
    }

    static double bicubic(float[] v, int w, int h, double x, double y) {
        int x0 = (int) Math.floor(x), y0 = (int) Math.floor(y);
        double fx = x - x0, fy = y - y0, sum = 0;
        for (int j = -1; j <= 2; j++) {
            double wy = cr(j - fy);
            for (int i = -1; i <= 2; i++)
                sum += wy * cr(i - fx) * v[clampI(y0 + j, h) * w + clampI(x0 + i, w)];
        }
        return sum;
    }

    // Catmull-Rom kernel
    static double cr(double t) {
        t = Math.abs(t);
        if (t < 1) return 1.5 * t * t * t - 2.5 * t * t + 1;
        if (t < 2) return -0.5 * t * t * t + 2.5 * t * t - 4 * t + 2;
        return 0;
    }

    static float smoothstep(float e0, float e1, float x) {
        float t = clamp((x - e0) / (e1 - e0));
        return t * t * (3 - 2 * t);
    }

    static float clamp(float v) { return Math.max(0, Math.min(1, v)); }

    static int clampI(int v, int n) { return Math.max(0, Math.min(n - 1, v)); }

    static int b(float v) { return Math.max(0, Math.min(255, Math.round(v))); }
}
