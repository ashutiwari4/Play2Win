package ashutosh.bdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.regex.Pattern;


/**
 * Created by Ashutosh on 30/1/17.
 */
public class MEditText extends TextInputEditText {
    final static public int TEXT_NOT_EMPTY = 1;
    final static public int TEXT_NOT_ZERO = 2;
    final static public int TEXT_TEMPRATURE_RANGE_FAHRENHEIT = 4;
    final static public int TEXT_TEMPRATURE_RANGE_CELCIOUS = 8;
    final static public int TEXT_EMAIL = 16;
    final static public int TEXT_VALID_USER_NAME = 32;
    final static public int TEXT_MIN_MIN_LENGTH = 64;
    final static public int TEXT_NULLABLE = 128;
    final static public int TEXT_MOBILE = 256;
    final static public int TEXT_WHITE_SPACE = 512;
    final static public float MAX_FAHRENHEIT = 107f;
    final static public int MAX_CELCIOUS = 42;
    final static public int MIN_FAHRENHEIT = 93;
    final static public int MIN_CELCIOUS = 34;

    final static public int ERROR_TYPE_TOAST = 1;
    final static public int ERROR_TYPE_RETURN = 0;
    final static public int ERROR_TYPE_SET = 2;
    final static public int ERROR_TYPE_SET_TO_LAYOUT = 3;
    boolean seteror;
    int errortype = 0;
    private int validations = 0;
    private String flagName = "";
    private int textminLength = 5;
    private String errorMessage = "";
    private Typeface mTypeface;

    public MEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public MEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MEditText(Context context) {
        super(context);
    }

    public static boolean validateEditText(View v) {

        boolean validation = true;

        if (v instanceof ViewGroup) {
            int count = ((ViewGroup) v).getChildCount();
            for (int i = 0; i < count; i++) {
                if (!validateEditText(((ViewGroup) v).getChildAt(i))) return false;
            }
        } else if (v instanceof MEditText) {
            v.requestFocus();
            validation = ((MEditText) v).validateText(true);
        }
        return validation;
    }

    public static boolean isEmailValid(CharSequence email) {
        Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+");
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        boolean check;
        if (Pattern.matches("^[+]?[0-9]{10,13}$", phone))
            check = (phone.length() == 10);
        else check = false;
        return check;
    }

    public static boolean isUserNameValid(String str) {
        boolean check;
        check = Pattern.matches("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", str);
        return check;
    }

    public int isErrortypeToast() {
        return errortype;
    }

    public void setErrortypeToast(int errortype) {
        this.errortype = errortype;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void addValidation(int validation) {
        validations = validations | validation;
    }

    public void removeValidation(int validation) {
        validations = validations & ~validation;
    }

    public void setError() {
        setError(errorMessage);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        validateTextWithExclusion(~TEXT_EMAIL);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MEditText);
        validations = a.getInt(R.styleable.MEditText_validation, 0);
        flagName = a.getString(R.styleable.MEditText_flag_name);
        if (flagName == null && getHint() != null)
            flagName = getHint().toString();
        else flagName = "";
        errortype = a.getInt(R.styleable.MEditText_errorType, 0);
        seteror = a.getInt(R.styleable.MEditText_errorType, 0) == 0;
        textminLength = a.getInt(R.styleable.MEditText_minLength, textminLength);
        setCustomFont(context, attrs);
        a.recycle();

    }

    public boolean validateText() {
        return validateText(flagName, seteror, validations);
    }

    public boolean validateTextWithExclusion(int exclusion) {
        return validateText(flagName, seteror, exclusion);
    }

    public boolean validateText(boolean seteror) {
        return validateText(flagName, seteror, validations);
    }

    public boolean validateText(String flag, boolean seteror, int exclusion) {
        boolean result = true;
        this.seteror = seteror;
        String text = getText().toString();
        try {
            int validations = this.validations & exclusion;
            if ((validations & TEXT_WHITE_SPACE) == TEXT_WHITE_SPACE && text.contains(" ")) {
                errorMessage = getContext().getString(R.string.whitespace_invalid, flag);
                result = false;
            } else if ((validations & TEXT_NOT_EMPTY) == TEXT_NOT_EMPTY && text.trim().equals("")) {
                errorMessage = getContext().getString(R.string.empty_field, flag);
                result = false;
            } else if ((validations & TEXT_NULLABLE) == TEXT_NULLABLE && text.equals("")) {
                //errorMessage = getContext().getString(R.string.zero_field, internetCheckFlag);
                result = true;
            } else if ((validations & TEXT_NOT_ZERO) == TEXT_NOT_ZERO && Double.parseDouble(text) <= 0) {
                errorMessage = getContext().getString(R.string.zero_field, flag);
                result = false;
            } else if ((validations & TEXT_EMAIL) == TEXT_EMAIL && !isEmailValid(text)) {
                errorMessage = getContext().getString(R.string.email_invalid, flag);
                result = false;
            } else if ((validations & TEXT_MOBILE) == TEXT_MOBILE && !isValidPhone(text)) {
                errorMessage = getContext().getString(R.string.mobile_invalid, flag);
                result = false;
            } else if ((validations & TEXT_VALID_USER_NAME) == TEXT_VALID_USER_NAME && !isUserNameValid(text)) {
                errorMessage = getContext().getString(R.string.special_character_invalid, flag);
                result = false;
            } else if ((validations & TEXT_MIN_MIN_LENGTH) == TEXT_MIN_MIN_LENGTH && text.length() < textminLength) {
                errorMessage = String.format(getResources().getString(R.string.invalid_length), flag, textminLength - 1);
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }
        if (!result && seteror && errortype == ERROR_TYPE_SET) setError();
        else if (!result && seteror && errortype == ERROR_TYPE_SET_TO_LAYOUT && getParent().getParent() instanceof TextInputLayout)
            ((TextInputLayout) getParent().getParent()).setError(errorMessage);
        else if (!result && seteror && errortype == ERROR_TYPE_TOAST)
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        else if (result && seteror && errortype == ERROR_TYPE_SET_TO_LAYOUT && getParent().getParent() instanceof TextInputLayout) {
            ((TextInputLayout) getParent().getParent()).setError("");
            ((TextInputLayout) getParent().getParent()).invalidate();
        }
        return result;
    }

    public boolean isValidMobileNumber(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray fontTypedArray = ctx.obtainStyledAttributes(attrs, R.styleable.custom_font_styles);
        String textStyle = fontTypedArray.getString(R.styleable.custom_font_styles_font_name_with_asset_path);
        try {
            if (textStyle == null) {
                mTypeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/ROBOTO-REGULAR.TTF");
            } else {
                mTypeface = Typeface.createFromAsset(ctx.getAssets(), textStyle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), "Could not get typeface: " + textStyle + "&&" + e.getMessage() + " " + this.getId());
        }
        setTypeface(mTypeface, fontTypedArray.getInt(R.styleable.custom_font_styles_android_textStyle, 0));
        fontTypedArray.recycle();
    }


}
