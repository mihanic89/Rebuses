package xyz.yapapa.rebuses;

import androidx.annotation.DrawableRes;

/** A single rebus: the picture and the answer to it. */
public record Rebus(@DrawableRes int imageRes, String answer) {
}
