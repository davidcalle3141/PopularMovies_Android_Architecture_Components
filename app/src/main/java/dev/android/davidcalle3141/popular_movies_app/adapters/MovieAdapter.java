package dev.android.davidcalle3141.popular_movies_app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.R;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<MovieEntry> movies;
    private Context context;
    private String JsonMovieString;
    private final MovieAdapterOnClickHandler mClickHandler;

    public void addMoviesList(List<MovieEntry> mainMoviesEntries) {
        movies = mainMoviesEntries;

    }



    public interface MovieAdapterOnClickHandler {
        void onItemClick(int position);}


    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, Context context){
        mClickHandler = clickHandler;
        movies = new ArrayList<>();
        this.context = context;
    }





    public String getJsonMovieString(){
        return JsonMovieString;
    }
    public List<MovieEntry> getMovieData(){
        return movies;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_main_row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Picasso.with(context).load( movies.get(i).getImage_url()).into(viewHolder.img_movie);




    }

    @Override
    public int getItemCount() {
        return movies.size();

    }









    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img_movie;


        private ViewHolder(View view) {
            super(view);
            img_movie = view.findViewById(R.id.image_Iv);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            mClickHandler.onItemClick(adapterPosition);
        }
    }




}