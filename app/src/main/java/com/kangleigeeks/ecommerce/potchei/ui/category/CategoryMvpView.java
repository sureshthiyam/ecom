package com.kangleigeeks.ecommerce.potchei.ui.category;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.AllCategoryResponse;

public interface CategoryMvpView extends MvpView {
    void onCategoryListSuccess(AllCategoryResponse productCategories);
    void onCategoryListError(String message);
}
