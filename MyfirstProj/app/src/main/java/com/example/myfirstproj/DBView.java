package com.example.myfirstproj;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DBView extends AppCompatActivity implements View.OnClickListener{

    private TextView textId, textName,textPhone;
    private EditText editId,editName, editPhone;
    private Button update,delete,clear,read;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbview);

        textId = findViewById(R.id.textId);
        textName = findViewById(R.id.textName);
        textPhone = findViewById(R.id.textPhone);

        editId = findViewById(R.id.editId);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);

        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        clear = findViewById(R.id.clear);
        read = findViewById(R.id.read);

        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        clear.setOnClickListener(this);
        read.setOnClickListener(this);

        dbHelper = new DBHelper(this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // имя таблицы, список запрашиваемых полей, условие выборки, группировка, сортировка...
        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS,null,null,null,null,null,null);
        //moveToFirst - перемещает курсор на первую сроку
        //moveToNext - перемещает курсор на следующую строку
        //moveToLast - перемещает курсор на последнюю строку
        //moveToPosition - перемещает курсор на указанную строку

        //считываем записи друг за другом начиная с первой и заканчивая последней
        if(cursor.moveToFirst()){

            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int phoneIndex = cursor.getColumnIndex(DBHelper.KEY_PHONE);

            do{
                textId.setText(String.valueOf(cursor.getInt(idIndex)));
                textName.setText(String.valueOf(cursor.getString(nameIndex)));
                textPhone.setText(String.valueOf(cursor.getString(phoneIndex)));
            }while (cursor.moveToNext());

        }else Toast.makeText(DBView.this,"0 rows",Toast.LENGTH_SHORT).show();

        //считываем записи с конкретной позиции (3)
        /*if(cursor.moveToPosition(3)){
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int phoneIndex = cursor.getColumnIndex(DBHelper.KEY_PHONE);
            textId.setText(String.valueOf(cursor.getInt(idIndex)));
            textName.setText(String.valueOf(cursor.getString(nameIndex)));
            textPhone.setText(String.valueOf(cursor.getString(phoneIndex)));
        }*/
        //освобождаем ресурсы курсора
        cursor.close();
        //закрываем соединение с БД
        dbHelper.close();

    }

    @Override
    public void onClick(View v) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (v.getId()){
            //считываем БД в логах
            case R.id.read:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS,null,null,null,null,null,null);
                if(cursor.moveToFirst()){

                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int phoneIndex = cursor.getColumnIndex(DBHelper.KEY_PHONE);

                    do{
                        Log.d("DB READER", "ID = " + cursor.getInt(idIndex) + " Name = " + cursor.getString(nameIndex) + " Phone = " + cursor.getString(phoneIndex) );
                    }while (cursor.moveToNext());

                }else Toast.makeText(DBView.this,"0 rows",Toast.LENGTH_SHORT).show();
                cursor.close();
                dbHelper.close();
            break;

    //Обновляем строку с конкретным ID в таблице
            case R.id.update:

                if (editId.getText().toString().equalsIgnoreCase("")){
                    break;
                }

                contentValues.put(DBHelper.KEY_NAME,editName.getText().toString());
                contentValues.put(DBHelper.KEY_PHONE,editPhone.getText().toString());

                int updCount = database.update(DBHelper.TABLE_CONTACTS,contentValues,DBHelper.KEY_ID + "= ?", new String[]{editId.getText().toString()});

                Toast.makeText(this, "updates row count=" + updCount, Toast.LENGTH_SHORT).show();

                break;

//Очищаем Таблицу в БД
            case R.id.clear:
                database.delete(DBHelper.TABLE_CONTACTS,null,null);
                break;

    //удаляем строку с конкретным ID в таблице
            case R.id.delete:
                if (editId.getText().toString().equalsIgnoreCase("")){
                    break;
                }

                int updDelete= database.delete(DBHelper.TABLE_CONTACTS,DBHelper.KEY_ID + "= " + editId.getText().toString(),null);

                Toast.makeText(this, "updates row count=" + updDelete, Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
