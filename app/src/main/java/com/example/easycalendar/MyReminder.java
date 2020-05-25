package com.example.easycalendar;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import org.threeten.bp.LocalDate;

import java.util.Calendar;

public class MyReminder {
    private Context context;
    MyEvent event;

    public MyReminder(Context context, MyEvent event) {
        this.context = context;
        this.event = event;
    }


    public void createReminder() {

        //Creating Intent
        Intent intent = new Intent(context, ReminderBroadcast.class);
        intent.setAction("EVENT_REMINDER");
        //TODO learn what makes this flags
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("id", event.getpKey());
        intent.putExtra("event_name", event.getEventName());
        String eventType = context.getResources().
                getStringArray(R.array.categoryOptions_array)[event.getIndex_category()];
        intent.putExtra("event_type", eventType);
        intent.putExtra("event_time", event.getStartTime().toString());
        intent.putExtra("event_date", event.getStartDate());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //getting event time and setting alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        LocalDate eventDate = MyEvent.StringToDate(event.getStartDate(),'-');
        Time eventTime = event.getStartTime();
        calendar.set(eventDate.getYear(), eventDate.getMonthValue()-1, eventDate.getDayOfMonth(),
                eventTime.getHour(), eventTime.getMinute(), 0);
        Log.i("Alarm", calendar.getTime().toString());
        if(event.getIndex_recurrence() > 0 ){
            // Create Repeating Reminder
            //TODO
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60, pendingIntent);
        }else{
            //Create One Shot Reminder
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

    }

    public void createNotificationChannel(Integer source) {
        final String packageName = context.getPackageName();
        String source_sound = "android.resource://" + packageName + "/" + R.raw.promise;

        //  Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Uri sound = Uri.parse(source_sound);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(source.toString(), "events", importance);
            channel.setDescription("Calendar events");
            channel.setSound(sound, audioAttributes);
            Toast.makeText(context, sound.toString(), Toast.LENGTH_SHORT).show();
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}
