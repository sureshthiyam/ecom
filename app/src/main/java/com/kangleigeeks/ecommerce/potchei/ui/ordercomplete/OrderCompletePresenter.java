package com.kangleigeeks.ecommerce.potchei.ui.ordercomplete;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.BasePresenter;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.OrderListResponse;
import com.kangleigeeks.ecommerce.potchei.data.provider.retrofit.RetrofitClient;
import com.kangleigeeks.ecommerce.potchei.data.util.Constants;
import com.kangleigeeks.ecommerce.potchei.data.util.NetworkHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderCompletePresenter extends BasePresenter<OrderCompleteMvpView> {

    public void getOrderedList(Context context, String userID, String page) {
        if (NetworkHelper.hasNetworkAccess(context)) {
            RetrofitClient.getApiService()
                    .getOrderList(Constants.ServerUrl.API_TOKEN, userID, page).enqueue(new Callback<OrderListResponse>() {
                @Override
                public void onResponse(@NonNull Call<OrderListResponse> call, @NonNull Response<OrderListResponse> response) {
                    if (response.body() != null) {
                        getMvpView().onGettingOrderInfoSuccess(response.body());
                    }

                }

                @Override
                public void onFailure(@NonNull Call<OrderListResponse> call, @NonNull Throwable t) {
                    getMvpView().onGettingOrderInfoError(t.getMessage());
                }
            });
        } else {
            Toast.makeText(context, "Could not connect to internet.", Toast.LENGTH_SHORT).show();
        }
    }
}
