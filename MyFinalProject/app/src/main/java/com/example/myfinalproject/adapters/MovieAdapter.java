package com.example.myfinalproject.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myfinalproject.R;
import com.example.myfinalproject.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private List<Movie> movies;
    private OnPosterClickListener onPosterClickListener;
    private OnReachEndListener onReachEndListener;

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public MovieAdapter(){
        movies = new ArrayList<>();
    }



    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public interface OnPosterClickListener{
        void onPosterClick(int position);
    }

    public interface OnReachEndListener{
        void OnReachEnd();//вызывается при достижении конца листа
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(view);
    }


    //связывает вьюхолдер с позицией элемента
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
    //берем imageView и устанавливаем значение из фильма
    Movie movie = movies.get(i);
    Picasso.get().load(movie.getPosterPath()).into(movieViewHolder.imageViewSmallPoster);
    //вставка во вью картинки по пути постера
     if (movies.size() >= 20 && i > movies.size()- 4 && onReachEndListener !=null){//почти конец списка и слушатель не пустой
        onReachEndListener.OnReachEnd();
     }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewSmallPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.ImageViewSmallPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPosterClickListener != null){
                        onPosterClickListener.onPosterClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void clear(){
        this.movies.clear();
        notifyDataSetChanged();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();//после добавки работает
    }

    //когда будем листать список то мы будем добавлять фильмы в этот же массив, поэтому
    public void addMovies(ArrayList<Movie> movies){
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
