package ru.gushchin.politexmark;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import ru.gushchin.politexmark.Database.Subject;
import ru.gushchin.politexmark.Database.SubjectRoomDataBase;

public class MyPeriodWork extends Worker {

    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    public static final String TAG = "MyPeriodicWork";
    private LifecycleOwner LifecycleOwner;

    public MyPeriodWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Elements response;
        response = NetworkUtils.getWeb(preferences.getString("firstName", "name"),
                preferences.getString("secondName", "name"),
                preferences.getString("fatherName", "name"),
                preferences.getString("eduType", "name"),
                preferences.getString("studentNumber", "name"));

        List<String> list = ParseInfo.getBaseInfo(response);
        List<ru.gushchin.politexmark.Database.Subject> subjectListFROMSITE = ParseInfo.getSubjectList(response, list.get(1), getApplicationContext());

        List<Subject> subjectListFROMDB = SubjectRoomDataBase.getINSTANCE(getApplicationContext())
                .personDao()
                .getAllSubject();

        if (!(subjectListFROMDB.size() == subjectListFROMSITE.size() && subjectListFROMSITE.containsAll(subjectListFROMDB))){
            Log.d(TAG, "subjectlist db"+subjectListFROMDB);
            Log.d(TAG, "subjectlist from site"+subjectListFROMSITE+"");
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
                            .setContentTitle("Есть изменения")
                            .setContentText("1"+(subjectListFROMDB.size() == subjectListFROMSITE.size())+
                                    " 2"+ (subjectListFROMSITE.containsAll(subjectListFROMDB)));
            createChannelIfNeeded(notificationManager);
            notificationManager.notify(NOTIFY_ID, notificationBuilder.build());

            SubjectRoomDataBase.getINSTANCE(getApplicationContext())
                .personDao()
                .deleteAllSubjects();

            SubjectRoomDataBase.getINSTANCE(getApplicationContext())
                    .personDao()
                    .insertMultipleSubjects(subjectListFROMSITE);
        } else {
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
                            .setContentTitle("Есть изменения в листе информации:)")
                            .setContentText("Ничего не нашел ( НО проверил!");
            createChannelIfNeeded(notificationManager);
            notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
        }


        Log.d("MyperiodWork", subjectListFROMSITE + "");



        Log.d(TAG,"work is done");


        return Result.success();
        }

        public static void createChannelIfNeeded (NotificationManager manager){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(notificationChannel);
            }
        }
    }
