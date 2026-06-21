package com.kardeiro.library.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue

/**
 * Density / size helpers used across Kardeiro views.
 */
object KardeiroDisplay {

    fun dpToPx(dp: Float): Float =
        dp * Resources.getSystem().displayMetrics.density

    fun dpToPx(dp: Int): Int = dpToPx(dp.toFloat()).toInt()

    fun pxToDp(px: Float): Float =
        px / Resources.getSystem().displayMetrics.density

    fun spToPx(sp: Float): Float =
        sp * Resources.getSystem().displayMetrics.scaledDensity

    /**
     * Resolve a themed color from an attribute reference, falling back to [fallback].
     */
    fun resolveColor(context: Context, attrId: Int, @androidx.annotation.ColorInt fallback: Int): Int {
        val tv = TypedValue()
        return if (context.theme.resolveAttribute(attrId, tv, true)) {
            tv.resourceId.takeIf { it != 0 }
                ?.let { androidx.core.content.ContextCompat.getColor(context, it) }
                ?: tv.data
        } else fallback
    }

    /**
     * Blend two colors. [ratio] = 0 -> [c1], 1 -> [c2].
     */
    fun blend(c1: Int, c2: Int, ratio: Float): Int {
        val r = ratio.coerceIn(0f, 1f)
        val rr = (Color.red(c1) * (1 - r) + Color.red(c2) * r).toInt()
        val gg = (Color.green(c1) * (1 - r) + Color.green(c2) * r).toInt()
        val bb = (Color.blue(c1) * (1 - r) + Color.blue(c2) * r).toInt()
        val aa = (Color.alpha(c1) * (1 - r) + Color.alpha(c2) * r).toInt()
        return Color.argb(aa, rr, gg, bb)
    }
}
