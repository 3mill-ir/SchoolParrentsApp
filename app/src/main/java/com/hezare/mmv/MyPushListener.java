package com.hezare.mmv;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import co.ronash.pushe.PusheListenerService;

/**
 * Created by amirhododi on 6/21/2017.
 */
public class MyPushListener extends PusheListenerService {


    @Override
    public void onMessageReceived(JSONObject message, JSONObject content){

        if (message.length() == 0)
            return; //json is empty

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean loggedin = preferences.getBoolean("Login", false);
        if(loggedin){
            try{
                String Type = message.getString("Type");
                String Title = message.getString("Title");
                String Matn = message.getString("Matn");
                if(Type.contains("Mali")){

                    Intent intent = new Intent(App.getContext(), Mali.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(App.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder b = new NotificationCompat.Builder(App.getContext());
                    b.setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.drawable.logosolda)
                            .setContentTitle(Title)
                            .setContentText(Matn)
                            .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                            .setContentIntent(contentIntent);

                    Random r=new Random();
                    NotificationManager notificationManager = (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(r.nextInt(255), b.build());



                }


            } catch (JSONException e) {
            }
        }




    }
}