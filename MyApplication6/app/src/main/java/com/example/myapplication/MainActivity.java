package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listViewNumbers;
    private SeekBar seekbar;
    private ArrayList<Integer> numbers;
    private int max = 20;
    private int min = 1;
    private int count = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewNumbers = findViewById(R.id.listViewNumbers);
        seekbar = findViewById(R.id.seekBar2);
        seekbar.setMax(max);//добавили максимальное значение для сикбара
        numbers = new ArrayList<>();
        final ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, numbers);
        listViewNumbers.setAdapter(arrayAdapter);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {//слушатель событий для вьюлиста
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (progress < min){
                    seekBar.setProgress(min);
                }
                numbers.clear();//чистим числа чтобы на экране всегда 10 цифр было!
                for (int i = min; i <= count; i++){
                    numbers.add(seekBar.getProgress() * i);
                }
                arrayAdapter.notifyDataSetChanged();//явно указываем адаптеру что значения изменились
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
        seekbar.setProgress(10);//чтобы сразу вызвался onprogressChanged (начальное положение)
    }
}
