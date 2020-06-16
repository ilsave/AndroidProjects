package com.example.stringadvanced;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String nameString = "Anrew, Alex, Poman, Pavel, Michel";
        String [] names = nameString.split(", ");
        for (String name : names){
            Log.i("myName", name);
        }
    }
}
