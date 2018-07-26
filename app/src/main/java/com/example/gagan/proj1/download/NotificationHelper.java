package com.example.gagan.proj1.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.example.gagan.proj1.R;

/**
 * Created by Gagan on 4/19/2018.
 */

public class NotificationHelper {
    private Context mContext;
    private int NOTIFICATION_ID = 1;
    private Notification mNotification;
    private NotificationManager mNotificationManager;
    private PendingIntent mContentIntent;
    private CharSequence mContentTitle;
    private Notification.Builder notificationBuilder;

    public NotificationHelper(Context context) {
        mContext = context;
    }

    /**
     * Put the notification into the status bar
     */
    public void createNotification() {
        //get the notification manager
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        //create the notification
        int icon = android.R.drawable.ic_menu_share;
        mContentTitle = "Downloading file"; //Full title of the notification in the pull down
        CharSequence contentText = "0% complete"; //Text of the notification in the pull down
        Intent notificationIntent = new Intent();
        //I don't want to use this here so I'm just creating a blank one
        mContentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);

        notificationBuilder = new Notification.Builder(mContext)
                .setContentTitle(mContentTitle)
                .setContentText(contentText)
                .setSmallIcon(icon)
                .setLargeIcon((BitmapFactory.decodeResource(mContext.getResources(),
                        android.R.drawable.ic_menu_share)))
                .setContentIntent(mContentIntent)
                .setAutoCancel(false);
        mNotification = notificationBuilder.build();
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;

        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }

    /**
     * Receives progress updates from the background task and updates the status bar notification appropriately
     *
     * @param percentageComplete
     */
    public void progressUpdate(int percentageComplete) {
        //build up the new status message
        CharSequence contentText = percentageComplete + "% complete";
        //publish it to the status bar
        notificationBuilder.setContentText(contentText);
        mNotification = notificationBuilder.build();
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }

    /**
     * called when the background task is complete, this removes the notification from the status bar.
     * We could also use this to add a new ‘task complete’ notification
     */
    public void completed() {
        //remove the notification from the status bar
//        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}
