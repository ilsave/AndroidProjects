package com.example.myfinalproject.data;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

//обозначили, что это таблица в базе данных
@Entity(tableName = "favourite_movies")
public class FavouriteMovie extends Movie {
    public FavouriteMovie(int uniqueId, int id, int voteCount, String title, String originalTitle, String overview, String bigPosterPath, String posterPath, String backdropPath, double voteAverage, String releaseDate) {
        super(uniqueId, id, voteCount, title, originalTitle, overview, bigPosterPath, posterPath, backdropPath, voteAverage, releaseDate);
    }

    //это для преобразования обычного кино в избранное
    @Ignore
    public FavouriteMovie(Movie movie){
        super(movie.getUniqueId(), movie.getId(), movie.getVoteCount(), movie.getTitle(), movie.getOriginalTitle(), movie.getOverview(), movie.getPosterPath(), movie.getBigPosterPath(), movie.getBackdropPath(), movie.getVoteAverage(), movie.getReleaseDate());
    }
}
