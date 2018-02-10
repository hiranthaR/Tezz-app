package com.tezz.tezzapp.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tezz.tezzapp.R;
import com.tezz.tezzapp.uis.LoginActivity;

import java.util.Map;
import java.util.Random;

/**
 * Created by User on 12/31/2017.
 */
public class FCMMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String,String> map = remoteMessage.getData();
        String title = map.get("title");
        String message = map.get("text");
        System.out.println(map.size());


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle(title);
        mBuilder.setContentText(message);
        mBuilder.setAutoCancel(true);
        mBuilder.setDefaults(Notification.DEFAULT_ALL);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);

        Intent resultIntent = new Intent(this, LoginActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginActivity.class);

        //Adds the Intent that starts the Activity to the top of the stack

        stackBuilder.addNextIntent(resultIntent);

        //if service restart cotinuesly,i think, we must use big random int as below NotificationID
        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(new Random().nextInt(), PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(new Random().nextInt(), mBuilder.build());
    }
}
