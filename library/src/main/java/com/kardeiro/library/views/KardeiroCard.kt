package com.kardeiro.library.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.withSave
import com.kardeiro.library.R
import com.kardeiro.library.util.KardeiroDisplay

/**
 * KardeiroCard — the centerpiece of the Kardeiro layout style.
 *
 * Renders a warm-toned, rounded card with:
 *   - Configurable corner radius (default 16dp)
 *   - Soft, warm-tinted shadow (drawn manually so it works on every API level
 *     without elevation requirements)
 *   - Optional 1dp outline in the Kardeiro outline color
 *   - Optional ripple feedback for clickable cards
 *
 * Use it as a regular container — it extends [View] and lays out children
 * like a FrameLayout-style container via the superclass.
 *
 * XML example:
 * ```xml
 * <com.kardeiro.library.views.KardeiroCard
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:kardeiro_cornerRadius="20dp"
 *     app:kardeiro_elevation="8dp"
 *     app:kardeiro_borderWidth="1dp"
 *     app:kardeiro_shadowEnabled="true">
 *
 *     <!-- child views -->
 * </com.kardeiro.library.views.KardeiroCard>
 * ```
 */
class KardeiroCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE }
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }

    private val rect = RectF()
    private val clipPath = Path()

    private var cornerRadius: Float = KardeiroDisplay.dpToPx(16f)
    private var shadowRadius: Float = KardeiroDisplay.dpToPx(12f)
    private var shadowEnabled: Boolean = true
    private var borderWidth: Float = KardeiroDisplay.dpToPx(1f)
    private var borderColor: Int = ContextCompat.getColor(context, R.color.kardeiro_outline_variant)
    private var backgroundColorKardeiro: Int = ContextCompat.getColor(context, R.color.kardeiro_surface)

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.KardeiroCard, 0, 0)
            try {
                cornerRadius = a.getDimension(
                    R.styleable.KardeiroCard_kardeiro_cornerRadius,
                    KardeiroDisplay.dpToPx(16f)
                )
                shadowRadius = a.getDimension(
                    R.styleable.KardeiroCard_kardeiro_shadowRadius,
                    KardeiroDisplay.dpToPx(12f)
                )
                shadowEnabled = a.getBoolean(
                    R.styleable.KardeiroCard_kardeiro_shadowEnabled, true
                )
                borderWidth = a.getDimension(
                    R.styleable.KardeiroCard_kardeiro_borderWidth,
                    KardeiroDisplay.dpToPx(1f)
                )
                borderColor = a.getColor(
                    R.styleable.KardeiroCard_kardeiro_borderColor, borderColor
                )
                backgroundColorKardeiro = a.getColor(
                    R.styleable.KardeiroCard_kardeiro_backgroundColor, backgroundColorKardeiro
                )
                val padding = a.getDimension(
                    R.styleable.KardeiroCard_kardeiro_padding, 0f
                ).toInt()
                if (padding > 0) {
                    setPadding(padding, padding, padding, padding)
                }
                val rippleEnabled = a.getBoolean(
                    R.styleable.KardeiroCard_kardeiro_rippleEnabled, false
                )
                if (rippleEnabled) {
                    isClickable = true
                    isFocusable = true
                }
            } finally {
                a.recycle()
            }
        }
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }

    fun setCardBackgroundColor(color: Int) {
        backgroundColorKardeiro = color
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rect.set(
            paddingLeft.toFloat(),
            paddingTop.toFloat(),
            (width - paddingRight).toFloat(),
            (height - paddingBottom).toFloat()
        )

        // Shadow
        if (shadowEnabled) {
            val shadowColor = ContextCompat.getColor(context, R.color.kardeiro_shadow)
            shadowPaint.color = shadowColor
            shadowPaint.maskFilter = android.graphics.BlurMaskFilter(
                shadowRadius, android.graphics.BlurMaskFilter.Blur.NORMAL
            )
            val shadowRect = RectF(
                rect.left,
                rect.top + KardeiroDisplay.dpToPx(2f),
                rect.right,
                rect.bottom + KardeiroDisplay.dpToPx(4f)
            )
            canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint)
        }

        // Background fill
        bgPaint.color = backgroundColorKardeiro
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, bgPaint)

        // Border
        if (borderWidth > 0f) {
            borderPaint.color = borderColor
            borderPaint.strokeWidth = borderWidth
            canvas.drawRoundRect(rect, cornerRadius, cornerRadius, borderPaint)
        }
    }
}
