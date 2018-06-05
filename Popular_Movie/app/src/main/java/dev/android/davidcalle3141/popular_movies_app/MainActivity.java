package dev.android.davidcalle3141.popular_movies_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import dev.android.davidcalle3141.popular_movies_app.models.Movie;
import dev.android.davidcalle3141.popular_movies_app.utils.AsyncUtils;
import dev.android.davidcalle3141.popular_movies_app.adapters.MovieAdapter;
import dev.android.davidcalle3141.popular_movies_app.utils.NetworkUtils;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.movie_RecyclerView);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MovieAdapter(this, getApplicationContext());

        mRecyclerView.setAdapter(mAdapter);

        new AsyncUtils(mAdapter,"main").execute(NetworkUtils.popularMoviesUrl("en_Us", "840"));



    }

    @Override
    public void onItemClick(int position) {
        Context context = this;
        Log.v(TAG,"position" + position);
        launchDetailActivity(position);
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("moviePoster", mAdapter.getMovieData().get(position).getImage_url());
        intent.putExtra("movieName", mAdapter.getMovieData().get(position).getMovie_name());
        intent.putExtra("movieReleaseDate", mAdapter.getMovieData().get(position).getRelease_date());
        intent.putExtra("movieRating", mAdapter.getMovieData().get(position).getRating());
        intent.putExtra("moviePlot", mAdapter.getMovieData().get(position).getPlot_synopsis());
        intent.putExtra("movieID", mAdapter.getMovieData().get(position).getId());
        startActivity(intent);
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_settings_popular){
            new AsyncUtils(mAdapter,"main").execute(NetworkUtils.popularMoviesUrl("en_Us", "840"));

            return true;
        }
        if(id == R.id.action_settings_top){
            new AsyncUtils(mAdapter,"main").execute(NetworkUtils.ratingMoviesUrl("en_Us", "840"));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
