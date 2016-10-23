package com.example.ayala.sapirjewelry.api;

import com.example.ayala.sapirjewelry.entities.Customers;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ayala on 10/16/2016.
 */

public interface SapirServerAPiI {

    @GET("/")
    Call<Collection<Customers>> getAllUsersNames();

    @GET("/{name}")
    Call<Customers> getUser(@Path("name") String name);

    //	 @FormUrlEncoded
    @POST("/")
    Call<Customers> postUser(@Body Customers user);
}
