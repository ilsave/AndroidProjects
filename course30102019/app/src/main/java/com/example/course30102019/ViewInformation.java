package com.example.course30102019;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewInformation extends AppCompatActivity implements View.OnClickListener{

    private TextView ViewInfo;
    private TextView count;
    private Button click;
    private Button exit;
    private int i =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_information);

        ViewInfo = (TextView) findViewById(R.id.ViewInfo);
        ViewInfo.setText(MainActivity.a + "  " + MainActivity.b);

        click = (Button) findViewById(R.id.click);
        click.setOnClickListener( this);

        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(this);

        count = (TextView) findViewById(R.id.count);
        count.setText(String.valueOf(i));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.click:
                count.setText(String.valueOf(i++));
                break;

            case R.id.exit:

                //Выход из прилки
                finish();
                moveTaskToBack(true);

                System.exit(0); // возвращает на предыдущее активити
                break;
        }
    }
}
