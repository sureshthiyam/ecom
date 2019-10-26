package com.kangleigeeks.ecommerce.potchei.data.util;

import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kangleigeeks.ecommerce.potchei.data.helper.database.DatabaseUtil;

public class UniqaApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //  if (CustomSharedPrefs.getFirebaseToken(getApplicationContext()).equals("")) {
        FirebaseMessaging.getInstance().subscribeToTopic("message");
        FacebookSdk.sdkInitialize(getApplicationContext());
        DatabaseUtil.init(getApplicationContext());
        // }

    }
}
