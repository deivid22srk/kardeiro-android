package com.kardeiro.library.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.kardeiro.library.R
import com.kardeiro.library.util.KardeiroDisplay

/**
 * KardeiroTextField — a warm-toned, rounded text input with:
 *   - Floating-style label above
 *   - Optional leading and trailing icons
 *   - Helper / error text slot below
 *   - Rounded 14dp background in cream tone
 *
 * XML example:
 * ```xml
 * <com.kardeiro.library.views.KardeiroTextField
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:kardeiro_fieldLabel="E-mail"
 *     app:kardeiro_fieldHint="voce@exemplo.com"
 *     app:kardeiro_fieldHelperText="Nunca compartilhamos seu e-mail" />
 * ```
 */
class KardeiroTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val labelView: TextView
    private val inputView: EditText
    private val leadingIconView: AppCompatImageView
    private val trailingIconView: AppCompatImageView
    private val helperView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.kardeiro_field_content, this, true)
        labelView = findViewById(R.id.kardeiro_field_label)
        inputView = findViewById(R.id.kardeiro_field_input)
        leadingIconView = findViewById(R.id.kardeiro_field_leading)
        trailingIconView = findViewById(R.id.kardeiro_field_trailing)
        helperView = findViewById(R.id.kardeiro_field_helper)

        attrs?.let { applyAttributes(it) }
        applyDefaultStyling()
    }

    private fun applyAttributes(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.KardeiroTextField, 0, 0)
        try {
            labelView.text = a.getString(R.styleable.KardeiroTextField_kardeiro_fieldLabel).orEmpty()
            inputView.hint = a.getString(R.styleable.KardeiroTextField_kardeiro_fieldHint).orEmpty()
            helperView.text = a.getString(R.styleable.KardeiroTextField_kardeiro_fieldHelperText).orEmpty()
            val errorText = a.getString(R.styleable.KardeiroTextField_kardeiro_fieldErrorText).orEmpty()
            if (errorText.isNotEmpty()) setError(errorText)

            val leading = a.getResourceId(R.styleable.KardeiroTextField_kardeiro_fieldLeadingIcon, 0)
            if (leading != 0) {
                leadingIconView.setImageResource(leading)
                leadingIconView.visibility = VISIBLE
            }
            val trailing = a.getResourceId(R.styleable.KardeiroTextField_kardeiro_fieldTrailingIcon, 0)
            if (trailing != 0) {
                trailingIconView.setImageResource(trailing)
                trailingIconView.visibility = VISIBLE
            }
        } finally {
            a.recycle()
        }
    }

    private fun applyDefaultStyling() {
        setBackgroundResource(R.drawable.kardeiro_field_background)
        labelView.setTextColor(ContextCompat.getColor(context, R.color.kardeiro_secondary))
        labelView.textSize = KardeiroDisplay.pxToDp(KardeiroDisplay.spToPx(13f))
        labelView.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        inputView.background = null
        inputView.textSize = KardeiroDisplay.pxToDp(KardeiroDisplay.spToPx(15f))
        inputView.setTextColor(ContextCompat.getColor(context, R.color.kardeiro_on_surface))
        inputView.setHintTextColor(ContextCompat.getColor(context, R.color.kardeiro_on_surface_variant))
        helperView.textSize = KardeiroDisplay.pxToDp(KardeiroDisplay.spToPx(12f))
        helperView.setTextColor(ContextCompat.getColor(context, R.color.kardeiro_on_surface_variant))
    }

    fun getText(): String = inputView.text?.toString().orEmpty()

    fun setText(text: CharSequence) {
        inputView.setText(text)
    }

    fun setError(message: CharSequence) {
        helperView.text = message
        helperView.setTextColor(ContextCompat.getColor(context, R.color.kardeiro_error))
        labelView.setTextColor(ContextCompat.getColor(context, R.color.kardeiro_error))
    }

    fun clearError() {
        labelView.setTextColor(ContextCompat.getColor(context, R.color.kardeiro_secondary))
        helperView.text = ""
    }
}
