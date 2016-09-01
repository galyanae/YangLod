package com.goodthinking.younglod.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;


@IgnoreExtraProperties
public class Course implements Parcelable {

    private String CourseName;
    private String CourseStartdate;
    private String Coursetime;
    private String CourseGide;
    private String CourseSynopsys;
    private String CourseInfo;
    private String CourseEndDate;
    private String MaxNoOfParticipetors;
    private int numberPrticipate;
    private String CourseCost;
    private String CourseAduience;
    private String CourseLang;
    private String StatusIsValidDate;
    @Exclude
    private String key;

    public Course() {
    }

    public Course(String courseAduience, String courseCost, String courseEndDate, String courseGide,
                  String CourseName, String courseInfo, String courseLang, String courseStartdate,
                  String courseSynopsys, String coursetime, String maxNoOfParticipetors,
                  int numberPrticipate, String statusIsValidDate) {
        this.CourseAduience = courseAduience;
        this.CourseCost = courseCost;
        this.CourseEndDate = courseEndDate;
        this.CourseGide = courseGide;
        this.CourseName = CourseName;
        this.CourseInfo = courseInfo;
        this.CourseLang = courseLang;
        this.CourseStartdate = courseStartdate;
        this.CourseSynopsys = courseSynopsys;
        this.Coursetime = coursetime;
        this.MaxNoOfParticipetors = maxNoOfParticipetors;
        this.numberPrticipate = numberPrticipate;
        this.StatusIsValidDate = statusIsValidDate;
    }


    public String getCourseAduience() {
        return CourseAduience;
    }

    public void setCourseAduience(String courseAduience) {
        this.CourseAduience = courseAduience;
    }

    public String getCourseCost() {
        return CourseCost;
    }

    public void setCourseCost(String courseCost) {
        this.CourseCost = courseCost;
    }

    public String getCourseEndDate() {
        return CourseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.CourseEndDate = courseEndDate;
    }

    public String getCourseGide() {
        return CourseGide;
    }

    public void setCourseGide(String courseGide) {
        this.CourseGide = courseGide;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    public String getCourseInfo() {
        return CourseInfo;
    }

    public void setCourseInfo(String courseInfo) {
        this.CourseInfo = courseInfo;
    }

    public String getCourseLang() {
        return CourseLang;
    }

    public void setCourseLang(String courseLang) {
        this.CourseLang = courseLang;
    }

    public String getCourseStartdate() {
        return CourseStartdate;
    }

    public void setCourseStartdate(String courseStartdate) {
        this.CourseStartdate = courseStartdate;
    }

    public String getCourseSynopsys() {
        return CourseSynopsys == null ? "": CourseSynopsys;
    }

    public void setCourseSynopsys(String courseSynopsys) {
        this.CourseSynopsys = courseSynopsys;
    }

    public String getCoursetime() {
        return Coursetime;
    }

    public void setCoursetime(String coursetime) {
        this.Coursetime = coursetime;
    }

    public String getMaxNoOfParticipetors() {
        return MaxNoOfParticipetors;
    }

    public void setMaxNoOfParticipetors(String maxNoOfParticipetors) {
        this.MaxNoOfParticipetors = maxNoOfParticipetors;
    }

    public int getNumberPrticipate() {
        return numberPrticipate;
    }

    public void setNumberPrticipate(int numberPrticipate) {
        this.numberPrticipate = numberPrticipate;
    }

    public String getStatusIsValidDate() {
        return StatusIsValidDate;
    }

    public void setStatusIsValidDate(String statusIsValidDate) {
        this.StatusIsValidDate = statusIsValidDate;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CourseName);
        dest.writeString(this.CourseStartdate);
        dest.writeString(this.Coursetime);
        dest.writeString(this.CourseGide);
        dest.writeString(this.CourseSynopsys);
        dest.writeString(this.CourseInfo);
        dest.writeString(this.CourseEndDate);
        dest.writeString(this.MaxNoOfParticipetors);
        dest.writeInt(this.numberPrticipate);
        dest.writeString(this.CourseCost);
        dest.writeString(this.CourseAduience);
        dest.writeString(this.CourseLang);
        dest.writeString(this.StatusIsValidDate);
        dest.writeString(this.key);
    }

    protected Course(Parcel in) {
        this.CourseName = in.readString();
        this.CourseStartdate = in.readString();
        this.Coursetime = in.readString();
        this.CourseGide = in.readString();
        this.CourseSynopsys = in.readString();
        this.CourseInfo = in.readString();
        this.CourseEndDate = in.readString();
        this.MaxNoOfParticipetors = in.readString();
        this.numberPrticipate = in.readInt();
        this.CourseCost = in.readString();
        this.CourseAduience = in.readString();
        this.CourseLang = in.readString();
        this.StatusIsValidDate = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
