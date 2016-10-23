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

public interface ServerUsersAPiI {

    @GET("/users/")
    Call<Collection<Customers>> getAllUsersNames();

    @GET("/users/{name}")
    Call<Customers> getUser(@Path("name") String name);

    //	 @FormUrlEncoded
    @POST("/users/")
    Call<Customers> postUser(@Body Customers user);
}
