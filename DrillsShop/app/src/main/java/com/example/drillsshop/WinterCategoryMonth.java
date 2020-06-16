package com.example.drillsshop;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WinterCategoryMonth extends AppCompatActivity {
    private ListView listViewWinterMonths;
    private ArrayList<Month> months;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winter_category_month);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        listViewWinterMonths = findViewById(R.id.listViewWinterMonths);
        months  = new ArrayList<>();
        months.add(new Month(getString(R.string.winter_december_title),getString(R.string.winter_december_info),R.drawable.december));//poslednui parametr eto id kartinki moego mecuaca
        months.add(new Month(getString(R.string.winter_january_title),getString(R.string.winter_january_info),R.drawable.january));
        months.add(new Month(getString(R.string.winter_february_title),getString(R.string.winter_february_info),R.drawable.february));
        //для того чтобы связать листвью и какой-либо массив нужен адаптер
        ArrayAdapter<Month> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,months);//maket
        listViewWinterMonths.setAdapter(adapter);
        listViewWinterMonths.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Month month = months.get(position);
                Intent intent = new Intent(getApplicationContext(), MonthWinterActivity.class);
                intent.putExtra("title",month.getTitle());
                intent.putExtra("info",month.getInfo());
                intent.putExtra("resId",month.getImageResourceid());
                startActivity(intent);
            }
        });

    }
}
