package com.lambton.capstone_wic_fitandfine.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;

import com.lambton.capstone_wic_fitandfine.R;


public class CustomProgressDialog extends Dialog {

    public static CustomProgressDialog show(Context context,
                                            CharSequence title, CharSequence message) {
        return show(context, title, message, false);
    }

    public static CustomProgressDialog show(Context context) {
        return show(context, "", "", false);
    }

    public static CustomProgressDialog show(Context context,
                                            CharSequence title, CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false, null);
    }

    public static CustomProgressDialog show(Context context,
                                            CharSequence title, CharSequence message, boolean indeterminate,
                                            boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }

    public static CustomProgressDialog show(Context context,
                                            CharSequence title, CharSequence message, boolean indeterminate,
                                            boolean cancelable, OnCancelListener cancelListener) {
        CustomProgressDialog dialog = new CustomProgressDialog(context);
        try {

            dialog.setTitle(title);
            dialog.setCancelable(cancelable);
            dialog.setOnCancelListener(cancelListener);
        /* The next line will add the ProgressBar to the dialog. */
            dialog.addContentView(new ProgressBar(context), new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            dialog.show();

            return dialog;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public CustomProgressDialog(Context context) {
        super(context, R.style.custom_progress_dialog);
    }
}