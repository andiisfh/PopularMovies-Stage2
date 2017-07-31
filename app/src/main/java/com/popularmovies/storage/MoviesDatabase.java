package com.popularmovies.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by andiisfh on 31/07/17.
 */

public class MoviesDatabase {

    private static final String TAG = "MoviesDatabase";
    private static final String DATABASE_NAME = "movies_database.db";
    private DatabaseHelper mDbHelper;
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;
    public static MoviesDataAccess mMoviesDataAccess;

    public MoviesDatabase open() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

        mMoviesDataAccess = new MoviesDataAccess(mDb);

        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public MoviesDatabase(Context context) {
        this.mContext = context;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MoviesSchema.MOVIES_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            Log.w(TAG, "Upgrading database from version "
                    + oldVersion + " to "
                    + newVersion + " which destroys all old data");

            db.execSQL("DROP TABLE IF EXISTS "
                    + MoviesSchema.MOVIES_TABLE);
            onCreate(db);

        }
    }

}
