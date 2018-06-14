
package dev.android.davidcalle3141.popular_movies_app.ui.detail;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;


import dev.android.davidcalle3141.popular_movies_app.R;
import dev.android.davidcalle3141.popular_movies_app.adapters.ReviewsAdapter;
import dev.android.davidcalle3141.popular_movies_app.adapters.TrailersAdapter;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;
import dev.android.davidcalle3141.popular_movies_app.ui.main.MainActivity;
import dev.android.davidcalle3141.popular_movies_app.utilities.InjectorUtils;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.TrailerAdapterOnClickHandler {
    Context mContext;
    private DetailActivityViewModel mViewModel;
    private RecyclerView mReviewRV;
    private RecyclerView mTrailerRV;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReviewsAdapter mReviewAdapter;
    private TrailersAdapter mTrailerAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mContext=this;
        Intent intent = getIntent();




        mReviewAdapter = new ReviewsAdapter(this);
        mTrailerAdapter = new TrailersAdapter(this,this);
        mReviewRV = findViewById(R.id.review_RecyclerView);
        mTrailerRV = findViewById(R.id.trailer_RecyclerView);
        mReviewRV.setNestedScrollingEnabled(false);
        mTrailerRV.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mTrailerRV.setLayoutManager(mLayoutManager);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mReviewRV.setLayoutManager(mLayoutManager);
        mTrailerRV.setAdapter(mTrailerAdapter);
        mReviewRV.setAdapter(mReviewAdapter);


        DetailViewModelFactory factory = InjectorUtils.provideDetailActivityViewModelFactory(this.getApplicationContext(), intent.getStringExtra("movieID"));
        mViewModel = ViewModelProviders.of(this, factory).get(DetailActivityViewModel.class);

        mViewModel.getmReviews().observe(this,
                reviews ->{
            int c = 0;
            if(reviews!= null){
            mReviewAdapter.setMovieReviews(reviews);
            mReviewAdapter.notifyDataSetChanged();
                }});
        mViewModel.getmTrailers().observe(this,
                trailers ->{
            if(trailers!= null){
            mTrailerAdapter.setMovieTrailers(trailers);
            mTrailerAdapter.notifyDataSetChanged();
                }});

        populateUI(intent, mContext);





    }

    private void populateUI(Intent intent, Context mContext) {

        TextView movieName = findViewById(R.id.detail_movie_title);
        ImageView moviePoster = findViewById(R.id.poster_thumbnail);
        TextView movieReleaseDate = findViewById(R.id.movie_release_date);
        TextView movieRating = findViewById(R.id.movie_rating);
        TextView moviePlot= findViewById(R.id.movie_synopsis);

        Picasso.with(mContext).load(intent.getStringExtra("moviePoster")).into(moviePoster);

        movieName.setText(intent.getStringExtra("movieName"));
        movieReleaseDate.setText(intent.getStringExtra("movieReleaseDate"));
        movieRating.setText(String.valueOf(intent.getDoubleExtra("movieRating", -1)));
        moviePlot.setText(intent.getStringExtra("moviePlot"));




    }


    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://"+ mTrailerAdapter.getYoutubeLink(position))));

    }
}
