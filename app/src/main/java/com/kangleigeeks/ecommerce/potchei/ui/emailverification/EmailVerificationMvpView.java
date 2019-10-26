package com.kangleigeeks.ecommerce.potchei.ui.emailverification;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UserRegistrationResponse;

public interface EmailVerificationMvpView extends MvpView {
    void onEmailVerificationSuccess(UserRegistrationResponse user);

    void onEmailVeridicationError(String errorMessage);

    void onResendCodeSuccess(UserRegistrationResponse user);

    void onResendCodeError(String errorMessage);
}
