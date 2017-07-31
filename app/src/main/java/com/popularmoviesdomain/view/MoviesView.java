package com.popularmoviesdomain.view;

import com.popularmoviesdomain.model.movies.MoviesModel;

/**
 * Created by andiisfh on 01/07/17.
 */

public interface MoviesView {
    void onSuccess(MoviesModel moviesModel);
    void onError(String message);
}
