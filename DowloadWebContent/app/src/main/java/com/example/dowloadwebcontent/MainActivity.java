package com.example.dowloadwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private String mailRu ="https://mail.ru/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DowloandTask task = new DowloandTask();
        try {
            String result = task.execute(mailRu).get();//выполнили в другом потоке и получили данные (гет)
            Log.i("URL", result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static class DowloandTask extends AsyncTask<String, Void, String >{
        //1 стринг данные которые мы будем отправлять
        //данные которые будут передавать в процессе загрузки
        //данные которые будут возвращаться (htmlcode)
        @Override
        protected String doInBackground(String... strings) {
            Log.i("URL",strings[0]);
            StringBuilder result = new StringBuilder();
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);//polychaem url
                urlConnection = (HttpURLConnection) url.openConnection();//открываем соединение
                InputStream in = urlConnection.getInputStream(); // получение потока ввода чтобы читать ввод
                InputStreamReader reader = new InputStreamReader(in);//чтобы начать процесс чтения данных
                BufferedReader bufferedReader = new BufferedReader(reader);//чтобы читать строками
                String line = bufferedReader.readLine();//получили первую строчку
                while(line != null){
                    result.append(line);
                    line =  bufferedReader.readLine();//читаем следующую строку
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally { //в конце операции надо закрыть соединение
                if (urlConnection!=null){
                    urlConnection.disconnect();
                }
            }
            return result.toString();
        }
    }
}
