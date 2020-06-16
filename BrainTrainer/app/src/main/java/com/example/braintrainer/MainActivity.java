package com.example.braintrainer;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //preferences.edit().putInt("test",5).apply();//apply для выполнения edit для вставки ключ тест номер 5
        //int test = preferences.getInt("test",0);//0 значение по умолчанию в случае есл этот ключ отсуствует
        //Toast.makeText(this,"" + test , Toast.LENGTH_SHORT).show();//чтобы показать число надо + или же метод инт ту стринг
        textViewTimer = findViewById(R.id.TextViewTimer);
        CountDownTimer timer = new CountDownTimer(6000,1000) { // так каунт таймер - абстрактный класс то нам надо реализовать методы, так как он нам понадобится только один раз то используем нанонимный класс
           //600000 это сколько миллисикунд будет отсчитывать таймер. 1000 каждые сколько миллисекунд будет тикать
            @Override
            public void onTick(long l) {//l - это сколько миллисекунд осталось
                int seconds = (int) (l / 1000);
                seconds++;
                textViewTimer.setText(Integer.toString(seconds));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Timer is over bro", Toast.LENGTH_SHORT).show();
                textViewTimer.setText(Integer.toString(0));
            }
        };
        timer.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
