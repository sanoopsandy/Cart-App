package com.example.sanoop.cartapp.Constant;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sanoop on 8/2/2016.
 */
public class ApiCall {

    OkHttpClient client;
    String credential;
    public void setupClient(){
        credential = Credentials.basic(Constant.KEY_USERNAME, Constant.KEY_PASSWORD);
        client = new OkHttpClient();
    }

    public Response sendOtpCode(String phone){
        setupClient();
        RequestBody formBody = new FormBody.Builder()
                .add(Constant.KEY_PHONE, phone)
                .build();
        Request request = new Request.Builder()
                .header("Authorization", credential)
                .url(Constant.REGISTER_URL)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response verifyOtpCode(String phone, String code){
        setupClient();
        RequestBody formBody = new FormBody.Builder()
                .add(Constant.KEY_PHONE, phone)
                .add(Constant.KEY_CODE, code)
                .build();
        Request request = new Request.Builder()
                .header("Authorization", credential)
                .url(Constant.VERIFICATION_URL)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response getCartList(String memberId){
        setupClient();
        String url = Constant.CART_LIST_URL + memberId + "/carts/?page=1";
        Request request = new Request.Builder()
                .header("Authorization", credential)
                .url(url)
                .get()
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response createList(String name, String price, String quantity, String cardId, String time, String categoryId, String barcode){
        setupClient();
        RequestBody formBody = new FormBody.Builder()
                .add(Constant.KEY_NAME, name)
                .add(Constant.KEY_PRICE, price)
                .add(Constant.KEY_QUANTITY, quantity)
                .add(Constant.KEY_CARTID, cardId)
                .add(Constant.KEY_TIME, time)
                .add(Constant.KEY_CATEGORYID, categoryId)
                .add(Constant.KEY_BARCODE, barcode)
                .build();
        Request request = new Request.Builder()
                .header("Authorization", credential)
                .url(Constant.LIST_CREATION_URL)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
