package dev.android.davidcalle3141.popular_movies_app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import dev.android.davidcalle3141.popular_movies_app.R;
import dev.android.davidcalle3141.popular_movies_app.data.network.MovieJsonUtils;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder>{
    private final Context context;
    private ArrayList<HashMap<String,String>> mTrailers;


    public TrailersAdapter(Context context){
        this.context = context;
        mTrailers = new ArrayList<>();
    }

    public void setMovieTrailers(String JsonTrailerString){
        mTrailers = MovieJsonUtils.parseMovieTrailerJson(JsonTrailerString);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_detail_trailers_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        position ++;
        String name = "Trailer "+ position ;
        holder.trailer_name.setText(name);
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView trailer_name;


        private ViewHolder(View view) {
            super(view);
            trailer_name = view.findViewById(R.id.trailer_name);

        }


    }
}
