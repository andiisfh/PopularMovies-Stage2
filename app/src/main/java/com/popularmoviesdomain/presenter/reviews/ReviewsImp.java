package com.popularmoviesdomain.presenter.reviews;

import android.content.Context;

import com.popularmovies.R;
import com.popularmovies.net.Config;
import com.popularmoviesdomain.model.reviews.ReviewsModel;
import com.popularmoviesdomain.view.ReviewsView;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andiisfh on 31/07/17.
 */

public class ReviewsImp implements ReviewsPresenter {

    private final Context mContext;
    private final ReviewsView mReviewsView;

    public ReviewsImp(Context context, ReviewsView moviesView) {
        mContext = context;
        mReviewsView = moviesView;
    }
    
    @Override
    public void getReviews(String movie_id, String api_key) {
        Call<ReviewsModel> call = Config.getApi().getReviews(movie_id, api_key);
        call.enqueue(new Callback<ReviewsModel>() {
            @Override
            public void onResponse(Call<ReviewsModel> call, Response<ReviewsModel> response) {
                if (response.isSuccessful()) {
                    mReviewsView.onSuccess(response.body());
                } else {
                    mReviewsView.onError(mContext.getResources().getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<ReviewsModel> call, Throwable t) {
                mReviewsView.onError(getError(t));
            }
        });
    }

    private String getError(Throwable t) {
        String message = "";
        if (t instanceof SocketTimeoutException) {
            message = mContext.getResources().getString(R.string.rto);
        } else if (t instanceof ConnectException || t instanceof UnknownHostException || t instanceof TimeoutException || t instanceof SocketException) {
            message = mContext.getResources().getString(R.string.no_internet);
        } else {
            if (t.getMessage() != null)
                message = t.getMessage();
        }

        return message;
    }
}
