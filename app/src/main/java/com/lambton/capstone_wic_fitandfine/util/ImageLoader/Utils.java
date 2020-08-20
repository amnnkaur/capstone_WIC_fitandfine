package com.lambton.capstone_wic_fitandfine.util.ImageLoader;

import android.net.Uri;

import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static String buildUrl()
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("developer.android.com")
                .appendPath("guide")
                .appendPath("components")
                .appendPath("intents-common.html")
                .appendQueryParameter("utm_source", "udacity")
                .appendQueryParameter("utm_medium", "course")
                .appendQueryParameter("utm_campaign", "android_basics")
                .fragment("Phone");
        return builder.build().toString();
    }
}

