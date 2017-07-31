package com.popularmoviesdomain.presenter.trailers;

import android.content.Context;

import com.popularmovies.R;
import com.popularmovies.net.Config;
import com.popularmoviesdomain.model.trailers.TrailersModel;
import com.popularmoviesdomain.view.TrailersView;

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

public class TrailersImp implements TrailersPresenter {

    private final Context mContext;
    private final TrailersView mTrailersView;

    public TrailersImp(Context context, TrailersView moviesView) {
        mContext = context;
        mTrailersView = moviesView;
    }
    
    @Override
    public void getTrailers(String movie_id, String api_key) {
        Call<TrailersModel> call = Config.getApi().getTrailers(movie_id, api_key);
        call.enqueue(new Callback<TrailersModel>() {
            @Override
            public void onResponse(Call<TrailersModel> call, Response<TrailersModel> response) {
                if (response.isSuccessful()) {
                    mTrailersView.onSuccess(response.body());
                } else {
                    mTrailersView.onError(mContext.getResources().getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<TrailersModel> call, Throwable t) {
                mTrailersView.onError(getError(t));
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
