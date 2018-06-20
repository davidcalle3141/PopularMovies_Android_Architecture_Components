
package dev.android.davidcalle3141.popular_movies_app.ui.detail;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.R;
import dev.android.davidcalle3141.popular_movies_app.adapters.ReviewsAdapter;
import dev.android.davidcalle3141.popular_movies_app.adapters.TrailersAdapter;
import dev.android.davidcalle3141.popular_movies_app.utilities.InjectorUtils;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.TrailerAdapterOnClickHandler {
    Context mContext;
    private DetailActivityViewModel mViewModel;
    private RecyclerView mReviewRV;
    private RecyclerView mTrailerRV;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReviewsAdapter mReviewAdapter;
    private TrailersAdapter mTrailerAdapter;
    private static Boolean saved;




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



        populateRV();
        populateUI(mContext);





    }
    private void populateRV(){
        mViewModel.getmReviews().observe(this,
                reviews ->{
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

    }

    private void populateUI(Context mContext) {

        TextView movieName = findViewById(R.id.detail_movie_title);
        ImageView moviePoster = findViewById(R.id.poster_thumbnail);
        TextView movieReleaseDate = findViewById(R.id.movie_release_date);
        TextView movieRating = findViewById(R.id.movie_rating);
        TextView moviePlot= findViewById(R.id.movie_synopsis);
        Button favoriteButton = findViewById(R.id.favorite_button);

        mViewModel.getMovieEntry().observe(this,
                movie ->{

                    assert movie != null;
                    Picasso.with(mContext).load(movie.getImage_url()).into(moviePoster);

                    movieName.setText(movie.getMovie_name());
                    movieReleaseDate.setText(movie.getRelease_date());
                    movieRating.setText(String.valueOf(movie.getRating()));
                    moviePlot.setText(movie.getPlot_synopsis());

                });



        mViewModel.getmFavoriteEntry().observe(this,
                isFavorite ->{
            if(isFavorite!=null) {
                    favoriteButton.setText(R.string.saved);
                    mViewModel.setIsFavorite(true);
                } else {favoriteButton.setText(R.string.save);
                    mViewModel.setIsFavorite(false);}
                });

        favoriteButton.setOnClickListener(new FavoriteButtonClick());



    }

    ///////////////////////////////////////////////////////////////////
    //trailers onitemclick
    @Override
    public void onItemClick(int position) {
        Intent sendIntend = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + mTrailerAdapter.getYoutubeLink(position)));
        try {
            PackageManager packageManager = getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(sendIntend,
                    PackageManager.MATCH_DEFAULT_ONLY);
            boolean isIntentSafe = activities.size() > 0;


            if (isIntentSafe) {
                startActivity(sendIntend);
            }
        } catch (Exception e) {
            //TODO handle no app to open intent available
        }
    }
    //////////////////////////////////////////////////////////////////

    class FavoriteButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Log.d("CLICKKR", "been clicked");


            if(mViewModel.getIsFavoriteBoolean()){
                mViewModel.removeFavorite();}
            else { mViewModel.addFavorite();}


        }
    }

}
