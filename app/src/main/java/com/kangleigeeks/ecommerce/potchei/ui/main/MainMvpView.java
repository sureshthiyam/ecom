package com.kangleigeeks.ecommerce.potchei.ui.main;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.MainPageResponse;

public interface MainMvpView extends MvpView {

    void onGettingHomePageDataSuccess(MainPageResponse mainPageResponse);
    void onGettingHomePageDataError(String error);

}
