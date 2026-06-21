package com.kardeiro.library.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.kardeiro.library.R
import com.kardeiro.library.util.KardeiroDisplay

/**
 * KardeiroButton — a warm, rounded button with four styles:
 *   - FILLED   : terracotta primary fill
 *   - TONAL    : warm cream surface
 *   - OUTLINED : transparent with warm outline
 *   - TEXT     : text only
 *
 * Supports leading/trailing icons and a built-in loading spinner.
 *
 * XML example:
 * ```xml
 * <com.kardeiro.library.views.KardeiroButton
 *     android:layout_width="wrap_content"
 *     android:layout_height="52dp"
 *     app:kardeiro_buttonStyle="filled"
 *     app:kardeiro_buttonRadius="14dp"
 *     app:kardeiro_iconSrc="@drawable/ic_check"
 *     app:kardeiro_iconGravity="start"
 *     android:text="Confirmar" />
 * ```
 */
class KardeiroButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    enum class Style { FILLED, TONAL, OUTLINED, TEXT }
    enum class IconGravity { START, END, TOP, BOTTOM }

    private val textView: TextView
    private val iconView: AppCompatImageView
    private val progressView: ProgressBar

    private var style: Style = Style.FILLED
    private var iconGravity: IconGravity = IconGravity.START
    private var iconSizePx: Int = KardeiroDisplay.dpToPx(22)
    private var radiusPx: Float = KardeiroDisplay.dpToPx(14f)
    private var defaultText: CharSequence = ""

    init {
        isClickable = true
        isFocusable = true

        LayoutInflater.from(context).inflate(R.layout.kardeiro_button_content, this, true)

        textView = findViewById(R.id.kardeiro_button_text)
        iconView = findViewById(R.id.kardeiro_button_icon)
        progressView = findViewById(R.id.kardeiro_button_progress)
        progressView.visibility = View.GONE

        minimumWidth = KardeiroDisplay.dpToPx(120)
        minimumHeight = KardeiroDisplay.dpToPx(52)

        attrs?.let { applyAttributes(it) }

        applyStyle()
    }

    private fun applyAttributes(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.KardeiroButton, 0, 0)
        try {
            val styleIdx = a.getInt(R.styleable.KardeiroButton_kardeiro_buttonStyle, 0)
            style = Style.values().getOrElse(styleIdx) { Style.FILLED }

            radiusPx = a.getDimension(
                R.styleable.KardeiroButton_kardeiro_buttonRadius,
                KardeiroDisplay.dpToPx(14f)
            )
            iconGravity = IconGravity.values()
                .getOrElse(a.getInt(R.styleable.KardeiroButton_kardeiro_iconGravity, 0)) {
                    IconGravity.START
                }
            iconSizePx = a.getDimension(
                R.styleable.KardeiroButton_kardeiro_iconSize,
                KardeiroDisplay.dpToPx(22f)
            ).toInt()

            val iconRes = a.getResourceId(R.styleable.KardeiroButton_kardeiro_iconSrc, 0)
            if (iconRes != 0) setIcon(iconRes)

            val iconTint = a.getColor(
                R.styleable.KardeiroButton_kardeiro_iconTint, 0
            )
            if (iconTint != 0) iconView.setColorFilter(iconTint)

            defaultText = a.getString(R.styleable.KardeiroButton_android_text).orEmpty()
            textView.text = defaultText
            textView.textSize = KardeiroDisplay.pxToDp(KardeiroDisplay.spToPx(15f))
            textView.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textView.gravity = Gravity.CENTER

            val loading = a.getBoolean(R.styleable.KardeiroButton_kardeiro_loading, false)
            setLoading(loading)
        } finally {
            a.recycle()
        }
    }

    private fun applyStyle() {
        val bgRes = when (style) {
            Style.FILLED -> R.drawable.kardeiro_button_filled_background
            Style.TONAL -> R.drawable.kardeiro_button_tonal_background
            Style.OUTLINED -> R.drawable.kardeiro_button_outlined_background
            Style.TEXT -> 0
        }
        if (bgRes != 0) {
            background = ContextCompat.getDrawable(context, bgRes)
        } else {
            background = null
        }

        val textColor = when (style) {
            Style.FILLED -> ContextCompat.getColor(context, R.color.kardeiro_on_primary)
            Style.TONAL -> ContextCompat.getColor(context, R.color.kardeiro_primary_dark)
            Style.OUTLINED -> ContextCompat.getColor(context, R.color.kardeiro_primary)
            Style.TEXT -> ContextCompat.getColor(context, R.color.kardeiro_primary)
        }
        textView.setTextColor(textColor)

        val padding = KardeiroDisplay.dpToPx(24)
        setPadding(padding, padding / 2, padding, padding / 2)
    }

    fun setStyle(s: Style) {
        style = s
        applyStyle()
        invalidate()
    }

    fun setIcon(@DrawableRes res: Int) {
        iconView.setImageResource(res)
        iconView.visibility = VISIBLE
    }

    fun setText(text: CharSequence) {
        defaultText = text
        textView.text = text
    }

    fun setLoading(loading: Boolean) {
        progressView.visibility = if (loading) VISIBLE else GONE
        textView.visibility = if (loading) INVISIBLE else VISIBLE
        isClickable = !loading
    }
}
