package com.example.easycalendar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {

    private static final String CHANNEL_ID = "1234";
    private NotificationCompat.Builder builder;
    private Context context;
    private Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("EVENT_REMINDER")) {
            this.context = context;
            this.intent = intent;
            //createNotificationChannel();
            createNotification();

            final String packageName = context.getPackageName();
            String source_sound = "android.resource://" + packageName + "R.raw.promise";
            Uri sound = Uri.parse(source_sound);

            // int newRingtoneType = RingtoneManager.TYPE_ALARM;
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.promise);
            // mediaPlayer.start();

        }
    }

    private void createNotification() {

        String uuid = intent.getStringExtra("id");
        String event_name = intent.getStringExtra("event_name");
        String event_type = intent.getStringExtra("event_type");
        String event_time = intent.getStringExtra("event_time");
        String event_date = intent.getStringExtra("event_date");


        Intent fullScreenIntent = new Intent(context, MainActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(event_type)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(event_name + " \n" + event_date + " " + event_time))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(generateID(uuid), builder.build());

    }

    private void createNotificationChannel() {
        final String packageName = context.getPackageName();
        // String source_sound ="android.resource://" + packageName+ "/" + "R/raw/promise";

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        //  Uri sound = Uri.parse(source_sound);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "events", importance);
            channel.setDescription("Calendar events");
            channel.setSound(sound, null);
            Toast.makeText(context, sound.toString(), Toast.LENGTH_SHORT).show();
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    private int generateID(String uuid){
        UUID myuuid = UUID.fromString(uuid);
        Long highbits = myuuid.getMostSignificantBits();
        int id = (int)(highbits >> 32);
        return id;

    }
}
