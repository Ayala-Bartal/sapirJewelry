package com.example.ayala.sapirjewelry.entities;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by ayala on 10/23/2016.
 */

public class Shop {

    protected String name;
    protected String picPath;
    protected String contant;
    transient protected Bitmap picView;


    public Shop(String name, String picPath, String contant, Bitmap picView) {
        super();
        this.name = name;
        this.picPath = picPath;
        this.contant = contant;
        this.picView = picView;
    }

    private void writeObject (ObjectOutputStream oos) throws IOException {
        if (picView != null) {
            picView.compress(Bitmap.CompressFormat.JPEG, 70, oos);
        }
        oos.defaultWriteObject();
    }


    public Bitmap getPicView() {return picView;}
    public void setPicView(Bitmap picView) {
        this.picView = picView;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPicPath() {
        return picPath;
    }
    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
    public String getContant() {
        return contant;
    }
    public void setContant(String contant) {
        this.contant = contant;
    }

}
