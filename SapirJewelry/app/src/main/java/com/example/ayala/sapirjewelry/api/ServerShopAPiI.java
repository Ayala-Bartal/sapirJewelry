package com.example.ayala.sapirjewelry.api;

import com.example.ayala.sapirjewelry.entities.Customers;
import com.example.ayala.sapirjewelry.entities.Shop;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ayala on 10/24/2016.
 */

public interface ServerShopAPiI {

    @GET("/shop/")
    Call<Collection<Shop>> getAllShopNames();

    @GET("/shop/{name}")
    Call<Shop> getUser(@Path("name") String name);

    //	 @FormUrlEncoded
    @POST("/shop/")
    Call<Shop> postUser(@Body Shop shop);
}
