package com.example.dowloadjson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DowloadJSONTask task = new DowloadJSONTask();
        task.execute("https://samples.openweathermap.org/data/2.5/weather?q=Berlin&appid=9c9659565d23a170d9809d59ffb50d1a");
    }

    private static class DowloadJSONTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL(strings[0]);//получаем строку
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();//открываем соединение
                    InputStream inputStream = urlConnection.getInputStream();//получаем поток ввода
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String line = reader.readLine();//читаем первую строку
                    while (line != null){
                        result.append(line);
                        line = reader.readLine();
                    }
                    return result.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {//переопределяем метод для того чтобы можно было вевести данные
            super.onPostExecute(s);
            //только этот метод имеет доступ к elementam graph interfaces
            Log.i("MyResult",s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String name = jsonObject.getString("name"); // из этой большой строки получаем название города
                Log.i("MyResult",name);
                /*
                JSONObject main = jsonObject.getJSONObject("main");
                String temp = main.getString("temp");
                String pressure = main.getString("pressure");
                Log.i("MyResult", temp +" "+ pressure); тут мы получили по 2 значения температуры и давления*/
                JSONArray jsonArray = jsonObject.getJSONArray("weather");
                JSONObject weather = jsonArray.getJSONObject(0);
                String main = weather.getString("main");
                String description = weather.getString("description");
                Log.i("MyResult", main + description);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
