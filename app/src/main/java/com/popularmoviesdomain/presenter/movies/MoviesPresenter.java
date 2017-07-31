package com.popularmoviesdomain.presenter.movies;

/**
 * Created by andiisfh on 01/07/17.
 */

interface MoviesPresenter {
    void getPopularMovies(String api_key);
    void getTopRateMovies(String api_key);
}
