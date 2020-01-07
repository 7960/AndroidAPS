package info.nightscout.androidaps.utils.textValidator

import android.R
import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.TypedArrayUtils
import androidx.preference.EditTextPreference

class ValidatingEditTextPreference(private val ctx: Context, val attrs: AttributeSet, private val defStyleAttr: Int, private val defStyleRes: Int)
    : EditTextPreference(ctx, attrs, defStyleAttr, defStyleRes) {

    constructor(ctx: Context, attrs: AttributeSet, defStyle: Int)
        : this(ctx, attrs, defStyle, 0)

    constructor(ctx: Context, attrs: AttributeSet)
        : this(ctx, attrs, TypedArrayUtils.getAttr(ctx, R.attr.editTextPreferenceStyle,
        R.attr.editTextPreferenceStyle))

    lateinit var editTextValidator: EditTextValidator

    override fun setOnBindEditTextListener(onBindEditTextListener: OnBindEditTextListener?) {
        super.setOnBindEditTextListener { editText ->
            editTextValidator = DefaultEditTextValidator(editText, attrs, context)

        }
    }
}