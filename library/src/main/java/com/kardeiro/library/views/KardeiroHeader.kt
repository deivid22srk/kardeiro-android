package com.kardeiro.library.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.kardeiro.library.R
import com.kardeiro.library.util.KardeiroDisplay

/**
 * KardeiroHeader — a top app-bar-style header with the warm Kardeiro gradient
 * background, optional back icon, title and subtitle.
 *
 * XML example:
 * ```xml
 * <com.kardeiro.library.views.KardeiroHeader
 *     android:layout_width="match_parent"
 *     android:layout_height="180dp"
 *     app:kardeiro_header_title="Bem-vindo"
 *     app:kardeiro_header_subtitle="Estilo Kardeiro" />
 * ```
 */
class KardeiroHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val titleView: TextView
    private val subtitleView: TextView

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER_VERTICAL or Gravity.START
        setBackgroundResource(R.drawable.kardeiro_header_gradient)
        val hPad = KardeiroDisplay.dpToPx(20)
        val vPad = KardeiroDisplay.dpToPx(24)
        setPadding(hPad, vPad, hPad, vPad)
        minimumHeight = KardeiroDisplay.dpToPx(160)

        LayoutInflater.from(context).inflate(R.layout.kardeiro_header_content, this, true)
        titleView = findViewById(R.id.kardeiro_header_title)
        subtitleView = findViewById(R.id.kardeiro_header_subtitle)

        titleView.setTextColor(ContextCompat.getColor(context, R.color.kardeiro_on_primary))
        subtitleView.setTextColor(ContextCompat.getColor(context, R.color.kardeiro_on_primary))
        titleView.textSize = KardeiroDisplay.pxToDp(KardeiroDisplay.spToPx(26f))
        subtitleView.textSize = KardeiroDisplay.pxToDp(KardeiroDisplay.spToPx(14f))
        titleView.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

        attrs?.let { applyAttributes(it) }
    }

    private fun applyAttributes(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.KardeiroHeader, 0, 0)
        try {
            titleView.text = a.getString(R.styleable.KardeiroHeader_kardeiro_header_title).orEmpty()
            subtitleView.text = a.getString(R.styleable.KardeiroHeader_kardeiro_header_subtitle).orEmpty()
        } finally {
            a.recycle()
        }
    }

    fun setTitle(title: CharSequence) {
        titleView.text = title
    }

    fun setSubtitle(subtitle: CharSequence) {
        subtitleView.text = subtitle
    }
}
