package com.kangleigeeks.ecommerce.potchei.data.helper.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kangleigeeks.ecommerce.potchei.data.helper.models.SettingsModel;

public class SettingsResponse {
    @SerializedName("status_code")
    @Expose
    public int status;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("data")
    @Expose
    public SettingsModel settingsModel;
}
