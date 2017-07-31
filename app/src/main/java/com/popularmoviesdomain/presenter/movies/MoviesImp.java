package com.popularmoviesdomain.presenter.movies;

import android.content.Context;

import com.popularmovies.R;
import com.popularmovies.net.Config;
import com.popularmoviesdomain.model.movies.MoviesModel;
import com.popularmoviesdomain.view.MoviesView;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andiisfh on 01/07/17.
 */

public class MoviesImp implements MoviesPresenter {

    private final Context mContext;
    private final MoviesView mMoviesView;

    public MoviesImp(Context context, MoviesView moviesView){
        mContext = context;
        mMoviesView = moviesView;
    }

    @Override
    public void getPopularMovies(String api_key) {
        Call<MoviesModel> call = Config.getApi().getPopularMovies(api_key);
        call.enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                if(response.isSuccessful()){
                    mMoviesView.onSuccess(response.body());
                }else {
                    mMoviesView.onError(mContext.getResources().getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                mMoviesView.onError(getError(t));
            }
        });
    }

    @Override
    public void getTopRateMovies(String api_key) {
        Call<MoviesModel> call = Config.getApi().getTopRatedMovies(api_key);
        call.enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                if(response.isSuccessful()){
                    mMoviesView.onSuccess(response.body());
                }else {
                    mMoviesView.onError(mContext.getResources().getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                mMoviesView.onError(getError(t));
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
