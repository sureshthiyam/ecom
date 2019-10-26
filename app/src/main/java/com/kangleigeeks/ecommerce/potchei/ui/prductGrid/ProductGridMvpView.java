package com.kangleigeeks.ecommerce.potchei.ui.prductGrid;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.AddFavouriteResponse;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.ProductGridResponse;

public interface ProductGridMvpView extends MvpView {
    void onProductListSuccess(ProductGridResponse products);

    void onProductListError(String errorMessage);

    void onFavSuccess(AddFavouriteResponse response);
    void onFavError(String errorMessage);

    void onRemoveFavSuccess(AddFavouriteResponse response);
}
