package com.example.myfinalproject.utils;

import android.util.Log;

import com.example.myfinalproject.data.Movie;
import com.example.myfinalproject.data.Review;
import com.example.myfinalproject.data.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//преобразование дзона в объект
public class JSONUtils {

    private static final String KEY_RESULTS = "results";//по этому ключу получаем массив array

    //for reviews
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_CONTENT = "content";

    //for Trailers(videos)
    public static final String KEY_KEY_OF_VIDEO = "key";
    public static final String KEY_NAME = "name";
    public static final String BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    //the all info about movie
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";

    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_POSTER_SIZE = "w185";
    public static final String BIG_POSTER_SIZE = "w780";

    public static ArrayList<Review>  getReviewsFromJSON(JSONObject jsonObject){
        ArrayList<Review> result = new ArrayList<>();
        if (jsonObject == null){
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject1Review = jsonArray.getJSONObject(i);
                String author = jsonObject1Review.getString(KEY_AUTHOR);
                String content = jsonObject1Review.getString(KEY_CONTENT);
                Review review = new Review(author, content);
                result.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Trailer>  getTrailersFromJSON(JSONObject jsonObject){
        ArrayList<Trailer> result = new ArrayList<>();
        if (jsonObject == null){
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObjectTrailer = jsonArray.getJSONObject(i);
                String key = BASE_YOUTUBE_URL+ jsonObjectTrailer.getString(KEY_KEY_OF_VIDEO);
                //чтобы получить ссылку на ютьюб
                String name = jsonObjectTrailer.getString(KEY_NAME);
                Trailer trailer = new Trailer(key, name);
                result.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //сделав запрос к бд надо получить массив с фильмami
    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject){
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null){
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject objectMovie = jsonArray.getJSONObject(i);
                int id = objectMovie.getInt(KEY_ID);
                int voteCount = objectMovie.getInt(KEY_VOTE_COUNT);
                String title = objectMovie.getString(KEY_TITLE);
                String orignalTitle = objectMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = objectMovie.getString(KEY_OVERVIEW);
                String posterPath =  BASE_POSTER_URL + SMALL_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String bigposterPath =  BASE_POSTER_URL + BIG_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String backdropPath = objectMovie.getString(KEY_BACKDROP_PATH);
                double voteAverage = objectMovie.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = objectMovie.getString(KEY_RELEASE_DATE);
                Movie movie = new Movie(id, voteCount, title, orignalTitle, overview, posterPath,bigposterPath, backdropPath, voteAverage, releaseDate);
                result.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
