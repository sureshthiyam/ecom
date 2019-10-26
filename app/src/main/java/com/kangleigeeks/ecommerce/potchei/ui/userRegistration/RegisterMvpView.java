package com.kangleigeeks.ecommerce.potchei.ui.userRegistration;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UserRegistrationResponse;

public interface RegisterMvpView extends MvpView {
    void onSignUpSuccess(UserRegistrationResponse user);
    void onSignUpError(String errorMessage);
}
