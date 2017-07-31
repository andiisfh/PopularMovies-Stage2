package com.popularmovies.ui.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by andiisfh on 01/07/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ImageLoader {

    public static void load(Context context, String path, ImageView imageView){
        String BASE_URL = "http://image.tmdb.org/t/p/w185";
        Picasso.with(context).load(BASE_URL + path).into(imageView);
    }

    public static void loadYoutubeThumbnail(Context context, String path, ImageView imageView){
        Picasso.with(context).load("http://img.youtube.com/vi/" + path + "/0.jpg").into(imageView);
    }
}
