package dev.android.davidcalle3141.popular_movies_app.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import dev.android.davidcalle3141.popular_movies_app.R;
import dev.android.davidcalle3141.popular_movies_app.adapters.MovieAdapter;
import dev.android.davidcalle3141.popular_movies_app.ui.detail.DetailActivity;
import dev.android.davidcalle3141.popular_movies_app.utilities.InjectorUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler
{
    private RecyclerView.LayoutManager mLayoutManager;
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private MainActivityViewModel mViewModel;
    private int onOptionsSelectedPosition =1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.movie_RecyclerView);
        mLayoutManager =
                new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mMovieAdapter = new MovieAdapter(this, getApplicationContext());

        mRecyclerView.setAdapter(mMovieAdapter);

        MainViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(this.getApplicationContext());

        mViewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);

        mViewModel.getCurrentViewMovies().observe(MainActivity.this,
                movieEntry -> {
                    if(movieEntry != null){
                        mMovieAdapter.addMoviesList(movieEntry);
                        mMovieAdapter.notifyDataSetChanged();}

                });


    }

    @Override
    public void onItemClick(int position) {
        launchDetailActivity(position);
    }

    private void launchDetailActivity(int position){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("movieID", mMovieAdapter.getMovieData().get(position).getMovieID());

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
        if (id == R.id.action_settings_favorites) {
            populateUI(2);
            return true;
        }

        if(id == R.id.action_settings_popular){
            populateUI(1);
            return true;
        }
        if(id == R.id.action_settings_sortRating){
            populateUI(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void populateUI(int display){
        mViewModel.getPopularMovies().removeObservers(MainActivity.this);
        mViewModel.getRatingMovies().removeObservers(MainActivity.this);
        this.onOptionsSelectedPosition = display;

        switch (display){
            case 2:
                mViewModel.getFavoriteList().observe(MainActivity.this,
                        favoritesEntries -> {
                            if (favoritesEntries != null) {
                                mMovieAdapter.addFavoritesList(favoritesEntries);
                                mMovieAdapter.notifyDataSetChanged();
                            }
                        });
                break;
            case 1:
                mViewModel.getPopularMovies().observe(MainActivity.this,
                        movieEntry -> {
                           if(movieEntry != null){
                               mMovieAdapter.addMoviesList(movieEntry);
                               mMovieAdapter.notifyDataSetChanged();}

                        });
                break;
            case 0:
                mViewModel.getRatingMovies().observe(MainActivity.this,
                        movieEntry -> {
                            if(movieEntry != null){
                                mMovieAdapter.addMoviesList(movieEntry);
                                mMovieAdapter.notifyDataSetChanged();}

                        });
        }
    }


}