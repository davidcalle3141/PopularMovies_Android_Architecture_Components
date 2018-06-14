package dev.android.davidcalle3141.popular_movies_app.data.network;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

public class ReviewsAndTrailersResponse {

        private final List<HashMap<String, String>> mReviews;

        ReviewsAndTrailersResponse(@NonNull final List<HashMap<String, String>> reviews) {
            this.mReviews = reviews;
        }

        public List<HashMap<String, String>> getResponse() {
            return mReviews;
        }

}