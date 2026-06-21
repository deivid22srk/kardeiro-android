package com.kardeiro.library.theme

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.kardeiro.library.R

/**
 * Central definition of the Kardeiro visual language.
 *
 * Kardeiro is a warm, modern Android layout style inspired by terracotta,
 * amber, sand, and cream tones. It emphasizes:
 *   - Soft rounded corners (16dp default)
 *   - Gentle elevation with warm shadow tints
 *   - Cream backgrounds with terracotta accents
 *   - Generous spacing on an 8dp grid
 *
 * Use [KardeiroTheme] from any module that depends on the library to access
 * the canonical colors, dimensions, and helpers that define the look.
 */
object KardeiroTheme {

    @ColorInt
    fun primary(context: Context): Int =
        ContextCompat.getColor(context, R.color.kardeiro_primary)

    @ColorInt
    fun primaryDark(context: Context): Int =
        ContextCompat.getColor(context, R.color.kardeiro_primary_dark)

    @ColorInt
    fun secondary(context: Context): Int =
        ContextCompat.getColor(context, R.color.kardeiro_secondary)

    @ColorInt
    fun accent(context: Context): Int =
        ContextCompat.getColor(context, R.color.kardeiro_accent)

    @ColorInt
    fun background(context: Context): Int =
        ContextCompat.getColor(context, R.color.kardeiro_background)

    @ColorInt
    fun surface(context: Context): Int =
        ContextCompat.getColor(context, R.color.kardeiro_surface)

    @ColorInt
    fun onSurface(context: Context): Int =
        ContextCompat.getColor(context, R.color.kardeiro_on_surface)

    @ColorInt
    fun outline(context: Context): Int =
        ContextCompat.getColor(context, R.color.kardeiro_outline)

    /**
     * Lighten a color by [amount] (0..1).
     */
    @ColorInt
    fun lighten(@ColorInt color: Int, amount: Float): Int {
        val a = amount.coerceIn(0f, 1f)
        val r = Color.red(color) + ((255 - Color.red(color)) * a).toInt()
        val g = Color.green(color) + ((255 - Color.green(color)) * a).toInt()
        val b = Color.blue(color) + ((255 - Color.blue(color)) * a).toInt()
        return Color.rgb(r, g, b)
    }

    /**
     * Darken a color by [amount] (0..1).
     */
    @ColorInt
    fun darken(@ColorInt color: Int, amount: Float): Int {
        val a = amount.coerceIn(0f, 1f)
        val r = (Color.red(color) * (1 - a)).toInt()
        val g = (Color.green(color) * (1 - a)).toInt()
        val b = (Color.blue(color) * (1 - a)).toInt()
        return Color.rgb(r, g, b)
    }
}
