package com.lambton.capstone_wic_fitandfine.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;

import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.lambton.capstone_wic_fitandfine.R;


public class CustomFontButton extends AppCompatButton {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomFontButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomFontButton);

        String fontName = attributeArray.getString(R.styleable.CustomFontButton_button_font);
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, fontName, textStyle);
        setTypeface(customFont);

        attributeArray.recycle();
    }

    private Typeface selectTypeface(Context context, String fontName, int textStyle) {

        if (!TextUtils.isEmpty(fontName)) {
           /* if (fontName.equalsIgnoreCase(context.getString(R.string.font_lato_light))) {
                return FontCache.getTypeface(context.getString(R.string.font_lato_light), context);

            } else {
                switch (textStyle) {
                    case Typeface.BOLD: // bold
                        return FontCache.getTypeface(context.getString(R.string.font_lato_bold), context);

                    case Typeface.ITALIC: // italic
                        return FontCache.getTypeface(context.getString(R.string.font_lato_italic), context);

                    case Typeface.BOLD_ITALIC: // bold italic
                        return FontCache.getTypeface(context.getString(R.string.font_lato_bold_italic), context);

                    case Typeface.NORMAL: // regular
                        return FontCache.getTypeface(context.getString(R.string.font_lato_regular), context);

                    default:
                        return FontCache.getTypeface(context.getString(R.string.font_lato_regular), context);
                }
            }*/
            return FontCache.getTypeface(fontName, context);
        } else {
            // no matching font found
            // return null so Android just uses the standard font (Roboto)
            return null;
        }
    }
}
