package ru.gushchin.politexmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MarkActivity extends AppCompatActivity {

    private TextView textViewFacultetTitle;
    private TextView textViewKyrsTitle;
    private TextView textViewGroupTitle;
    private TextView textViewInfoMark;
    private TextView textViewAverMark;

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        textViewAverMark = findViewById(R.id.textView_mark_averMark);
        textViewAverMark.setTextColor(Color.parseColor("#000000"));


        textViewInfoMark = findViewById(R.id.textView_mark_Info);
        textViewInfoMark.setTextColor(Color.parseColor("#000000"));

        textViewFacultetTitle = findViewById(R.id.textView_mark_facultetInfo);
        textViewFacultetTitle.setTextColor(Color.parseColor("#000000"));

        textViewKyrsTitle = findViewById(R.id.textView_mark_kyrsInfo);
        textViewKyrsTitle.setTextColor(Color.parseColor("#000000"));


        textViewGroupTitle = findViewById(R.id.textView_mark_groupInfo);
        textViewGroupTitle.setTextColor(Color.parseColor("#000000"));

        String info0 = getIntent().getStringExtra("line0");
        String info1 = getIntent().getStringExtra("line1");
        String info2 = getIntent().getStringExtra("line2");
        String info3 = getIntent().getStringExtra("line3");
        String info4 = getIntent().getStringExtra("line4");

        Log.d("MysecondLog",info0);
        textViewFacultetTitle.setText(info0);
        textViewKyrsTitle.setText(info1);
        textViewGroupTitle.setText(info2);
        textViewInfoMark.setText(info3);
        textViewAverMark.setText(info4.substring(0,4));

        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Update, your average mark is  ")
                        .setLargeIcon( BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_menu_favorites))
                        .setContentText(info4);
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID, notificationBuilder.build());

    }

    @Override
    protected void onStop() {
        super.onStop();
//        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
//                        .setAutoCancel(false)
//                        .setSmallIcon(R.drawable.ic_launcher_foreground)
//                        .setWhen(System.currentTimeMillis())
//                        .setContentIntent(pendingIntent)
//                        .setContentTitle("Загрузка прошла удачно!")
//                        .setContentText("Вы ввели правильные данные");
//        createChannelIfNeeded(notificationManager);
//        notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
    }
    public static void  createChannelIfNeeded(NotificationManager manager){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}