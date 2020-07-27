package ru.gushchin.politexmark;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.DateFormat;
import java.util.Calendar;

public class MyPeriodWork extends Worker {

    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    public static final String TAG = "MyPeriodicWork";

    public MyPeriodWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Calendar calendar = Calendar.getInstance();

        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());





        if (calendar.get(Calendar.MONTH)+1 == 4
                || calendar.get(Calendar.MONTH)+1 == 5
                || calendar.get(Calendar.MONTH)+1 == 10
                || calendar.get(Calendar.MONTH)+1 == 11){
            //control weeks

        }


        Log.d(TAG, "work is done");


        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_menu_favorites)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Update, your average mark is  ")
                        .setContentText(currentDate);
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID, notificationBuilder.build());

        return Result.success();
    }

    public static void  createChannelIfNeeded(NotificationManager manager){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
