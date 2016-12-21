package com.example.ayala.sapirjewelry;

import android.app.Application;

/**
 * Created by ayala on 12/21/2016.
 */
public class IpAdrress extends Application {

    private String ipAdrress = "http://192.168.100.62:8082";

    public String getIpAdrress() {
        return ipAdrress;
    }

    public String setIpAdrress(String ipAdrress) {
        this.ipAdrress = ipAdrress;
        return ipAdrress;
    }
}
