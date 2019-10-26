package com.kangleigeeks.ecommerce.potchei.ui.signinresendcode;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UserRegistrationResponse;

public interface SignInEmailSendMvpView extends MvpView {
   void onGetCodeSuccess(UserRegistrationResponse user);
   void onGetCodeError(String errorMessage);
}
