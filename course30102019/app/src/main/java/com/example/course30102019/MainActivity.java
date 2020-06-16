package com.example.course30102019;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText editText;
    private EditText editText2;
    private Button button;
    private Button alert;


    public static String a;
    public static String b;

    private Intent intent;
    private AlertDialog.Builder builder;
    private AlertDialog alert1;

    @Override
    //Экран
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Поле для ввода имени
        editText = (EditText) findViewById(R.id.editText);
        //Поле для ввода телефона
        editText2 = (EditText) findViewById(R.id.editText2);
        //Кнопка регистрации
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        alert = (Button) findViewById(R.id.alert);
        alert.setOnClickListener(this);
    }

    @Override
    //Нажатие кнопочек
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button:

                // Перехаод на новое активити
                intent = new Intent(this, ViewInformation.class);
                startActivity(intent);


                a = String.valueOf(editText.getText());
                b = String.valueOf(editText2.getText());

                //Вывод информации в консоли (Тег , Выводимая информация)
                Log.d("INFO", "Имя" + editText.getText() + "Telephone" + editText2.getText());

                Toast.makeText(MainActivity.this, a + b, Toast.LENGTH_SHORT).show();


                break;

            case R.id.alert:

                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Дополнительная информация")
                        .setMessage("Ознакомтесь с нашими правилами ...")
                        .setCancelable(false)
                        .setNegativeButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intent = new Intent(MainActivity.this, ViewInformation.class);
                                startActivity(intent);
                            }
                        });
                alert1 = builder.create();
                alert1.show();

                break;
        }
    }

}

