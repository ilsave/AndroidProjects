package ru.gushchin.politexmark.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import maes.tech.intentanim.CustomIntent;
import ru.gushchin.politexmark.R;

public class InfoActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        textView = findViewById(R.id.textViewActInfo);
        textView.setText("Приложение показывает ваши оценки. Для того, чтобы изменить данные нажмите \"назад\" в экране с оценками. ");
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }
}