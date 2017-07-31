package com.popularmovies.net;

import com.popularmoviesdomain.model.movies.MoviesModel;
import com.popularmoviesdomain.model.trailers.TrailersModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by andiisfh on 01/07/17.
 */

public interface API {

    @GET("/3/movie/popular")
    Call<MoviesModel> getPopularMovies(@Query("api_key") String api_key);

    @GET("/3/movie/top_rated")
    Call<MoviesModel> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("/3/movie/{id}/videos")
    Call<TrailersModel> getTrailers(@Path("id") String id, @Query("api_key") String api_key);

    @GET("/3/movie/{id}/reviews")
    Call<com.popularmoviesdomain.model.reviews.ReviewsModel> getReviews(@Path("id") String id, @Query("api_key") String api_key);
}
