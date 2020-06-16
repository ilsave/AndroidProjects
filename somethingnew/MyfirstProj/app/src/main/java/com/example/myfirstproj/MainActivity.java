package com.example.myfirstproj;

        import android.app.Activity;
        import android.content.ContentValues;
        import android.content.DialogInterface;
        import android.content.Intent;

        import android.content.SharedPreferences;
        import android.database.sqlite.SQLiteDatabase;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


   private EditText editName;
   private EditText editPhone;
   private Button button;
    private Button alert;


   public static String a;
   public static String b;

   private Intent intent;
   private AlertDialog.Builder builder;
   private AlertDialog alert1;

   //Сохраняет данные в виде ключ - значение
   private SharedPreferences sPref;

   public final String SAVED_TEXT_NAME = "saved_text_name";
   public final String SAVED_TEXT_PHONE = "saved_text_phone";

   //ключ для параметра доступа
   public static String MY_PREFS = "MY_PREFS";

   //параметр доступа, MODE_PRIVATE - доступ только данному приложению
   int prefMode = Activity.MODE_PRIVATE;

   DBHelper dbHelper;

    @Override
    //Экран
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Поле для ввода имени
        editName = (EditText) findViewById(R.id.editName);
        //Поле для ввода телефона
        editPhone = (EditText) findViewById(R.id.editPhone);
        //Кнопка регистрации
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        alert = (Button) findViewById(R.id.alert);
        alert.setOnClickListener(this);

        loadText();

        dbHelper = new DBHelper(this);
    }

    @Override
    //Нажатие кнопочек
    public void onClick(View v) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        //для добавления новых строк в таблицу
        ContentValues contentValues = new ContentValues();

        switch (v.getId()) {

            case R.id.button:

                // Перехаод на новое активити
                intent = new Intent(this, ViewInformation.class);
                startActivity(intent);


                a = String.valueOf(editName.getText());
                b = String.valueOf(editPhone.getText());

                //Вывод информации в консоли (Тег , Выводимая информация)
                Log.d("INFO", "Имя" + editName.getText() + "Telephone" + editPhone.getText());

                Toast.makeText(MainActivity.this, a + b, Toast.LENGTH_SHORT).show();


                //добавляем записи в таблицу (ключ - значение)
                contentValues.put(DBHelper.KEY_NAME,editName.getText().toString());
                contentValues.put(DBHelper.KEY_PHONE,editPhone.getText().toString());

                //запись в БД, null для создания пустой строки
                database.insert(DBHelper.TABLE_CONTACTS,null,contentValues);

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
        //закрываем соединение с БД
        dbHelper.close();
    }

    private  void saveText(){

        //Инициализируем с ключом и параметром доступа
        sPref = getSharedPreferences(MY_PREFS,prefMode);

        //для редактирования данных
        SharedPreferences.Editor ed = sPref.edit();

        //добавляем в editor значения вида ключ - знаение
        ed.putString(SAVED_TEXT_NAME,editName.getText().toString());
        ed.putString(SAVED_TEXT_PHONE,editPhone.getText().toString());

        //сохраняем данные
        ed.apply();
    }

    private void loadText(){

        //Инициализируем с ключом и параметром доступа
        sPref = getSharedPreferences(MY_PREFS,prefMode);

        //считывание данных(ключ, значение по умолчанию)
        String savedTextName = sPref.getString(SAVED_TEXT_NAME,null);
        String savedTextPhone = sPref.getString(SAVED_TEXT_PHONE,null);

        editName.setText(savedTextName);
        editPhone.setText(savedTextPhone);

    }

    //метод закрытия Activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }
}