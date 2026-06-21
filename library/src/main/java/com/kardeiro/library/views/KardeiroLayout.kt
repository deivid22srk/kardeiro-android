package com.kardeiro.library.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.kardeiro.library.R
import com.kardeiro.library.util.KardeiroDisplay

/**
 * KardeiroLayout — a LinearLayout-style container with built-in,
 * uniform spacing between children and configurable insets.
 *
 * Kardeiro encourages consistent 8dp-grid spacing, so this view automatically
 * applies [kardeiro_spacing] between consecutive children without requiring
 * each child to declare its own margins.
 *
 * XML example:
 * ```xml
 * <com.kardeiro.library.views.KardeiroLayout
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:kardeiro_layoutType="linear"
 *     app:kardeiro_orientation="vertical"
 *     app:kardeiro_spacing="16dp"
 *     app:kardeiro_insetStart="16dp"
 *     app:kardeiro_insetEnd="16dp"
 *     app:kardeiro_insetTop="16dp"
 *     app:kardeiro_insetBottom="16dp">
 *
 *     <!-- children -->
 * </com.kardeiro.library.views.KardeiroLayout>
 * ```
 */
class KardeiroLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var spacingPx: Int = KardeiroDisplay.dpToPx(8)

    init {
        orientation = VERTICAL
        attrs?.let { applyAttributes(it) }
    }

    private fun applyAttributes(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.KardeiroLayout, 0, 0)
        try {
            val typeIdx = a.getInt(R.styleable.KardeiroLayout_kardeiro_layoutType, 0)
            // type 0 = linear (we keep LinearLayout implementation).
            // type 1 (frame) and 2 (relative) still render as linear in this version,
            // but the API is reserved for future expansion.
            typeIdx // keep for clarity

            orientation = a.getInt(
                R.styleable.KardeiroLayout_kardeiro_orientation, VERTICAL
            )

            spacingPx = a.getDimension(
                R.styleable.KardeiroLayout_kardeiro_spacing,
                KardeiroDisplay.dpToPx(8f)
            ).toInt()

            val insetStart = a.getDimension(
                R.styleable.KardeiroLayout_kardeiro_insetStart, 0f
            ).toInt()
            val insetEnd = a.getDimension(
                R.styleable.KardeiroLayout_kardeiro_insetEnd, 0f
            ).toInt()
            val insetTop = a.getDimension(
                R.styleable.KardeiroLayout_kardeiro_insetTop, 0f
            ).toInt()
            val insetBottom = a.getDimension(
                R.styleable.KardeiroLayout_kardeiro_insetBottom, 0f
            ).toInt()
            setPadding(
                paddingLeft + insetStart,
                paddingTop + insetTop,
                paddingRight + insetEnd,
                paddingBottom + insetBottom
            )
        } finally {
            a.recycle()
        }
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        applySpacingTo(child)
    }

    private fun applySpacingTo(child: View?) {
        child ?: return
        val lp = child.layoutParams as? LinearLayout.LayoutParams ?: return
        if (orientation == VERTICAL) {
            if (lp.topMargin == 0) lp.topMargin = spacingPx
        } else {
            if (lp.leftMargin == 0) lp.leftMargin = spacingPx
        }
        child.layoutParams = lp
    }
}
