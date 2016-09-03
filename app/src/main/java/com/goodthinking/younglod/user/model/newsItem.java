package com.goodthinking.younglod.user.model;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;



@IgnoreExtraProperties
public class newsItem {
    private String image;
    private String info;
    private String headline;
    private String date;
    private String time;
    private String iName;

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public boolean isPicInTitle() {
        return picInTitle;
    }

    public void setPicInTitle(boolean picInTitle) {
        this.picInTitle = picInTitle;
    }

    private boolean picInTitle; // show picture in info yes/no
    private int imgID;
    @Exclude
    private Bitmap img;
    @Exclude
    private String key;

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
            this.date = date;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }



    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public newsItem() {
    }

    public newsItem(String date, String headline, String image, String info) {
        this.date = date;
        this.headline = headline;
        this.image = image;
        this.info = info;
    }

    @Override
    public String toString() {
        return "newsItem{" +
                "imgID=" + imgID +
                ", info='" + info + '\'' +
                ", headline='" + headline + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
