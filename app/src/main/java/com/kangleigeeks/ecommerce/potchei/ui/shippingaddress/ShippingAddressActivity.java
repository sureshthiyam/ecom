package com.kangleigeeks.ecommerce.potchei.ui.shippingaddress;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kangleigeeks.ecommerce.potchei.ProgressUtils;
import com.kangleigeeks.ecommerce.potchei.R;
import com.kangleigeeks.ecommerce.potchei.data.helper.base.BaseActivity;
import com.kangleigeeks.ecommerce.potchei.data.helper.base.ItemClickListener;
import com.kangleigeeks.ecommerce.potchei.data.helper.database.DatabaseUtil;
import com.kangleigeeks.ecommerce.potchei.data.helper.models.AddressModel;
import com.kangleigeeks.ecommerce.potchei.data.helper.models.CustomProductInventory;
import com.kangleigeeks.ecommerce.potchei.data.helper.models.InventoryModel;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.AvailableInventoryResponse;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UserAddressResponse;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UserMultipleAddressResponse;
import com.kangleigeeks.ecommerce.potchei.data.helper.response.UserRegistrationResponse;
import com.kangleigeeks.ecommerce.potchei.data.util.Constants;
import com.kangleigeeks.ecommerce.potchei.data.util.CustomSharedPrefs;
import com.kangleigeeks.ecommerce.potchei.data.util.Loader;
import com.kangleigeeks.ecommerce.potchei.data.util.SharedPref;
import com.kangleigeeks.ecommerce.potchei.databinding.ActivityShippingAddressBinding;
import com.kangleigeeks.ecommerce.potchei.ui.checkout.CheckOutActivity;
import com.kangleigeeks.ecommerce.potchei.ui.productdetails.ProductDetailsActivity;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShippingAddressActivity extends BaseActivity<ShippingAddressMvpView, ShippingAddressPresenter> implements ShippingAddressMvpView , ItemClickListener<AddressModel> {

    private Toolbar toolbar;
    private ActivityShippingAddressBinding mBinding;
    private EditText etAddress1, etAddress2, etCity, etZip, etState, etCountry;
    private String address1 = "", address2 = "", city = "", zip = "", state = "", country = "";

    boolean isFieldEmpty;
    private FrameLayout btnCompleteAddress;
    private Loader mLoader;
    private Dialog dialog;
    private float totalPrice;
    StringBuilder inventoryIds = new StringBuilder(100);
    private List<CustomProductInventory> inventoryList;
    private List<InventoryModel> availableList;
    private boolean isAvailable;
    public String clientToken;
    public boolean isPaymentClicked;
    public int paymentMethod;
    public int clickedRadio = 0;
    private UserAddressResponse addressResponses;
    public static ShippingAddressActivity shippingAddressActivity;
    boolean isAddressInput;
    private ShippingAddressAdapter mAdapter;

    private String sUserName;
    private String sEmail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shipping_address;
    }

    @Override
    protected void startUI() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        initToolbar();
        mLoader = new Loader(this);
        getListFromDataBase();
        totalPrice = SharedPref.getSharedPref(this).readFloat(Constants.IntentKey.TOTAL_AMOUNT);
        mBinding.textTotalCostTitle.setText(CustomSharedPrefs.getCurrency(this) + "" + totalPrice);
        setClickListener(mBinding.layoutAddress.radioCurrentAddress2, mBinding.buttonContinue, mBinding.layoutPayment.radioPaypal,
                mBinding.layoutPayment.radioCredit);
        isPaymentClicked = false;
        shippingAddressActivity = this;
        mAdapter = new ShippingAddressAdapter(new ArrayList<>(), this);
        mBinding.layoutAddress.recyclerViewAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.layoutAddress.recyclerViewAddress.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);

        presenter.getAllAddress(this, ""+CustomSharedPrefs.getLoggedInUserId(this));

        UserRegistrationResponse loggedInUser = CustomSharedPrefs.getLoggedInUser(this);
        if (loggedInUser != null) {
            mLoader.show();
            presenter.getAllAddress(this, loggedInUser.userRegistrationInfo.id);

            sUserName = loggedInUser.userRegistrationInfo.username;
            sEmail = loggedInUser.userRegistrationInfo.email;
        }

    }

    private void launchPaymentFlow() {
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        //payUmoneyConfig.setPayUmoneyActivityTitle("Buy" + getResources().getString(R.string.nike_power_run));
        payUmoneyConfig.setDoneButtonText("Pay " + getResources().getString(R.string.Rupees) + totalPrice);
        //setTxnId(System.currentTimeMillis() + "")
        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(String.valueOf(convertStringToDouble(String.valueOf(totalPrice))))
                .setTxnId("1234567890")
                .setProductName(getResources().getString(R.string.nike_power_run))
                .setPhone("7878232386")
                .setFirstName(sUserName)
                .setEmail(sEmail)
                .setsUrl(Constants.SURL)
                .setfUrl(Constants.FURL)
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
                .setUdf6("")
                .setUdf7("")
                .setUdf8("")
                .setUdf9("")
                .setUdf10("")
                .setIsDebug(Constants.DEBUG)
                .setKey(Constants.MERCHANT_KEY)
                .setMerchantId(Constants.MERCHANT_ID);
        try {
            PayUmoneySdkInitializer.PaymentParam mPaymentParams = builder.build();
            calculateHashInServer(mPaymentParams);
        } catch (Exception e) {
            Toast.makeText(this,"catch"+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void calculateHashInServer(final PayUmoneySdkInitializer.PaymentParam mPaymentParams) {
        ProgressUtils.showLoadingDialog(this);
        String url = Constants.MONEY_HASH;
        StringRequest request = new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String merchantHash = "";

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            merchantHash = jsonObject.getString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ProgressUtils.cancelLoading();

                        if (merchantHash.isEmpty() || merchantHash.equals("")) {
                            Toast.makeText(ShippingAddressActivity.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
                        } else {
                            mPaymentParams.setMerchantHash(merchantHash);
                            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, ShippingAddressActivity.this, R.style.PayUMoney, true);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            Toast.makeText(ShippingAddressActivity.this, "Connect to internet Volley", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ShippingAddressActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        ProgressUtils.cancelLoading();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return mPaymentParams.getParams();
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {

            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //showAlert("Payment Successful");
                    Toast.makeText(this, "Payment Successfully", Toast.LENGTH_SHORT).show();
                    //new updateTransaction().execute();
                    //sp.edit().putString(ConstantSp.TRANSACTION_STATUS,"yes").commit();
                    //startActivity(new Intent(PaymentHomeActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.CANCELLED)) {
                    showAlert("Payment Cancelled");
                } else if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.FAILED)) {
                    showAlert("Payment Failed");
                }

            } else if (resultModel != null && resultModel.getError() != null) {
                Toast.makeText(this, "Error check log", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Both objects are null", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_CANCELED) {
            showAlert("Payment Cancelled");
        }
    }

    private Double convertStringToDouble(String str) {
        return Double.parseDouble(str);
    }

    private void showAlert(String msg) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    /**
     * getting list from database
     */
    private void getListFromDataBase() {
        AsyncTask.execute(() -> {
            inventoryList = DatabaseUtil.on().getAllCodes();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (inventoryList != null && inventoryList.size() > 0) {
                        for (CustomProductInventory inventory : inventoryList) {
                            inventoryIds.append(inventory.inventory_id + ",");
                        }
                        mLoader.show();
                        presenter.getAvailableInventory(ShippingAddressActivity.this, String.valueOf(inventoryIds));
                    }
                }
            });
        });
    }



    /**
     * init toolbar
     */
    private void initToolbar() {
        TextView toobarTitle = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toobarTitle.setText(this.getString(R.string.title_shipping_add));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    protected void stopUI() {
        if (shippingAddressActivity != null)
            shippingAddressActivity = null;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.radio_current_address_2:
                //add current address
                mBinding.layoutAddress.radioCurrentAddress2.setChecked(false);
                openAddressPopUp();

                break;

            case R.id.btn_complete_address:
                //validation address
                validateAddress();
                if (!isFieldEmpty) {
                    mLoader.show();
                    if (address1.equals("")) {
                        address1 = "";
                    } else if (address2.equals("")) {
                        address2 = "";
                    }
                    //update address to server
                    presenter.updateAddress(this, address1, address2, city, zip, state, country);
                }
                break;

            case R.id.radio_paypal:
                isPaymentClicked = true;
                paymentMethod = 1;
                mBinding.layoutPayment.radioCredit.setChecked(false);
                break;

            case R.id.radio_credit:
                isPaymentClicked = true;
                paymentMethod = 2;
                mBinding.layoutPayment.radioPaypal.setChecked(false);
                break;

            case R.id.button_continue:

                if (availableList != null && availableList.size() > 0) {
                    if (inventoryList.size() > 0) {
                        for (CustomProductInventory productInventory : inventoryList) {
                            int quantity = 0, proId = 0;
                            quantity = productInventory.currentQuantity;
                            proId = productInventory.inventory_id;

                            for (InventoryModel inventoryModel : availableList) {
                                if (inventoryModel.id == proId) {
                                    if (inventoryModel.quantity >= quantity) {
                                        isAvailable = true;
                                    } else {
                                        isAvailable = false;
                                    }
                                } else {
                                    isAvailable = false;
                                }
                            }
                        }
                    }
                }

                if (isAvailable) {
                    if (isPaymentClicked) {
                        //clickedRadio = 1 means address is given before
                        if (clickedRadio != 0) {
                            //address taken
                            mLoader.show();
                            launchPaymentFlow();
                            //presenter.getClientTokenFromServer(this);
                        }

                        else {
                            Toast.makeText(this, "Please add address!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Please choose payment method!", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(this, "Your product is out of stock!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * checking validation of address field
     */
    private void validateAddress() {

        address1 = etAddress1.getText().toString().trim();
        address2 = etAddress2.getText().toString().trim();
        city = etCity.getText().toString().trim();
        zip = etZip.getText().toString().trim();
        state = etState.getText().toString().trim();
        country = etCountry.getText().toString().trim();

        if (address1.equals("")) {
            isFieldEmpty = true;
            etAddress1.setBackgroundResource(R.drawable.edittext_error);
        } else {
            isFieldEmpty = false;
            etAddress1.setBackgroundResource(R.drawable.edittext_round);
        }
        if (city.equals("")) {
            isFieldEmpty = true;
            etCity.setBackgroundResource(R.drawable.edittext_error);
        } else {
            isFieldEmpty = false;
            etCity.setBackgroundResource(R.drawable.edittext_round);
        }
        if (zip.equals("")) {
            isFieldEmpty = true;
            etZip.setBackgroundResource(R.drawable.edittext_error);
        } else {
            isFieldEmpty = false;
            etZip.setBackgroundResource(R.drawable.edittext_round);
        }
        if (state.equals("")) {
            isFieldEmpty = true;
            etState.setBackgroundResource(R.drawable.edittext_error);
        } else {
            isFieldEmpty = false;
            etState.setBackgroundResource(R.drawable.edittext_round);
        }
        if (country.equals("")) {
            isFieldEmpty = true;
            etCountry.setBackgroundResource(R.drawable.edittext_error);
        } else {
            isFieldEmpty = false;
            etCountry.setBackgroundResource(R.drawable.edittext_round);
        }
    }

    /**
     * open address pop up to update address
     */
    private void openAddressPopUp() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_alert_address, null);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(true);
        dialog = alertDialogBuilder.create();
        dialog.show();

        etAddress1 = dialog.findViewById(R.id.et_shipping_address_1);
        etAddress2 = dialog.findViewById(R.id.et_shipping_address_2);
        etCity = dialog.findViewById(R.id.et_shipping_city);
        etZip = dialog.findViewById(R.id.et_shipping_zip);
        etState = dialog.findViewById(R.id.et_shipping_state);
        etCountry = dialog.findViewById(R.id.et_shipping_country);

        btnCompleteAddress = dialog.findViewById(R.id.btn_complete_address);
        btnCompleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAddress();
                if (!isFieldEmpty) {
                    isAddressInput = true;
                    mLoader.show();
                    hideKeyboardFrom(ShippingAddressActivity.this,view);
                    presenter.updateAddress(ShippingAddressActivity.this, address1, address2, city, zip, state, country);
                } else {

                }
            }
        });
    }

    @Override
    protected ShippingAddressPresenter initPresenter() {
        return new ShippingAddressPresenter();
    }

    @Override
    public void onGetAvailableAddressSuccess(UserAddressResponse addressResponse) {
        if (dialog != null) dialog.dismiss();
        mLoader.stopLoader();
        if (addressResponse != null && addressResponse.statusCode == HttpURLConnection.HTTP_OK) {
            mAdapter.addItem(addressResponse.addressModel);
        }


    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    @Override
    public void onGetAvailableAddressError(String errorMessage) {
        if (dialog != null) dialog.dismiss();
        mLoader.stopLoader();
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        clickedRadio = 0;
    }

    @Override
    public void onGettingAllAddressSuccess(UserMultipleAddressResponse response) {
        mLoader.stopLoader();
        if (response != null && response.statusCode == HttpURLConnection.HTTP_OK) {
            if (!response.addressModel.isEmpty()) {
                mAdapter.addListItem(response.addressModel);
            }
        }
    }

    @Override
    public void onAvailableInventorySuccess(AvailableInventoryResponse response) {
        mLoader.stopLoader();
        if (response.statusCode == HttpURLConnection.HTTP_OK) {
            availableList = response.inventoryModelList;
        }
    }

    @Override
    public void onAvailableInventoryError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        mLoader.stopLoader();
    }

    @Override
    public void onBrainTreeClientTokenSuccess(String tokenResponse) {
        if (tokenResponse != null) {
            clientToken = tokenResponse;
            mLoader.stopLoader();
            Intent intent = new Intent(this, CheckOutActivity.class);
            intent.putExtra(Constants.IntentKey.PAYMENT_RESPONSE, clientToken);
            intent.putExtra(Constants.IntentKey.PAYMENT_METHOD, paymentMethod);
            startActivity(intent);
            if (ProductDetailsActivity.productDetailsActivity != null)
                ProductDetailsActivity.productDetailsActivity.finish();
            // finish();
        }
        mLoader.stopLoader();
    }


    @Override
    public void onBrainTreeClientTokenError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        mLoader.stopLoader();
    }

    @Override
    public void onItemClick(View view, AddressModel item, int i) {
        if (item != null) {
            clickedRadio = 1;
            presenter.saveAddressData(item, this);
        }
    }
}
