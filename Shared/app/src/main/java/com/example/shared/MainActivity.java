package com.example.shared;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textViewSavedText;
    private EditText textEditSavedText;
    private Button button;
    String saivedText;
    public static String mykey = "key";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textEditSavedText = findViewById(R.id.editTextSaveText);
        textViewSavedText = findViewById(R.id.textViewSavedText);
        SharedPreferences preferences = getSharedPreferences(mykey, Activity.MODE_PRIVATE);
        String abc = preferences.getString("text", "default");
        textViewSavedText.setText(abc);
    }

    public void OnClickButtonSaveText(View view) {

        SharedPreferences preferences = getSharedPreferences(mykey, Activity.MODE_PRIVATE);
        preferences.edit().putString("text", textEditSavedText.getText().toString() ).apply();
    }

}
