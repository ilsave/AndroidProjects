package ru.gushchin.politexmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Presentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

import maes.tech.intentanim.CustomIntent;

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


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!Objects.equals(preferences.getString("firstName", "name"), "name") &&
                !Objects.equals(preferences.getString("averMark", "name"), "name")) {

            Intent intent = new Intent(MainActivity.this, MarkActivity.class);
            intent.putExtra("firstName", preferences.getString("firstName", "Илья-"));
            intent.putExtra("secondName", preferences.getString("secondName", "Илья-"));
            intent.putExtra("fatherName", preferences.getString("fatherName", "Илья-"));
            intent.putExtra("eduType", preferences.getString("eduType", "Илья-"));
            intent.putExtra("studentNumber", preferences.getString("studentNumber", "Илья-"));
            intent.putExtra("facultetName", preferences.getString("facultetName", "Илья-"));
            intent.putExtra("kyrsNumber", preferences.getString("kyrsNumber", "Илья-"));
            intent.putExtra("group", preferences.getString("group", "Илья-"));
            intent.putExtra("averMark", preferences.getString("averMark", "Илья-"));
            startActivity(intent);


        }

    }


    public void onButtonReadyClicked(View view) {
        if (editText_StudentId.getText()== null || editText_FirstStudentName == null || editText_SecondStudentName == null
                || editText_ThirdStudentName == null) {
            Toast.makeText(this, "Данные должны быть во всех полях!", Toast.LENGTH_SHORT).show();
        } else {
            try {
                if (!iisConnected()) {
                    Toast.makeText(this, "Нет интернета:(", Toast.LENGTH_SHORT).show();
                } else {
                    new PolitechQueryTask(this).execute();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public boolean iisConnected() throws InterruptedException, IOException {
        final String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }


    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    class PolitechQueryTask extends AsyncTask<Void, Void, Elements> {

        Context context;

        public PolitechQueryTask(Context context) {
            this.context = context;
        }

        @Override
        protected Elements doInBackground(Void... params) {
            Elements response;
            response = NetworkUtils.getWeb(editText_FirstStudentName.getText().toString(),
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
            try {
                if (iisConnected()) {
                    if (response != null) {
                        List<String> list = ParseInfo.getBaseInfo(response);
                        String markline = ParseInfo.getAllMarks(response);
                        progressBar.setVisibility(View.INVISIBLE);

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        preferences.edit().putString("firstName", editText_FirstStudentName.getText().toString()).apply();
                        preferences.edit().putString("secondName", editText_SecondStudentName.getText().toString()).apply();
                        preferences.edit().putString("fatherName", editText_ThirdStudentName.getText().toString()).apply();
                        preferences.edit().putString("eduType", spinner_TypeEducation.getSelectedItem().toString()).apply();
                        preferences.edit().putString("studentNumber", editText_StudentId.getText().toString()).apply();
                        preferences.edit().putString("facultetName", list.get(0)).apply();
                        preferences.edit().putString("kyrsNumber", list.get(1)).apply();
                        preferences.edit().putString("group", list.get(2)).apply();
                        preferences.edit().putString("averMark", ParseInfo.getAverageMark(response).substring(0, 4)).apply();

                        Log.d("final", ParseInfo.getAverageMark(response).substring(0, 4) + "ghjk");

                        Toast.makeText(context, "Данные введены верно" + ParseInfo.getAverageMark(response).substring(0, 4), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, MarkActivity.class);
                        intent.putExtra("firstName", editText_FirstStudentName.getText().toString());
                        intent.putExtra("secondName", editText_SecondStudentName.getText().toString());
                        intent.putExtra("fatherName", editText_ThirdStudentName.getText().toString());
                        intent.putExtra("eduType", spinner_TypeEducation.getSelectedItem().toString());
                        intent.putExtra("studentNumber", editText_StudentId.getText().toString());
                        intent.putExtra("facultetName", list.get(0));
                        intent.putExtra("kyrsNumber", list.get(1));
                        intent.putExtra("group", list.get(2));
                        intent.putExtra("averMark", ParseInfo.getAverageMark(response).substring(0, 4));
                        startActivity(intent);
                        CustomIntent.customType(context, "left-to-right");
                    } else {
                        Toast.makeText(context, "Данные введены неверно", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    //            } else {
                    //                Toast.makeText(context, "нет интернета( ", Toast.LENGTH_SHORT).show();
                    //                progressBar.setVisibility(View.INVISIBLE);
                    //            }
                } else {
                    Toast.makeText(context, "Нет интернета:(", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        }

    }