package com.example.bundesliga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Spinner SpinnerDescriptions;
    private TextView TextDescriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpinnerDescriptions = findViewById(R.id.spinnerColors);
        TextDescriptions = findViewById(R.id.textView);
    }

    public void ShowPlayerDescription(View view) {
        int position = SpinnerDescriptions.getSelectedItemPosition();
        String lines = getInfo(position);
        TextDescriptions.setText(lines);
    }
    private String getInfo(int position){
        String[] line = getResources().getStringArray(R.array.player_description);
        return line[position];
    }
}
