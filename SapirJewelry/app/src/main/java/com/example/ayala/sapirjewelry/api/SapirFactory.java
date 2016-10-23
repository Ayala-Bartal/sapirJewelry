package com.example.ayala.sapirjewelry.api;

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

    private static Retrofit createRetrofit(String apiUrl, Factory  jsonConverter){
        Retrofit retrofit  = new Retrofit.Builder().baseUrl(apiUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(jsonConverter)
                .build();
        return  retrofit;
    }
}

