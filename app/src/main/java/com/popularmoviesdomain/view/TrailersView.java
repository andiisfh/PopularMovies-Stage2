package com.popularmoviesdomain.view;

import com.popularmoviesdomain.model.trailers.TrailersModel;

/**
 * Created by andiisfh on 31/07/17.
 */

public interface TrailersView {
    void onSuccess(TrailersModel trailersModel);
    void onError(String message);
}
