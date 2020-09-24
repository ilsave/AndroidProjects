package ru.gushchin.politexmark.ui;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import ru.gushchin.politexmark.other.NetworkUtils;
import ru.gushchin.politexmark.other.ParseInfo;
import ru.gushchin.politexmark.R;

public class MainActivity extends Activity {

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



    public void onButtonReadyClicked(View view) {
        if (editText_StudentId.getText().toString().equals("")
                || editText_FirstStudentName.getText().toString().equals("")
                || editText_SecondStudentName.getText().toString().equals("")
                || editText_ThirdStudentName.getText().toString().equals("")) {
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


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
//        finish();
//        System.exit(0);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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

                        Toast.makeText(getApplicationContext(), "Данные введены верно" + ParseInfo.getAverageMark(response).substring(0, 4), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "Данные введены неверно", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    //            } else {
                    //                Toast.makeText(context, "нет интернета( ", Toast.LENGTH_SHORT).show();
                    //                progressBar.setVisibility(View.INVISIBLE);
                    //            }
                } else {
                    Toast.makeText(getApplicationContext(), "Нет интернета:(", Toast.LENGTH_SHORT).show();
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