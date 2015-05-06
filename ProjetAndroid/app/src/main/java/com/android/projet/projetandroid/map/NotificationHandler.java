package com.android.projet.projetandroid.map;

/**
 * Created by Utilisateur on 2015-05-06.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.android.projet.projetandroid.R;
import com.android.projet.projetandroid.game.AndroidLauncher;

public class NotificationHandler {
    // Notification handler singleton
    private static NotificationHandler nHandler;
    private static NotificationManager mNotificationManager;


    public NotificationHandler() {}


    /**
     * Singleton pattern implementation
     * @return
     */
    public static  NotificationHandler getInstance(Context context) {
        if(nHandler == null) {
            nHandler = new NotificationHandler();
            mNotificationManager =
                    (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return nHandler;
    }


    /**
     * Shows a simple notification
     * @param context aplication context
     */
    public void createSimpleNotification(Context context) {
        // Creates an explicit intent for an Activity
        Intent resultIntent = new Intent(context, AndroidLauncher.class);

        // Creating a artifical activity stack for the notification activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MapsActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        // Pending intent to the notification manager
        PendingIntent resultPending = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Building the notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.castle) // notification icon
                .setContentTitle("Viens jouer") // main title of the notification
                .setContentText("Tu es proche d'un mini-jeu") // notification text
                .setContentIntent(resultPending); // notification intent

        // mId allows you to update the notification later on.
        mNotificationManager.notify(10, mBuilder.build());
    }

}