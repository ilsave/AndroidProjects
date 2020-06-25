package ru.gushchin.politexmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.jsoup.select.Elements;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";


    private EditText editText_FirstStudentName;
    private EditText editText_SecondStudentName;
    private EditText editText_ThirdStudentName;
    private EditText editText_StudentId;
    private Spinner spinner_TypeEducation;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_FirstStudentName = findViewById(R.id.editTextText_FirstStudentName);
        editText_SecondStudentName = findViewById(R.id.editTextText_SecondStudentName);
        editText_ThirdStudentName = findViewById(R.id.editTextText_ThirdStudentName);
        editText_StudentId = findViewById(R.id.editTextText_StudentId);
        spinner_TypeEducation = findViewById(R.id.spinnerTypeEducation);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);




    }



    private void init(){
        final NetworkUtils networkUtils = new NetworkUtils(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
               // networkUtils.getWeb(textViewKyrs);
            }
        }).start();
    }

    public void onButtonReadyClicked(View view) {
        final NetworkUtils networkUtils = new NetworkUtils(this);

        new PolitechQueryTask(this).execute();

    }



    public static void  createChannelIfNeeded(NotificationManager manager){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    class PolitechQueryTask extends AsyncTask<Void, Void, Elements>{

        Context context;

        public PolitechQueryTask(Context context) {
            this.context = context;
        }

        @Override
        protected Elements doInBackground(Void... params) {
            Elements response;
               response =  NetworkUtils.getWeb(editText_FirstStudentName.getText().toString(),
                         editText_SecondStudentName.getText().toString(),
                         editText_ThirdStudentName.getText().toString(),
                         spinner_TypeEducation.getSelectedItem().toString(),
                         editText_StudentId.getText().toString());
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
                List<String> list = ParseInfo.getBaseInfo(response);
                String markline = ParseInfo.getAllMarks(response);
                progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(MainActivity.this, MarkActivity.class);
                intent.putExtra("line0", list.get(0));
                intent.putExtra("line1", list.get(1));
                intent.putExtra("line2", list.get(2));
                intent.putExtra("line3", markline);
                intent.putExtra("line4", ParseInfo.getAverageMark(response));
                startActivity(intent);
            } else {
                Toast.makeText(context, "Данные введены неверно", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

}