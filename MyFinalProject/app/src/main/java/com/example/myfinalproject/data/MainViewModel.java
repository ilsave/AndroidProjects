package com.example.myfinalproject.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static MovieDatabase database;
    private static LiveData<List<Movie>> movies;
    private LiveData<List<FavouriteMovie>> favouriteMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(getApplication());
        movies = database.movieDao().getAllmovies();
        favouriteMovies = database.movieDao().getAllFavouritemovies();
    }

    public void deleteAllMovies(){
        new DeleteMovieTask().execute();
    }

    public LiveData<List<FavouriteMovie>> getFavouriteMovies() {
        return favouriteMovies;
    }
    public void insertFavouriteMovie(FavouriteMovie movie){
        new InsertFavouriteTask().execute(movie);
    }

    public void deleteFavouriteMovie(FavouriteMovie movie){
        new DeleteFavouriteTask().execute(movie);
    }

    public void insertMovie(Movie movie){
        new InsertTask().execute(movie);
    }

    public void deleteMovie(Movie movie){
        new DeleteTask().execute(movie);
    }

    public static LiveData<List<Movie>> getMovies() {
        return movies;
    }

    private static  class DeleteFavouriteTask extends AsyncTask<FavouriteMovie, Void, Void>{
        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if (movies != null && movies.length > 0){
                database.movieDao().deleteFavouriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static  class InsertFavouriteTask extends AsyncTask<FavouriteMovie, Void, Void>{
        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if (movies != null && movies.length > 0){
                database.movieDao().insertFavouriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static  class DeleteTask extends AsyncTask<Movie, Void, Void>{
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0){
                database.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    private static  class InsertTask extends AsyncTask<Movie, Void, Void>{
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0){
                database.movieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

    private static  class DeleteMovieTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... integers) {
                database.movieDao().deleteAllMovies();
            return null;
        }
    }

    public Movie getMovieById(int id){
        try {
            return new getMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FavouriteMovie getFavouriteMovieById(int id){
        try {
            return new getFavouriteMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }



    private static  class getMovieTask extends AsyncTask<Integer, Void, Movie>{
        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                return database.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }
    private static  class getFavouriteMovieTask extends AsyncTask<Integer, Void, FavouriteMovie>{
        @Override
        protected FavouriteMovie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                return database.movieDao().getFavouriteMovieById(integers[0]);
            }
            return null;
        }
    }
}