package ru.gushchin.politexmark;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MarkActivity extends AppCompatActivity {

    private TextView textViewFacultetTitle;
    private TextView textViewKyrsTitle;
    private TextView textViewGroupTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);

        textViewFacultetTitle = findViewById(R.id.textView_mark_facultetInfo);
        textViewFacultetTitle.setTextColor(Color.parseColor("#000000"));

        textViewKyrsTitle = findViewById(R.id.textView_mark_kyrsInfo);
        textViewKyrsTitle.setTextColor(Color.parseColor("#000000"));


        textViewGroupTitle = findViewById(R.id.textView_mark_groupInfo);
        textViewGroupTitle.setTextColor(Color.parseColor("#000000"));

        String info0 = getIntent().getStringExtra("line0");
        String info1 = getIntent().getStringExtra("line1");
        String info2 = getIntent().getStringExtra("line2");

        Log.d("MysecondLog",info0);
        textViewFacultetTitle.setText(info0);
        textViewKyrsTitle.setText(info1);
        textViewGroupTitle.setText(info2);

    }
}