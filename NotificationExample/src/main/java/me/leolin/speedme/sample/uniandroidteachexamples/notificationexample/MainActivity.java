package me.leolin.speedme.sample.uniandroidteachexamples.notificationexample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Notification notificationA = getNotificationForActivityA();
        Notification notificationB = getNotificationForActivityB();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationA);
        notificationManager.notify(2, notificationB);

    }

    private Notification getNotificationForActivityA() {
        Intent intentToActivityA = new Intent(getApplicationContext(), ActivityA.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intentToActivityA, PendingIntent.FLAG_ONE_SHOT);
        return new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Go to A")
                .setContentTitle("A")
                .setAutoCancel(true) //點了會自動消失
                .setContentIntent(pendingIntent)
                .build();
    }

    private Notification getNotificationForActivityB() {
        Intent intentToActivityB = new Intent(getApplicationContext(), ActivityB.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intentToActivityB, PendingIntent.FLAG_ONE_SHOT);
        return new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Go to B")
                .setContentTitle("B")
                .setAutoCancel(true) //點了會自動消失
                .setContentIntent(pendingIntent)
                .build();
    }

}
