package com.popularmovies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.popularmovies.R;
import com.popularmovies.net.Constant;
import com.popularmovies.storage.MoviesDatabase;
import com.popularmovies.ui.adapter.MoviesAdapter;
import com.popularmovies.ui.utils.CustomProgressDialog;
import com.popularmoviesdomain.model.movies.MoviesList;
import com.popularmoviesdomain.model.movies.MoviesModel;
import com.popularmoviesdomain.presenter.movies.MoviesImp;
import com.popularmoviesdomain.view.MoviesView;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MoviesView, MoviesAdapter.ListItemClickListener {

    @BindView(R.id.spin_category)
    Spinner mSpinCategory;
    @BindView(R.id.rv_movie_list)
    RecyclerView mRVMovieList;
    @BindView(R.id.tv_message)
    TextView mTVMessage;

    String from = "";

    @Override
    protected int setLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected String setTitle() {
        return getResources().getString(R.string.app_name);
    }

    @Override
    protected int setIconColor() {
        return 0;
    }

    @Override
    protected boolean isToolbarHasBack() {
        return false;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRVMovieList.setLayoutManager(gridLayoutManager);

        final MoviesImp moviesImp = new MoviesImp(this, this);

        mSpinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomProgressDialog.addDialog(MainActivity.this);

                if (position == 0) {
                    moviesImp.getPopularMovies(Constant.API_KEY);
                } else if (position == 1) {
                    moviesImp.getTopRateMovies(Constant.API_KEY);
                } else {
                    getFromLocalStorage();
                }

                from = getResources().getStringArray(R.array.category_arr)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onSuccess(MoviesModel moviesModel) {
        CustomProgressDialog.removeDialog();

        mRVMovieList.setVisibility(View.VISIBLE);
        mTVMessage.setVisibility(View.GONE);

        MoviesAdapter moviesAdapter = new MoviesAdapter(this, moviesModel.getMoviesLists(), this);
        mRVMovieList.setAdapter(moviesAdapter);
    }

    @Override
    public void onError(String message) {
        CustomProgressDialog.removeDialog();

        mTVMessage.setVisibility(View.VISIBLE);
        mRVMovieList.setVisibility(View.GONE);

        mTVMessage.setText(message);
    }

    @Override
    public void onListItemClick(MoviesList moviesList) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra("id", String.valueOf(moviesList.getId()));
        intent.putExtra("title", moviesList.getTitle());
        intent.putExtra("release_date", moviesList.getReleaseDate());
        intent.putExtra("rating", String.valueOf(moviesList.getVoteAverage()));
        intent.putExtra("synopsis", moviesList.getOverview());
        intent.putExtra("image_path", moviesList.getPosterPath());
        intent.putExtra("from", from);
        startActivity(intent);
    }

    private void getFromLocalStorage(){
        CustomProgressDialog.removeDialog();

        MoviesAdapter moviesAdapter = new MoviesAdapter(this, MoviesDatabase.mMoviesDataAccess.fetchAllMovies(), this);
        mRVMovieList.setAdapter(moviesAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(from.equalsIgnoreCase(getResources().getStringArray(R.array.category_arr)[2])){
            getFromLocalStorage();
        }
    }
}
