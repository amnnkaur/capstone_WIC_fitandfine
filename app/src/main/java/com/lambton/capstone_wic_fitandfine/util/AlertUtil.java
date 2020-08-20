package com.lambton.capstone_wic_fitandfine.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Toast;

import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.ui.addevent.IAppointmentAddedCallBack;


public class AlertUtil {

    public static void showToast(Context context, String msg) {

        if (context != null && msg != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }

    }

    public static void showAlertDialog(Context context, String msg) {
        try {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);

            alertDialog2.setMessage(msg);

            // Setting Positive "Yes" Btn
            alertDialog2.setPositiveButton(R.string.all_ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            dialog.cancel();
                        }
                    });

            // Showing Alert Dialog
            alertDialog2.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void showAlertDialogWithTitle(Context context, String msg, boolean isSuccess) {
        try {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
            if(isSuccess)
                alertDialog2.setTitle(R.string.alert_title_success);
            else
                alertDialog2.setTitle(R.string.alert_title_error);
            alertDialog2.setMessage(msg);
            // Setting Positive "Yes" Btn
            alertDialog2.setPositiveButton(R.string.all_ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            dialog.cancel();
                        }
                    });

            // Showing Alert Dialog
            alertDialog2.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void showAlertDialogWithTitleCallBack(Context context, String msg, boolean isSuccess, final IAppointmentAddedCallBack callBack) {
        try {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
            if(isSuccess)
                alertDialog2.setTitle(R.string.alert_title_success);
            else
                alertDialog2.setTitle(R.string.alert_title_error);
            alertDialog2.setMessage(msg);
            // Setting Positive "Yes" Btn
            alertDialog2.setPositiveButton(R.string.all_ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            dialog.cancel();
                            callBack.iAppointmentAddedCallBack();
                        }
                    });

            // Showing Alert Dialog
            alertDialog2.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    public static void showDialog(Context context, String title, String message, String buttonPositiveMessage,
                                  final DialogInterface.OnClickListener buttonPositiveOnClickListener,
                                  String buttonNegativeMessage, final DialogInterface.OnClickListener buttonNegativeOnClickListener) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }

        if (!TextUtils.isEmpty(message)) {
            dialog.setMessage(message);
        }
        dialog.setCancelable(false);

        if (buttonPositiveMessage != null) {
            dialog.setPositiveButton(buttonPositiveMessage, buttonPositiveOnClickListener);
        }

        // if we haven't specified attributes for the negative button, exclude it from the layout
        if (buttonNegativeMessage != null) {
            dialog.setNegativeButton(buttonNegativeMessage, buttonNegativeOnClickListener);
        }

        dialog.show();

    }

    public static void showDialogWithTitle(Context context, String title, String message, String buttonPositiveMessage,
                                           final DialogInterface.OnClickListener buttonPositiveOnClickListener,
                                           String buttonNegativeMessage, final DialogInterface.OnClickListener buttonNegativeOnClickListener
     , boolean isSuccess ) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        if(isSuccess)
            dialog.setTitle(R.string.alert_title_success);
        else
            dialog.setTitle(R.string.alert_title_error);

        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }

        if (!TextUtils.isEmpty(message)) {
            dialog.setMessage(message);
        }
        dialog.setCancelable(false);

        if (buttonPositiveMessage != null) {
            dialog.setPositiveButton(buttonPositiveMessage, buttonPositiveOnClickListener);
        }

        // if we haven't specified attributes for the negative button, exclude it from the layout
        if (buttonNegativeMessage != null) {
            dialog.setNegativeButton(buttonNegativeMessage, buttonNegativeOnClickListener);
        }

        dialog.show();

    }

}
