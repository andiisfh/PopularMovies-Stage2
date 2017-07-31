package com.popularmovies.storage;

/**
 * Created by andiisfh on 31/07/17.
 */

public interface MoviesSchema {

    String MOVIES_TABLE = "movies";
    String COLUMN_ID = "id";
    String COLUMN_TITLE = "title";
    String COLUMN_VOTE_AVERAGE = "vote_average";
    String COLUMN_POSTER_PATH = "poster_path";
    String COLUMN_OVERVIEW = "overview";
    String COLUMN_RELEASE_DATE = "release_date";

    String MOVIES_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + MOVIES_TABLE
            + " ("
            + COLUMN_ID
            + " BIGINT PRIMARY KEY, "
            + COLUMN_TITLE
            + " TEXT, "
            + COLUMN_VOTE_AVERAGE
            + " REAL,"
            + COLUMN_POSTER_PATH
            + " TEXT,"
            + COLUMN_OVERVIEW
            + " TEXT,"
            + COLUMN_RELEASE_DATE
            + " TEXT "
            + ")";

    String[] MOVIES_COLUMNS = new String[]{COLUMN_ID,
            COLUMN_TITLE, COLUMN_VOTE_AVERAGE, COLUMN_POSTER_PATH,
            COLUMN_OVERVIEW, COLUMN_RELEASE_DATE};

}
