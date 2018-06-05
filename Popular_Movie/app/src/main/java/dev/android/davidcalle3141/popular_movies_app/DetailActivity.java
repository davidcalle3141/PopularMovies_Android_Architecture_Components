
package dev.android.davidcalle3141.popular_movies_app;

import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;


import dev.android.davidcalle3141.popular_movies_app.adapters.ReviewsAdapter;
import dev.android.davidcalle3141.popular_movies_app.utils.AsyncUtils;
import dev.android.davidcalle3141.popular_movies_app.utils.NetworkUtils;

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity {
    private RecyclerView mReviewRV;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReviewsAdapter mReviewAdapter;

    Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context = this;
        Intent intent = getIntent();
        if(intent == null){
            Log.v(TAG,"detail activity closed on error null intent");
            return;
        }

        populateUI(intent, context);


        mReviewRV = findViewById(R.id.review_RecyclerView);
        mReviewRV.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewRV.setLayoutManager(mLayoutManager);
        mReviewAdapter = new ReviewsAdapter(getApplicationContext());
        mReviewRV.setAdapter(mReviewAdapter);

        new AsyncUtils(mReviewAdapter,"reviews")
                .execute(NetworkUtils.movieReviewsUrl(intent.getStringExtra("movieID"),"en_Us", "840"));





    }

    private void populateUI(Intent intent, Context context) {
        TextView movieName = findViewById(R.id.detail_movie_title);
        ImageView moviePoster = findViewById(R.id.poster_thumbnail);
        TextView movieReleaseDate = findViewById(R.id.movie_release_date);
        TextView movieRating = findViewById(R.id.movie_rating);
        TextView moviePlot= findViewById(R.id.movie_synopsis);

        Picasso.with(context).load(intent.getStringExtra("moviePoster")).into(moviePoster);

        movieName.setText(intent.getStringExtra("movieName"));
        movieReleaseDate.setText(intent.getStringExtra("movieReleaseDate"));
        movieRating.setText(intent.getStringExtra("movieRating"));
        moviePlot.setText(intent.getStringExtra("moviePlot"));

    }

}
