package com.popularmovies.utils;

import android.app.Application;

import com.popularmovies.storage.MoviesDatabase;

import java.sql.SQLException;

/**
 * Created by andiisfh on 31/07/17.
 */

public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();
    public static MoviesDatabase sMoviesDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        sMoviesDatabase = new MoviesDatabase(this);
        try {
            sMoviesDatabase.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {
        sMoviesDatabase.close();
        super.onTerminate();
    }

}
