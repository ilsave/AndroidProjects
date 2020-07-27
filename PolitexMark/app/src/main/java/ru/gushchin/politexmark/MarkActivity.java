package ru.gushchin.politexmark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import maes.tech.intentanim.CustomIntent;

public class MarkActivity extends AppCompatActivity {

    public void setList(List<Subject> list) {
        this.listfromtask = list;
    }

    private List<Subject> listfromtask;
     List<Subject> list;
    SubjectAdapter subjectAdapter;
    private RecyclerView recyclerView;
    String info0;
    String info1;
    String info2;
    String info3;
    String info4;
    String info5;
    String info6;
    String info7;
    String info8;

    Toolbar toolbar;

    private TextView textViewFacultetTitle;
    private TextView textViewKyrsTitle;
    private TextView textViewGroupTitle;
    private TextView textViewInfoMark;
    private TextView textViewAverMark;
    private ProgressBar progressBar;

    public static final String TAG = "MarkActivity";

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);




        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                MyPeriodWork.class, 1, TimeUnit.DAYS
        )
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance().enqueue(periodicWorkRequest);


        progressBar = findViewById(R.id.progressBar);

        textViewAverMark = findViewById(R.id.textView_mark_averMark);
        textViewAverMark.setTextColor(Color.parseColor("#000000"));


        textViewFacultetTitle = findViewById(R.id.textView_mark_facultetInfo);
        textViewFacultetTitle.setTextColor(Color.parseColor("#000000"));

        textViewKyrsTitle = findViewById(R.id.textView_mark_kyrsInfo);
        textViewKyrsTitle.setTextColor(Color.parseColor("#000000"));


        textViewGroupTitle = findViewById(R.id.textView_mark_groupInfo);
        textViewGroupTitle.setTextColor(Color.parseColor("#000000"));


        info0 = getIntent().getStringExtra("firstName");
        info1 = getIntent().getStringExtra("secondName");
        info2 = getIntent().getStringExtra("fatherName");
        info3 = getIntent().getStringExtra("eduType");
        info4 = getIntent().getStringExtra("studentNumber");

        info5 = getIntent().getStringExtra("facultetName");
        info6 = getIntent().getStringExtra("kyrsNumber");
        info7 = getIntent().getStringExtra("group");
        info8 = getIntent().getStringExtra("averMark");

        textViewFacultetTitle.setText(info5);
        textViewKyrsTitle.setText(info6);
        textViewGroupTitle.setText(info7);
        textViewAverMark.setText(info8);


        Calendar calendar = Calendar.getInstance();


        String currentDate = DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(calendar.getTime());


        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

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
                        .setLargeIcon( BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_menu_favorites))
                        .setContentText(String.valueOf(calendar.get(Calendar.MONTH)+1));
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
        new PolitechQueryTask().execute();

    }

    public static void  createChannelIfNeeded(NotificationManager manager){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_info,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_1:
                Toast.makeText(this, "item_1", Toast.LENGTH_SHORT).show();
                new PolitechQueryTask().execute();
                return true;
            case R.id.item_2:
                Toast.makeText(this, "item_2", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MarkActivity.this, InfoActivity.class);
                startActivity(intent);
                CustomIntent.customType(this, "left-to-right");
                return true;
            case R.id.item_3:
                Toast.makeText(this, "item_3", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MarkActivity.this, InfoSubjectActivity.class);
                startActivity(intent2);
                CustomIntent.customType(this, "left-to-right");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

    class PolitechQueryTask extends AsyncTask<Void, Void, Elements> {



//        public PolitechQueryTask(Context context) {
//            this.context = getApplicationContext();
//        }

        @Override
        protected Elements doInBackground(Void... params) {
            Elements response;
            response =  NetworkUtils.getWeb(info0,
                    info1,
                    info2,
                    info3,
                    info4);
            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Elements response) {
            super.onPostExecute(response);
            if (response!=null) {
                List<Subject> subjectList = ParseInfo.getSubjectList(response,info6,getApplicationContext());
                subjectAdapter = new SubjectAdapter(subjectList);
                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setAdapter(subjectAdapter);
                LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mlinearLayoutManager);
                subjectAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

}