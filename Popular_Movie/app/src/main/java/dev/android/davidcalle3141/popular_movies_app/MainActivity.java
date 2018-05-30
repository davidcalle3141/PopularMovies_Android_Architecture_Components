package dev.android.davidcalle3141.popular_movies_app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

import dev.android.davidcalle3141.popular_movies_app.models.Movie;
import dev.android.davidcalle3141.popular_movies_app.utils.MovieAdapter;
import dev.android.davidcalle3141.popular_movies_app.utils.MovieJsonUtils;
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


        new MoviesQueryTask().execute(NetworkUtils.popularMoviesUrl("en_Us", "840"));



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
        startActivity(intent);
    }


    public class MoviesQueryTask extends AsyncTask<URL, Void, String>{


        @Override
        protected String doInBackground(URL... params) {
            int i =0;
            URL url = params[0];
            try{
                return NetworkUtils.getResponseFromHttpUrl(url);
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String moviesString) {
            super.onPostExecute(moviesString);
            if(moviesString != null){

                mAdapter.setMovieData(moviesString, "w185");
                mAdapter.notifyDataSetChanged();
            }
        }


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
            new MoviesQueryTask().execute(NetworkUtils.popularMoviesUrl("en_Us", "840"));

            return true;
        }
        if(id == R.id.action_settings_top){
            new MoviesQueryTask().execute(NetworkUtils.ratingMoviesUrl("en_Us", "840"));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
