package com.kangleigeeks.ecommerce.potchei.ui.search;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.AddFavouriteResponse;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.ProductGridResponse;

public interface SearchMvpView extends MvpView {
    void onSearchSuccess(ProductGridResponse response);

    void onSearchError(String errorMessage);

    void onFavSuccess(AddFavouriteResponse response);
    void onFavError(String errorMessage);

    void onRemoveFavSuccess(AddFavouriteResponse response);
}
