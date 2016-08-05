package com.example.sanoop.cartapp.Interface;

import com.example.sanoop.cartapp.Constant.Constant;
import com.example.sanoop.cartapp.Model.Cart;
import com.example.sanoop.cartapp.Model.CartResponse;
import com.example.sanoop.cartapp.Model.Item;
import com.example.sanoop.cartapp.Model.Member;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sanoop on 8/4/2016.
 */
public interface ApiInterface {

    @POST("registration/")
    Call<Void> sendOtpCode(@Body RequestBody body);

    @POST("verification/")
    Call<Member> verifyOtpCode(@Body RequestBody body);

    @GET("members/{id}/carts/?page=1")
    Call<CartResponse> getCartList(@Path("id") String id);

    @POST("items/")
    Call<Item> addItemToCart(@Body RequestBody body);
}
