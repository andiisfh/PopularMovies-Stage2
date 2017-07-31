package com.popularmovies.storage;

import com.popularmoviesdomain.model.movies.MoviesList;

import java.util.List;

/**
 * Created by andiisfh on 31/07/17.
 */

public interface MoviesDataAccessInterface {

    MoviesList fetchMoviesById(int moviesId);

    List<MoviesList> fetchAllMovies();

    boolean addMovies(MoviesList moviesList);

    boolean deleteMoviesById(int moviesId);
}
