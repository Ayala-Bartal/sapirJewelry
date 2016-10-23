package com.example.ayala.sapirjewelry.api;

import retrofit2.Retrofit;
import retrofit2.Converter.Factory;
import retrofit2.ScalarsConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class SapirFactory {

    public static SapirServerAPiI create (String apiUrl) {
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
        return create (apiUrl, gsonConverterFactory);
    }

    public static SapirServerAPiI create(String apiUrl, Factory  jsonConverter) {

        Retrofit retrofit  = new Retrofit.Builder().baseUrl(apiUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(jsonConverter)
                .build();
        SapirServerAPiI sapirServer = retrofit.create(SapirServerAPiI.class);
        return sapirServer;
    }
}

