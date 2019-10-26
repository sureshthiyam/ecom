package com.kangleigeeks.ecommerce.potchei.ui.ordercomplete;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.OrderListResponse;

public interface OrderCompleteMvpView extends MvpView {
    void onGettingOrderInfoSuccess(OrderListResponse response);
    void onGettingOrderInfoError(String errorMessage);
}
