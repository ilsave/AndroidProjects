package ru.gushchin.politexmark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView textViewFacultet;
    public TextView textViewKyrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        textViewFacultet = findViewById(R.id.textView);
//        textViewKyrs = findViewById(R.id.textView2);
        init();
    }

    private void init(){
        final NetworkUtils networkUtils = new NetworkUtils(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                networkUtils.getWeb(textViewKyrs);
            }
        }).start();
    }
}