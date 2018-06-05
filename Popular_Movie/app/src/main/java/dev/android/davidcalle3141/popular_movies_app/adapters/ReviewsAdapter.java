package dev.android.davidcalle3141.popular_movies_app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import dev.android.davidcalle3141.popular_movies_app.R;
import dev.android.davidcalle3141.popular_movies_app.models.Movie;
import dev.android.davidcalle3141.popular_movies_app.utils.MovieJsonUtils;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder>{
    private Context context;
    private ArrayList<HashMap <String,String>> mMovieReviews;

    public ReviewsAdapter(Context context){
        this.context = context;
        mMovieReviews = new ArrayList<>();
    }

    public void setMovieReviews(String JsonReviewString){
        mMovieReviews= MovieJsonUtils.parseMovieReviewsJson(JsonReviewString);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_detail_reviews_row, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = mMovieReviews.get(position).get("author");
        String comment = mMovieReviews.get(position).get("content");

        holder.review_name.setText(name);
        holder.review_comment.setText(comment);


    }

    @Override
    public int getItemCount() {
        return mMovieReviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView review_name;
        private TextView review_comment;


        private ViewHolder(View view) {
            super(view);
            review_name = view.findViewById(R.id.review_name);
            review_comment = view.findViewById(R.id.review_comment);

        }


    }
}
