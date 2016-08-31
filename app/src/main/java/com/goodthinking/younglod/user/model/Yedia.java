package com.goodthinking.younglod.user.model;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ravit on 30/07/2016.
 */

@IgnoreExtraProperties
public class Yedia {
    private String image;
    private String yedia;
    private String headline;
    private String date;
    private String time;
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

    public String getYedia() {
        return yedia;
    }

    public void setYedia(String yedia) {
        this.yedia = yedia;
    }



    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public Yedia() {}
    public Yedia(String date, String headline, String image, String yedia) {
        this.date = date;
        this.headline = headline;
        this.image = image;
        this.yedia = yedia;
    }

    @Override
    public String toString() {
        return "Yedia{" +
                "imgID=" + imgID +
                ", yedia='" + yedia + '\'' +
                ", headline='" + headline + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
