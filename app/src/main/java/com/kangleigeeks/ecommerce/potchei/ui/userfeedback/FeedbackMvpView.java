package com.kangleigeeks.ecommerce.potchei.ui.userfeedback;

import android.graphics.Bitmap;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.FeedBackResponse;

public interface FeedbackMvpView extends MvpView {
    void onGettingBitmap(Bitmap bitmap);
    void onGettingImagePath(String imagePath);

    void onGettingReviewSuccess(FeedBackResponse feedBackResponse);

    void onGettingReviewError(String error);
}
