package com.example.ayala.sapirjewelry.entities;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by ayala on 12/4/2016.
 */

public class Jewelry implements Serializable{
    protected String name;
    protected String picPath;
    protected String price;
    protected Bitmap picView;
    protected String link;
    protected String type;

    public Jewelry(String name, String picPath, String price, Bitmap picView, String link, String type) {
        this.name = name;
        this.picPath = picPath;
        this.price = price;
        this.picView = picView;
        this.link = link;
        this.type = type;
    }



    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getPicPath() {return picPath;}

    public void setPicPath(String picPath) {this.picPath = picPath;}

    public Bitmap getPicView() {return picView;}

    public void setPicView(Bitmap picView) {this.picView = picView;}

    public String getLink() {return link;}

    public void setLink(String link) {this.link = link;}

    public String getPrice() {return price;}

    public void setPrice(String price) {this.price = price;}

    public String getType() {return type;}

    public void setType(String type) {this.price = type;}
}
