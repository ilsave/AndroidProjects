package ru.gushchin.politexmark;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.Objects;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!Objects.equals(preferences.getString("firstName", "name"), "name") &&
                !Objects.equals(preferences.getString("averMark", "name"), "name")) {

            Intent intent = new Intent(StartActivity.this, MarkActivity.class);
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

        }else {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!Objects.equals(preferences.getString("firstName", "name"), "name") &&
                !Objects.equals(preferences.getString("averMark", "name"), "name")) {

            Intent intent = new Intent(StartActivity.this, MarkActivity.class);
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

        }else {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}