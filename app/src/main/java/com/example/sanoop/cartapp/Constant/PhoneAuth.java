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
public class PhoneAuth {

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
}
