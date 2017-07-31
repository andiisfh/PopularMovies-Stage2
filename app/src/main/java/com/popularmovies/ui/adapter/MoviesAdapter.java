package com.popularmovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.popularmovies.R;
import com.popularmovies.ui.utils.ImageLoader;
import com.popularmoviesdomain.model.movies.MoviesList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andiisfh on 01/07/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private final Context mContext;
    private final List<MoviesList> mMoviesList;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(MoviesList moviesList);
    }

    public MoviesAdapter(Context context, List<MoviesList> moviesList, ListItemClickListener listener) {
        mContext = context;
        mMoviesList = moviesList;
        mOnClickListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(mMoviesList.get(position).getPosterPath());
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_poster)
        ImageView mIVPoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(String image_path) {
            ImageLoader.load(mContext, image_path, mIVPoster);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(mMoviesList.get(getAdapterPosition()));
        }
    }
}
