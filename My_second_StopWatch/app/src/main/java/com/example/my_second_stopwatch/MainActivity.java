package com.example.my_second_stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView TextViewTimer;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;
    private boolean isRunning = false;
    private boolean wasRunning = false;

    public void onClickStartTimer(View view) {
        isRunning = true;
    }

    public void onClickStopTimer(View view) {
        isRunning = false;
        wasRunning = true;
    }

    public void onClickResetInfo(View view) {
        isRunning = false;
        seconds = 0;
    }

    @Override
    public void onPause (){
        super.onPause();
        isRunning = false;
        wasRunning = true;
    }

    @Override
    public void onResume(){
        super.onResume();
        isRunning = wasRunning;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextViewTimer = findViewById(R.id.textView_Time);
        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
        }
        runTimer();
    }
    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putInt("seconds", seconds);
        outstate.putBoolean("wasRunning",wasRunning);
        outstate.putBoolean("isRunning",isRunning);
    }

    private void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
            int hours = seconds / 3600;
            int secs = seconds % 60;
            String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);
            TextViewTimer.setText(time);
            if (isRunning){
                seconds++;
            }
            handler.postDelayed(this, 1000);
            }
        });

    }


}
