package com.example.myfinalproject.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.webkit.HttpAuthHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

//вся работа связанная с сетью
public class NetworkUtils {

    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_POSTER_SIZE = "w185";
    public static final String BIG_POSTER_SIZE = "w780";

    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    public static final String BASE_URL_VIDEOS = "https://api.themoviedb.org/3/movie/%s/videos";
    //%S for string format
    public static final String BASE_URL_REVIEWS = "https://api.themoviedb.org/3/movie/%s/reviews";

    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String PARAMS_SORT_BY = "sort_by";
    private static final String PARAMS_PAGE ="page";
    public static final String PARAMS_MIN_VOTE_COUNT = "vote_count.gte";

    private static final String API_KEY = "dc7a593b1d03c7631f5d7a41a28b39fa";

    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_TOP_RATED = "vote_average.desc";
    public static final String MIN_VOTE_COUNT_VALUE = "1000";

    public static final int POPULARITY = 0;
    public static final int TOP_RATED = 1;


    public static URL buildURLToReviews(int id, String language){
        Uri uri = Uri.parse(String.format(BASE_URL_REVIEWS, id)).buildUpon()
                .appendQueryParameter(PARAMS_LANGUAGE, language)
                .appendQueryParameter(PARAMS_API_KEY, API_KEY).build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONForReviews(int id, String language){
        URL url = buildURLToReviews(id, language);
        JSONObject result = null;
        try {
            result =  new JSONLoad().execute(url).get();//get чтобы получить джейсон объект
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static URL buildURLToVideos(int id, String language){
        Uri uri = Uri.parse(String.format(BASE_URL_VIDEOS, id)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, language).build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONForVideos(int id, String language){
        URL url = buildURLToVideos(id, language);
        JSONObject result = null;
        try {
            result =  new JSONLoad().execute(url).get();//get чтобы получить джейсон объект
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static URL buildURL(int sortBy, int page, String language){
        URL result = null;
        String methodOfSort;
        if (sortBy == POPULARITY){
            methodOfSort = SORT_BY_POPULARITY;
        } else{
            methodOfSort = SORT_BY_TOP_RATED;
        }
        //получили строку в виде адреса
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, language)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_MIN_VOTE_COUNT,MIN_VOTE_COUNT_VALUE)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))
                .build();

        try {
             result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }


    //метод который получает джон из сети
    public static JSONObject getJSONFromNetwork(int sortBy, int page, String language){
        URL url = buildURL(sortBy, page, language);
        JSONObject result = null;
        try {
            result =  new JSONLoad().execute(url).get();//get чтобы получить джейсон объект
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }


    //класс чтобы при повороте экрана не будет вылетать приложение
    public static class JSONLoader extends AsyncTaskLoader<JSONObject>{
        //для того чтобы передавать значения при повороте экрана (мы будем передавать url)
        private Bundle bundle;
        private OnStartLoadingListener onStartLoadingListener;

        public void setOnStartLoadingListener(OnStartLoadingListener onStartLoadingListener) {
            this.onStartLoadingListener = onStartLoadingListener;
        }

        public interface OnStartLoadingListener{
            void onStartLoading();
        }

        public JSONLoader(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }

        //чтобы при инициальзации загрузчика происходила загрузка нужно прописать onStartLoading
        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (onStartLoadingListener != null){
                onStartLoadingListener.onStartLoading();
            }

            //команда для продолжения загрузки
            forceLoad();
        }

        @Nullable
        @Override
        public JSONObject loadInBackground() {
            if (bundle == null){
                return null;
            }
            String urlAsString = bundle.getString("url");
            URL url = null;
            try {
                url = new URL(urlAsString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //а дальше из этого юрл надо загрузить данные, это мы уже делали в JSONLoadTask

            JSONObject result = null;
            if (url == null ){
                return null;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();//открываем соединение
                InputStream inputStream = connection.getInputStream();//создает поток ввода
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);//чтобы читать строками
                String line = reader.readLine();
                StringBuilder builder = new StringBuilder();
                while(line != null){
                    builder.append(line);
                    line = reader.readLine();
                }
                try {
                    result = new JSONObject(builder.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.disconnect();
                }
            }
            return result;

        }
    }

    //загружаем из интернета
    private static class JSONLoad extends AsyncTask<URL, Void, JSONObject>{
        //принимает юрл. в процессе выполнения данные нам не нужны, и возвращает JSONObject
        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0){
                return result;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();//открываем соединение
                InputStream inputStream = connection.getInputStream();//создает поток ввода
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);//чтобы читать строками
                String line = reader.readLine();
                StringBuilder builder = new StringBuilder();
                while(line != null){
                    builder.append(line);
                    line = reader.readLine();
                }
                try {
                    result = new JSONObject(builder.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.disconnect();
                }
            }
            return result;
        }
    }

}
