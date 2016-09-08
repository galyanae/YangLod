package com.goodthinking.youngloduser.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class newsItem implements Parcelable {
    private String info;
    private String headline;
    private String date;
    private String time;
    private String iName;
    private String picInTitle; // show picture in info yes/no
    @Exclude
    private String key;

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public String isPicInTitle() {
        return picInTitle;
    }

    public void setPicInTitle(String picInTitle) {
        this.picInTitle = picInTitle;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public newsItem() {
    }

    public newsItem(String info, String headline, String date, String time, String iName, String picInTitle) {
        this.info = info;
        this.headline = headline;
        this.date = date;
        this.time = time;
        this.iName = iName;
        this.picInTitle = picInTitle;
    }

    @Override
    public String toString() {
        return "newsItem{" +
                ", info='" + info + '\'' +
                ", headline='" + headline + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", iName='" + iName + '\'' +
                ", picInTitle=" + picInTitle +
                ", key='" + key + '\'' +
                '}';
    }

    public Map<String, Object> toMap() {
        Map<String, Object> mp = new HashMap<>();
        mp.put("info", info);
        mp.put("headline", headline);
        mp.put("date", date);
        mp.put("time", time);
        mp.put("iName", iName);
        mp.put("picInTitle", picInTitle);

        return mp;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.info);
        dest.writeString(this.headline);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeString(this.iName);
        dest.writeString(this.picInTitle);
        dest.writeString(this.key);
    }

    protected newsItem(Parcel in) {
        this.info = in.readString();
        this.headline = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.iName = in.readString();
        this.picInTitle = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<newsItem> CREATOR = new Parcelable.Creator<newsItem>() {
        @Override
        public newsItem createFromParcel(Parcel source) {
            return new newsItem(source);
        }

        @Override
        public newsItem[] newArray(int size) {
            return new newsItem[size];
        }
    };

}
