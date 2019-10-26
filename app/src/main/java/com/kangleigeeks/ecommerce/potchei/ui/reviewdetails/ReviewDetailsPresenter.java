package com.kangleigeeks.ecommerce.potchei.ui.reviewdetails;

import android.content.Context;

import com.kangleigeeks.ecommerce.potchei.data.helper.base.BasePresenter;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.FeedBackResponse;
import com.kangleigeeks.ecommerce.potchei.data.provider.reposervices.ApiService;
import com.kangleigeeks.ecommerce.potchei.data.provider.retrofit.RetrofitClient;
import com.kangleigeeks.ecommerce.potchei.data.util.Constants;
import com.kangleigeeks.ecommerce.potchei.data.util.NetworkHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewDetailsPresenter extends BasePresenter<ReviewDetailsMvpView> {


    /**
     * Show review data from server
     * @param context mActivity
     * @param itemId itemId
     */
    public void showReviewFromServer(Context context,String itemId){

        if (NetworkHelper.hasNetworkAccess(context)) {
            ApiService getRestInfoApi = RetrofitClient.getApiService();
            Call<FeedBackResponse> call=getRestInfoApi.showReview(Constants.ServerUrl.API_TOKEN,itemId);
            call.enqueue(new Callback<FeedBackResponse>() {
                @Override
                public void onResponse(Call<FeedBackResponse> call, Response<FeedBackResponse> response) {
                    if (response.isSuccessful() && response.body() != null){
                        FeedBackResponse feedBackResponse=response.body();
                        getMvpView().onGettingShowReviewSuccess(feedBackResponse.getFeedBackModel());
                    }
                }

                @Override
                public void onFailure(Call<FeedBackResponse> call, Throwable t) {
                        getMvpView().onGettingShowReviewError(t.getMessage());
                }
            });
        }
    }

}
