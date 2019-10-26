package com.kangleigeeks.ecommerce.potchei.ui.checkout;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.MvpView;

public interface CheckOutMvpView extends MvpView {
    void onPaymentNonceSuccess(String response);
    void  onPaymentNonceError(String errorMessage);

    void allPaymentSuccess(String response);
    void allPaymentError(String errorMessage);

}
