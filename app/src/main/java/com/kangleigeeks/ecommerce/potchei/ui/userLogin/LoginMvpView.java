package com.kangleigeeks.ecommerce.potchei.ui.userLogin;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UserRegistrationResponse;

public interface LoginMvpView extends MvpView {
    void onEmailSignUpSuccess(UserRegistrationResponse user);
    void onEmainSignUpError(String errorMessage);

    void onSocialSignUpSuccess(UserRegistrationResponse user);
    void onSocialSignUpError(String errorMessage);
}
