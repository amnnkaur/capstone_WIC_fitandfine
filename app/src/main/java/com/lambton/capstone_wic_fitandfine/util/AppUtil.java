package com.lambton.capstone_wic_fitandfine.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.CalendarContract;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;

import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.common.IAppConstants;
import com.lambton.capstone_wic_fitandfine.models.PainAreaData;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;


public class AppUtil {

    public static void openCalendarAppInMonthView(Context context) {
        try {
            long startMillis = System.currentTimeMillis();
            Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
            builder.appendPath("time");
            ContentUris.appendId(builder, startMillis);
            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .setData(builder.build());
            context.startActivity(intent);


        } catch (ActivityNotFoundException e) {
            AlertUtil.showToast(context, context.getString(R.string.no_calendar_app_message));
        }

    }

    public static void openCalendarAppEditEventView(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setData(CalendarContract.Events.CONTENT_URI);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            AlertUtil.showToast(context, context.getString(R.string.no_calendar_app_message));
        }

    }

    public static void openEmailApp(Context context, String mailTo, String subject, String body) {
        Intent mailer = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        if (!TextUtils.isEmpty(subject)) mailer.putExtra(Intent.EXTRA_EMAIL, new String[]{mailTo});
        if (!TextUtils.isEmpty(subject)) mailer.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (!TextUtils.isEmpty(body)) mailer.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(mailer, context.getString(R.string.send_mail_using_msg)));
    }

    public static int getAttachmentResource(String path) {
        if (path.toLowerCase().endsWith("jpg") ||
                path.toLowerCase().endsWith("jpeg") ||
                path.toLowerCase().endsWith("png")) {
            return R.drawable.icn_image;
        } else if (path.toLowerCase().endsWith("pdf")) {
            return R.drawable.icn_pdf;
        } else if (path.toLowerCase().endsWith("doc")) {
            return R.drawable.icn_doc;
        }

        return -1;
    }

    public static String getAttachmentMimeType(String path) {
        if (path.toLowerCase().endsWith("jpg") ||
                path.toLowerCase().endsWith("jpeg") ||
                path.toLowerCase().endsWith("png")) {
            return IAppConstants.MIME_TYPE_IMAGE;
        } else if (path.toLowerCase().endsWith("pdf")) {
            return IAppConstants.MIME_TYPE_PDF;
        } else if (path.toLowerCase().endsWith("doc")) {
            return IAppConstants.MIME_TYPE_MS_WORD;
        }
        return IAppConstants.MIME_TYPE_IMAGE;
    }

    public static String getAttachmentName(String path) {
        String name = null;
        if (path.startsWith("http") || (path.startsWith("https"))) {
            name = path.substring(path.lastIndexOf("=") + 1, path.length());
        } else {
            name = new File(path).getName();
        }

        if (name == null) path = new File(path).getName();

        return name;
    }

    public static void openBrowser(final Context context, String url) {
        String HTTPS = "https://";
        String HTTP = "http://";

        if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
            url = HTTP + url;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(Intent.createChooser(intent, "Choose browser"));// Choose browser is arbitrary :)
        } catch (ActivityNotFoundException e) {
            AlertUtil.showToast(context, context.getString(R.string.no_browser_app_message));
        }
    }

    public static boolean isPaidAppDialogNeeded(final Activity activityContext, final JSONObject jsonObject, final boolean isFinishActivity) {
        boolean isPaidUserError = false;
        if (jsonObject != null) {
            if (JsonUtil.isPaidUserNeeded(jsonObject)) {
                isPaidUserError = true;
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activityContext);
                LayoutInflater inflater = activityContext.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.layout_popup_premium_app, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();

                final String clickableSubString = "Premium";
                String message = JsonUtil.getMessage(jsonObject);
                SpannableString spannableString = new SpannableString(message);
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        String redirectUrl = JsonUtil.getStringFromJsonObject(jsonObject, "data");
                        if (!TextUtils.isEmpty(redirectUrl)) {
                            AppUtil.openBrowser(activityContext, redirectUrl);
                        }
                        alertDialog.dismiss();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(true);
                    }
                };

                int clickableStringIndex = message.toLowerCase().indexOf(clickableSubString.toLowerCase());
                spannableString.setSpan(clickableSpan, clickableStringIndex,
                        clickableStringIndex + clickableSubString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(Typeface.BOLD), clickableStringIndex,
                        clickableStringIndex + clickableSubString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                TextView textview = (TextView) dialogView.findViewById(R.id.layout_paid_popup_message_text);
                textview.setText(spannableString);
                textview.setMovementMethod(LinkMovementMethod.getInstance());
                textview.setHighlightColor(Color.TRANSPARENT);

                Button okButton = (Button) dialogView.findViewById(R.id.layout_premium_popup_button);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        if (isFinishActivity)
                            activityContext.finish();
                    }
                });

                alertDialog.show();
            }
        }


        return isPaidUserError;
    }


    public static boolean isPaidAppDialogNeeded(final Activity activityContext, final PainAreaData jsonObject, final boolean isFinishActivity) {
        boolean isPaidUserError = false;
        if (jsonObject != null) {
            if (JsonUtil.isPaidUserNeeded(jsonObject)) {
                isPaidUserError = true;
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activityContext);
                LayoutInflater inflater = activityContext.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.layout_popup_premium_app, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();

                final String clickableSubString = "Premium";
                String message = jsonObject.getMessage();
                SpannableString spannableString = new SpannableString(message);
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        String redirectUrl = jsonObject.getData().toString();
                        if (!TextUtils.isEmpty(redirectUrl)) {
                            AppUtil.openBrowser(activityContext, redirectUrl);
                        }
                        alertDialog.dismiss();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(true);
                    }
                };

                int clickableStringIndex = message.toLowerCase().indexOf(clickableSubString.toLowerCase());
                spannableString.setSpan(clickableSpan, clickableStringIndex,
                        clickableStringIndex + clickableSubString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(Typeface.BOLD), clickableStringIndex,
                        clickableStringIndex + clickableSubString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                TextView textview = (TextView) dialogView.findViewById(R.id.layout_paid_popup_message_text);
                textview.setText(spannableString);
                textview.setMovementMethod(LinkMovementMethod.getInstance());
                textview.setHighlightColor(Color.TRANSPARENT);

                Button okButton = (Button) dialogView.findViewById(R.id.layout_premium_popup_button);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        if (isFinishActivity)
                            activityContext.finish();
                    }
                });

                alertDialog.show();
            }
        }


        return isPaidUserError;
    }


    public static Bitmap orientateBitmap(Bitmap bitmap, String filePath){
        int rotation = 0;

        try
        {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int exifRotation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            if(exifRotation!= ExifInterface.ORIENTATION_UNDEFINED)
            {
                switch(exifRotation)
                {
                    case ExifInterface.ORIENTATION_ROTATE_180 :
                        rotation = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270 :
                        rotation = 270;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90 :
                        rotation = 90;
                        break;
                }
            }
        }
        catch(IOException e){}


        if(rotation!=0)
        {
            Matrix matrix = new Matrix();
            matrix.setRotate(rotation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        return bitmap;
    }
}
