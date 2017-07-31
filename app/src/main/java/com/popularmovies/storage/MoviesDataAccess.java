package com.popularmovies.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.popularmoviesdomain.model.movies.MoviesList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andiisfh on 31/07/17.
 */

public class MoviesDataAccess extends DbContentProvider implements MoviesSchema, MoviesDataAccessInterface {

    private Cursor cursor;
    private ContentValues initialValues;

    public MoviesDataAccess(SQLiteDatabase db) {
        super(db);
    }

    public MoviesList fetchMoviesById(int id) {
        final String selectionArgs[] = {String.valueOf(id)};
        final String selection = COLUMN_ID + " = ?";
        MoviesList user = new MoviesList();
        cursor = super.query(MOVIES_TABLE, MOVIES_COLUMNS, selection, selectionArgs, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                user = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return user;
    }

    public List<MoviesList> fetchAllMovies() {
        List<MoviesList> userList = new ArrayList<MoviesList>();
        cursor = super.query(MOVIES_TABLE, MOVIES_COLUMNS, null,
                null, COLUMN_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                MoviesList user = cursorToEntity(cursor);
                userList.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return userList;
    }

    @Override
    public boolean addMovies(MoviesList moviesList) {
        setContentValue(moviesList);
        try {
            return super.insert(MOVIES_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex) {
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteMoviesById(int id) {
        super.delete(MOVIES_TABLE, COLUMN_ID + " = ?", new String[]{Integer.toString(id)});
        return false;
    }

    protected MoviesList cursorToEntity(Cursor cursor) {

        MoviesList moviesList = new MoviesList();

        int idIndex;
        int titleIndex;
        int voteAverageIndex;
        int posterPathIndex;
        int overviewIndex;
        int releaseDateIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                moviesList.id = cursor.getInt(idIndex);
            }
            if (cursor.getColumnIndex(COLUMN_TITLE) != -1) {
                titleIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE);
                moviesList.title = cursor.getString(titleIndex);
            }
            if (cursor.getColumnIndex(COLUMN_VOTE_AVERAGE) != -1) {
                voteAverageIndex = cursor.getColumnIndexOrThrow(COLUMN_VOTE_AVERAGE);
                moviesList.voteAverage = cursor.getDouble(voteAverageIndex);
            }
            if (cursor.getColumnIndex(COLUMN_POSTER_PATH) != -1) {
                posterPathIndex = cursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH);
                moviesList.posterPath = cursor.getString(posterPathIndex);
            }
            if (cursor.getColumnIndex(COLUMN_OVERVIEW) != -1) {
                overviewIndex = cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW);
                moviesList.overview = cursor.getString(overviewIndex);
            }
            if (cursor.getColumnIndex(COLUMN_RELEASE_DATE) != -1) {
                releaseDateIndex = cursor.getColumnIndexOrThrow(COLUMN_RELEASE_DATE);
                moviesList.releaseDate = cursor.getString(releaseDateIndex);
            }
        }
        return moviesList;
    }

    private void setContentValue(MoviesList moviesList) {
        initialValues = new ContentValues();
        initialValues.put(COLUMN_ID, moviesList.id);
        initialValues.put(COLUMN_TITLE, moviesList.title);
        initialValues.put(COLUMN_VOTE_AVERAGE, moviesList.voteAverage);
        initialValues.put(COLUMN_POSTER_PATH, moviesList.posterPath);
        initialValues.put(COLUMN_OVERVIEW, moviesList.overview);
        initialValues.put(COLUMN_RELEASE_DATE, moviesList.releaseDate);
    }

    private ContentValues getContentValue() {
        return initialValues;
    }


}
