package com.example.drillsshop;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MonthWinterActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewInfo;//создаем ссылки на объекты из макета
    private ImageView imageViewMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_month_winter);
        textViewName = findViewById(R.id.NameMonth);
        textViewInfo = findViewById(R.id.textViewWinterDescription);
        imageViewMonth = findViewById(R.id.imageViewWinterMonth);
        Intent intent = getIntent();
        if (intent.hasExtra("title") && intent.hasExtra("info") && intent.hasExtra("resId")){
            String title = intent.getStringExtra("title");//если в интенте есть значения то получаем их
            String info = intent.getStringExtra("info");
            int resId = intent.getIntExtra("resId",-1);//-1 дефолтное знаяение если не придет
            textViewName.setText(title);
            textViewInfo.setText(info);
            imageViewMonth.setImageResource(resId);

        } else {
            Intent backToCategory = new Intent(this,WinterCategoryMonth.class);
            startActivity(backToCategory);
        }
    }
}
