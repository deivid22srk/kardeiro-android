package com.kardeiro.library.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.kardeiro.library.R
import com.kardeiro.library.util.KardeiroDisplay

/**
 * KardeiroBadge — a small rounded label used for counts / status tags.
 *
 * Three preset sizes: SMALL (16dp tall), MEDIUM (20dp), LARGE (24dp).
 *
 * XML example:
 * ```xml
 * <com.kardeiro.library.views.KardeiroBadge
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     app:kardeiro_badgeText="Novo"
 *     app:kardeiro_badgeSize="medium" />
 * ```
 */
class KardeiroBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    enum class Size { SMALL, MEDIUM, LARGE }

    init {
        setBackgroundResource(R.drawable.kardeiro_badge_background)
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        setTextColor(ContextCompat.getColor(context, R.color.kardeiro_on_secondary))
        gravity = android.view.Gravity.CENTER

        attrs?.let { applyAttributes(it) }
    }

    private fun applyAttributes(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.KardeiroBadge, 0, 0)
        try {
            text = a.getString(R.styleable.KardeiroBadge_kardeiro_badgeText).orEmpty()
            val sizeIdx = a.getInt(R.styleable.KardeiroBadge_kardeiro_badgeSize, 1)
            val height = when (Size.values().getOrElse(sizeIdx) { Size.MEDIUM }) {
                Size.SMALL -> KardeiroDisplay.dpToPx(16)
                Size.MEDIUM -> KardeiroDisplay.dpToPx(20)
                Size.LARGE -> KardeiroDisplay.dpToPx(24)
            }
            val hPad = KardeiroDisplay.dpToPx(8)
            val vPad = (height - KardeiroDisplay.dpToPx(12)) / 2
            setPadding(hPad, vPad, hPad, vPad)
            textSize = KardeiroDisplay.pxToDp(KardeiroDisplay.spToPx(11f))

            val bg = a.getColor(R.styleable.KardeiroBadge_kardeiro_badgeColor, 0)
            if (bg != 0) setBadgeColor(bg)
            val tc = a.getColor(R.styleable.KardeiroBadge_kardeiro_badgeTextColor, 0)
            if (tc != 0) setTextColor(tc)
        } finally {
            a.recycle()
        }
    }

    fun setBadgeColor(color: Int) {
        setBackgroundColor(color)
    }
}
