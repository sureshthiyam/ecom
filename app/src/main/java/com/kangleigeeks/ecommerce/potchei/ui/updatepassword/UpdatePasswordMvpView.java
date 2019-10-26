package com.kangleigeeks.ecommerce.potchei.ui.updatepassword;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UserRegistrationResponse;

public interface UpdatePasswordMvpView extends MvpView {
    void onPasswordUpdateSuccess(UserRegistrationResponse user);
    void onPasswordUpdateError(String errorMessage);
}
