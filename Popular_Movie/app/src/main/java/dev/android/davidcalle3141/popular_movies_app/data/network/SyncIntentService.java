package dev.android.davidcalle3141.popular_movies_app.data.network;

import android.app.IntentService;
import android.content.Intent;


import dev.android.davidcalle3141.popular_movies_app.utilities.InjectorUtils;

public class SyncIntentService extends IntentService{
    public SyncIntentService(){
        super("SyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        MovieNetworkDataSource networkDataSource =
                InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchMovies();
    }
}
