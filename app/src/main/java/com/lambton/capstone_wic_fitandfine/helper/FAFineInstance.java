package com.lambton.capstone_wic_fitandfine.helper;

import android.content.Context;

import com.lambton.capstone_wic_fitandfine.models.User;
import com.lambton.capstone_wic_fitandfine.util.ImageLoader.ImageLoader;

public class FAFineInstance {
        public static FAFineInstance instance;

        private User user;

    private FAFineInstance() {
        //empty private constructor
    }

        public static FAFineInstance getInstance() {
        if (instance == null) {
            instance = new FAFineInstance();
        }
        return instance;
    }

        public User getUser() {
        if (user == null) user = new User();
        return user;
    }


        public void resetAuthenticationDetails(Context context){
        FAFineInstance.getInstance().user=null;
        ImageLoader imageLoader = new ImageLoader(context);
        imageLoader.clearCache();

    }
}
