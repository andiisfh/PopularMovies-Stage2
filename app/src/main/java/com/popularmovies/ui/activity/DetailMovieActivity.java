package com.popularmovies.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.popularmovies.R;
import com.popularmovies.net.Constant;
import com.popularmovies.storage.MoviesDatabase;
import com.popularmovies.ui.utils.ImageLoader;
import com.popularmovies.ui.utils.VideoLoader;
import com.popularmoviesdomain.model.movies.MoviesList;
import com.popularmoviesdomain.model.reviews.ReviewsModel;
import com.popularmoviesdomain.model.trailers.TrailersList;
import com.popularmoviesdomain.model.trailers.TrailersModel;
import com.popularmoviesdomain.presenter.reviews.ReviewsImp;
import com.popularmoviesdomain.presenter.trailers.TrailersImp;
import com.popularmoviesdomain.view.ReviewsView;
import com.popularmoviesdomain.view.TrailersView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by andiisfh on 01/07/17.
 */

public class DetailMovieActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTVTitle;
    @BindView(R.id.tv_release_date)
    TextView mTVReleaseDate;
    @BindView(R.id.tv_rating)
    TextView mTVRating;
    @BindView(R.id.tv_synopsis)
    TextView mTVSynopsis;
    @BindView(R.id.iv_thumbnail)
    ImageView mIVThumbnail;
    @BindView(R.id.ll_trailers)
    LinearLayout mLLTrailers;
    @BindView(R.id.ll_reviews)
    LinearLayout mLLReviews;
    @BindView(R.id.btn_favorite)
    Button mBtnFavorite;

    private String from, id, title, relase_date, rating, synopsis, image_path;

    private ProgressDialog mProgressDialog;

    @Override
    protected int setLayoutResource() {
        return R.layout.activity_detail;
    }

    @Override
    protected String setTitle() {
        return getResources().getString(R.string.movie_detail);
    }

    @Override
    protected int setIconColor() {
        return ContextCompat.getColor(this, android.R.color.white);
    }

    @Override
    protected boolean isToolbarHasBack() {
        return true;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {

        from = getIntent().getExtras().getString("from");
        id = getIntent().getExtras().getString("id");
        title = getIntent().getExtras().getString("title");
        relase_date = getIntent().getExtras().getString("release_date");
        rating = getIntent().getExtras().getString("rating");
        synopsis = getIntent().getExtras().getString("synopsis");
        image_path = getIntent().getExtras().getString("image_path");

        if(from.equalsIgnoreCase(getResources().getStringArray(R.array.category_arr)[2])){
            mBtnFavorite.setText(getResources().getString(R.string.remove_from_favorite));
        }else {
            mBtnFavorite.setText(getResources().getString(R.string.mark_as_favorite));
        }

        mTVTitle.setText(title);
        mTVReleaseDate.setText(relase_date);
        mTVRating.setText(getResources().getString(R.string.of_ten, rating));
        mTVSynopsis.setText(synopsis);
        ImageLoader.load(this, image_path, mIVThumbnail);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.loading_please_wait));
        mProgressDialog.show();

        getTrailers();
    }

    private void getTrailers() {
        (new TrailersImp(this, new TrailersView() {
            @Override
            public void onSuccess(final TrailersModel trailersModel) {
                LayoutInflater inflater = LayoutInflater.from(DetailMovieActivity.this);

                for (int i = 0; i < trailersModel.getResults().size(); i++) {
                    final TrailersList trailersList = trailersModel.getResults().get(i);

                    View v = inflater.inflate(R.layout.item_trailers, mLLTrailers, false);

                    ImageView ivTrailerThumbnail = (ImageView) v.findViewById(R.id.iv_trailer_thumbnail);
                    TextView tvTrailerTitle = (TextView) v.findViewById(R.id.tv_trailer_title);

                    ImageLoader.loadYoutubeThumbnail(getApplicationContext(), trailersList.getKey(), ivTrailerThumbnail);
                    tvTrailerTitle.setText(trailersList.getName());

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VideoLoader.loadYoutubeVideo(trailersList.getKey())));
                            startActivity(intent);
                        }
                    });

                    mLLTrailers.addView(v);
                }

                getReviews();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(DetailMovieActivity.this, message, Toast.LENGTH_SHORT).show();
                getReviews();
            }
        })).getTrailers(id, Constant.API_KEY);
    }

    private void getReviews() {
        (new ReviewsImp(this, new ReviewsView() {
            @Override
            public void onSuccess(ReviewsModel trailersModel) {
                LayoutInflater inflater = LayoutInflater.from(DetailMovieActivity.this);

                for (int i = 0; i < trailersModel.getResults().size(); i++) {
                    View v = inflater.inflate(R.layout.item_reviews, mLLReviews, false);

                    TextView tvTrailerAuthor = (TextView) v.findViewById(R.id.tv_reviews_author);
                    TextView tvTrailerContent = (TextView) v.findViewById(R.id.tv_reviews_content);

                    tvTrailerAuthor.setText(trailersModel.getResults().get(i).getAuthor());
                    tvTrailerContent.setText(trailersModel.getResults().get(i).getContent());

                    mLLReviews.addView(v);
                }

                mProgressDialog.dismiss();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(DetailMovieActivity.this, message, Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        })).getReviews(id, Constant.API_KEY);
    }

    @OnClick(R.id.btn_favorite)
    void favorite() {
        if(from.equalsIgnoreCase(getResources().getStringArray(R.array.category_arr)[2])){
            boolean isRemoved = MoviesDatabase.mMoviesDataAccess.deleteMoviesById(Integer.valueOf(id));
            Log.d("is removed", isRemoved + "");

            onBackPressed();

            Toast.makeText(this, getResources().getString(R.string.remove_from_favorite_desc), Toast.LENGTH_SHORT).show();
        }else {
            if(MoviesDatabase.mMoviesDataAccess.fetchMoviesById(Integer.valueOf(id)) != null){
                boolean isSaved = MoviesDatabase.mMoviesDataAccess.addMovies(new MoviesList(Integer.valueOf(id), Double.valueOf(rating), title, image_path, synopsis, relase_date));
                Log.d("is saved", isSaved + "");

                Toast.makeText(this, getResources().getString(R.string.add_to_favorite_desc), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
