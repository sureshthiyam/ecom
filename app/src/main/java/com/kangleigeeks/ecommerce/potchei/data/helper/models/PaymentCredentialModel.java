package com.kangleigeeks.ecommerce.potchei.data.helper.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentCredentialModel {
    @SerializedName("environment")
    @Expose
    public String environment;

    @SerializedName("merchant_id")
    @Expose
    public String merchantId;

    @SerializedName("public_key")
    @Expose
    public String publicKey;

    @SerializedName("private_key")
    @Expose
    public String privateKey;
}
// sigi code mayam se loina khngbro
// asidi ngaisaigi php gi id duga chp mannei