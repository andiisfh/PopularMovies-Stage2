package com.popularmoviesdomain.view;

import com.popularmoviesdomain.model.reviews.ReviewsModel;

/**
 * Created by andiisfh on 31/07/17.
 */

public interface ReviewsView {
    void onSuccess(ReviewsModel reviewsModel);
    void onError(String message);
}
