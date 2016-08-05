package com.example.sanoop.cartapp.Constant;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sanoop on 8/4/2016.
 */
public class ApiClient {
    public static final String BASE_URL = "http://cart.coderswebsites.com/";
    private static Retrofit retrofit = null;

    OkHttpClient client;
    String credential;
    public void setupClient(){
        credential = Credentials.basic(Constant.KEY_USERNAME, Constant.KEY_PASSWORD);
        client = new OkHttpClient();
    }

    public static Retrofit getClient() {

        if (retrofit==null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            final String credential = Credentials.basic(Constant.KEY_USERNAME, Constant.KEY_PASSWORD);
            httpClient.addInterceptor(new Interceptor() {
                  @Override
                  public Response intercept(Interceptor.Chain chain) throws IOException {
                      Request original = chain.request();
                      Request request = original.newBuilder()
                              .header("Authorization", credential)
                              .method(original.method(), original.body())
                              .build();

                      return chain.proceed(request);
                  }
            });
            OkHttpClient client = httpClient.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
