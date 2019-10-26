package com.kangleigeeks.ecommerce.potchei.ui.shippingaddress;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.AvailableInventoryResponse;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UserAddressResponse;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UserMultipleAddressResponse;

public interface ShippingAddressMvpView extends MvpView {
    void onGetAvailableAddressSuccess(UserAddressResponse addressResponse);
    void onGetAvailableAddressError(String errorMessage);
    void onGettingAllAddressSuccess(UserMultipleAddressResponse response);

    void onAvailableInventorySuccess(AvailableInventoryResponse response);
    void onAvailableInventoryError(String errorMessage);

    void onBrainTreeClientTokenSuccess(String tokenResponse);
    void onBrainTreeClientTokenError(String errorMessage);

}
