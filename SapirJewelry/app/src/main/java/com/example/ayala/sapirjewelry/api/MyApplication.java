package com.example.ayala.sapirjewelry.api;

import android.app.Application;

/**
 * Created by ayala on 11/16/2016.
 */

public class MyApplication extends Application {

    private String IPAddress = "http://192.168.100.170:8082/";

    public String getIPAddress() {
        return IPAddress;
    }


}