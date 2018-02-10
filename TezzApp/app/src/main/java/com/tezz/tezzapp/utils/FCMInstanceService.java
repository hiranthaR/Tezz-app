package com.tezz.tezzapp.utils;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by User on 12/31/2017.
 */
public class FCMInstanceService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("fcm",MODE_PRIVATE).edit();
        editor.putString("token",token);
        editor.apply();
        editor.commit();
    }
}
