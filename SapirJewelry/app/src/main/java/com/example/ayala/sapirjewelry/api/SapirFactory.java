package com.example.ayala.sapirjewelry.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Converter.Factory;
import retrofit2.ScalarsConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class SapirFactory {

    public static ServerUsersAPiI createUsersApi(String apiUrl) {
        GsonConverterFactory jsonConverter = GsonConverterFactory.create();
        Retrofit retrofit  = createRetrofit(apiUrl, jsonConverter);
        ServerUsersAPiI sapirServer = retrofit.create(ServerUsersAPiI.class);
        return sapirServer;
    }

    public static ServerShopAPiI createShopsApi(String apiUrl) {
        GsonConverterFactory jsonConverter = GsonConverterFactory.create();
        Retrofit retrofit  = createRetrofit(apiUrl, jsonConverter);
        ServerShopAPiI serverShopAPiI = retrofit.create(ServerShopAPiI.class);
        return serverShopAPiI;
    }

    public static ServerJewelryAPiI createJewelryApi(String apiUrl) {
        GsonConverterFactory jsonConverter = GsonConverterFactory.create();
        Retrofit retrofit  = createRetrofit(apiUrl, jsonConverter);
        ServerJewelryAPiI serverJewelryAPiI = retrofit.create(ServerJewelryAPiI.class);
        return serverJewelryAPiI;
    }

    private static Retrofit createRetrofit(String apiUrl, Factory  jsonConverter){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(apiUrl);
        builder.client(getRequestHeader());
        builder.addConverterFactory(ScalarsConverterFactory.create());
        builder.addConverterFactory(jsonConverter);
        Retrofit retrofit = builder.build();
        return  retrofit;
    }

    private static OkHttpClient getRequestHeader (){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.MINUTES);
        builder.readTimeout(60, TimeUnit.MINUTES);
        builder.writeTimeout(60, TimeUnit.MINUTES);
        OkHttpClient httpClient = builder.build();
        return httpClient;
    }

}

