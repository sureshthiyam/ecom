package com.kangleigeeks.ecommerce.potchei.ui.productdetails;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.AddFavouriteResponse;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.ProductDetailsResponse;

public interface ProductDetailsMvpView extends MvpView {
    /**
     * this interface is used to create callback to pass server response
     */


    void onProductDetailsSuccess(ProductDetailsResponse detailsResponse);

    void onProductDetailsError(String errorMessage);

    void onFavSuccess(AddFavouriteResponse response);

    void onFavError(String errorMessage);

    void onRemoveFavSuccess(AddFavouriteResponse response);
}
