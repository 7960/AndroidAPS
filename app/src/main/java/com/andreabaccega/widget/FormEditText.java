package com.andreabaccega.widget;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.appcompat.widget.AppCompatEditText;

import com.andreabaccega.formedittextvalidator.Validator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * EditText Extension to be used in order to create forms in android.
 *
 * @author Andrea Baccega <me@andreabaccega.com>
 */
public class FormEditText extends AppCompatEditText {
    public FormEditText(Context context) {
        super(context);
        //support dynamic new FormEditText(context)
        editTextValidator = new DefaultEditTextValidator(this, context);
    }

    public FormEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        editTextValidator = new DefaultEditTextValidator(this, attrs, context);
    }

    public FormEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        editTextValidator = new DefaultEditTextValidator(this, attrs, context);

    }

    /**
     * Add a validator to this FormEditText. The validator will be added in the
     * queue of the current validators.
     *
     * @param theValidator
     * @throws IllegalArgumentException if the validator is null
     */
    public void addValidator(Validator theValidator) throws IllegalArgumentException {
        editTextValidator.addValidator(theValidator);
    }

    public EditTextValidator getEditTextValidator() {
        return editTextValidator;
    }

    public void setEditTextValidator(EditTextValidator editTextValidator) {
        this.editTextValidator = editTextValidator;
    }

    /**
     * Calling *testValidity()* will cause the EditText to go through
     * customValidators and call {@link com.andreabaccega.formedittextvalidator.Validator#isValid(EditText)}
     *
     * @return true if the validity passes false otherwise.
     */
    public boolean testValidity() {
        return editTextValidator.testValidity();
    }

    private EditTextValidator editTextValidator;


    /**
     * Keep track of which icon we used last
     */
    private Drawable lastErrorIcon = null;

    /**
     * Don't send delete key so edit text doesn't capture it and close error
     */
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (TextUtils.isEmpty(getText().toString())
                && keyCode == KeyEvent.KEYCODE_DEL)
            return true;
        else
            return super.onKeyPreIme(keyCode, event);
    }

}
