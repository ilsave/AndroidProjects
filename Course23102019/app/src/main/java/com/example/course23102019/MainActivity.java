package com.example.course23102019;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText EditTextName;
    private EditText EditTextPassword;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditTextName = findViewById(R.id.editText);
        EditTextPassword = findViewById(R.id.editText2);
        button.setOnClickListener();
    }
}
