package com.kangleigeeks.ecommerce.potchei.ui.reviewdetails;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.models.FeedBackModel;

public interface ReviewDetailsMvpView extends MvpView {
    void onGettingShowReviewSuccess(FeedBackModel feedBackModel);

    void onGettingShowReviewError(String error);
}
