package com.kangleigeeks.ecommerce.potchei.ui.userProfile;


import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UploadImageResponse;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UserAddressResponse;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UserMultipleAddressResponse;

public interface ProfileMvpView extends MvpView {
    void onSetAddressSuccess(UserAddressResponse address);

    void onRemoveAddressSuccess(UserAddressResponse address);

    void onSetAddressError(String errorMessage);

    void onGettingImageSuccess(UploadImageResponse response);

    void onGettingImageError(String errorMessage);

    void onGettingAllAddressSuccess(UserMultipleAddressResponse response);
}
