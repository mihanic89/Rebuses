package xyz.yapapa.rebuses;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Source of all rebuses. The data lives in res/values/rebuses.xml; screens refer to a rebus
 * by its index in the list, never by a resource ID, because resource IDs change between builds.
 * Nothing is cached: reading two short arrays is cheap, and it always matches the current
 * configuration (e.g. a localized rebuses.xml).
 */
public final class RebusRepository {

    private RebusRepository() {
    }

    public static List<Rebus> getAll(Context context) {
        Resources resources = context.getResources();
        String[] answers = resources.getStringArray(R.array.rebus_answers);
        TypedArray images = resources.obtainTypedArray(R.array.rebus_images);
        try {
            if (images.length() != answers.length) {
                throw new IllegalStateException("rebus_images has " + images.length()
                        + " items but rebus_answers has " + answers.length);
            }
            List<Rebus> list = new ArrayList<>(answers.length);
            for (int i = 0; i < answers.length; i++) {
                list.add(new Rebus(images.getResourceId(i, 0), answers[i]));
            }
            return Collections.unmodifiableList(list);
        } finally {
            images.recycle();
        }
    }

    /** Returns the rebus at {@code index}, or null if there is no such rebus. */
    public static Rebus get(Context context, int index) {
        List<Rebus> all = getAll(context);
        return index >= 0 && index < all.size() ? all.get(index) : null;
    }
}
