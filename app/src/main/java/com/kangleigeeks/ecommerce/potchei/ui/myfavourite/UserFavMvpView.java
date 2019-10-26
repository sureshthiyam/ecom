package com.kangleigeeks.ecommerce.potchei.ui.myfavourite;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.ProductGridResponse;

public interface UserFavMvpView extends MvpView {
    void onGettingFavouriteSuccess(ProductGridResponse response);
    void onGettingFavouriteError(String errorMessage);
}
