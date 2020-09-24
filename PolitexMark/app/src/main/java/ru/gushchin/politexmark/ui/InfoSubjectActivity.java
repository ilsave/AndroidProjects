package ru.gushchin.politexmark.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import maes.tech.intentanim.CustomIntent;
import ru.gushchin.politexmark.R;

public class InfoSubjectActivity extends AppCompatActivity {

    private TextView textViewListMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_marks);
        textViewListMarks = findViewById(R.id.textViewListMarks);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Log.d("infosub", preferences.getString("allSubjects", "nam"));
        textViewListMarks.setText(preferences.getString("allSubjects", "nam"));

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }
}