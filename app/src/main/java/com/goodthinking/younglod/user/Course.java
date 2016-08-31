package com.goodthinking.younglod.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by elit on 17/07/16.
 */
public class Course implements Serializable {

    private String CourseHeadLine;
    private String CourseStartdate;
    private String Coursetime;
    private String CourseGide;
    private String CourseSynopsys;
    private String CourseInfo;
    private String CourseEndDate;
    private String MaxNoOfParticipetors;
    private String numberPrticipate;
    private String CourseCost;
    private String CourseAduience;
    private String CourseLang;
    private String StatusIsValidDate;

    public Course(String courseAduience, String courseCost, String courseEndDate, String courseGide, String courseHeadLine, String courseInfo, String courseLang, String courseStartdate, String courseSynopsys, String coursetime, String maxNoOfParticipetors, String numberPrticipate, String statusIsValidDate) {
        CourseAduience = courseAduience;
        CourseCost = courseCost;
        CourseEndDate = courseEndDate;
        CourseGide = courseGide;
        CourseHeadLine = courseHeadLine;
        CourseInfo = courseInfo;
        CourseLang = courseLang;
        CourseStartdate = courseStartdate;
        CourseSynopsys = courseSynopsys;
        Coursetime = coursetime;
        MaxNoOfParticipetors = maxNoOfParticipetors;
        this.numberPrticipate = numberPrticipate;
        StatusIsValidDate = statusIsValidDate;
    }


    public String getCourseAduience() {
        return CourseAduience;
    }

    public void setCourseAduience(String courseAduience) {
        CourseAduience = courseAduience;
    }

    public String getCourseCost() {
        return CourseCost;
    }

    public void setCourseCost(String courseCost) {
        CourseCost = courseCost;
    }

    public String getCourseEndDate() {
        return CourseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        CourseEndDate = courseEndDate;
    }

    public String getCourseGide() {
        return CourseGide;
    }

    public void setCourseGide(String courseGide) {
        CourseGide = courseGide;
    }

    public String getCourseHeadLine() {
        return CourseHeadLine;
    }

    public void setCourseHeadLine(String courseHeadLine) {
        CourseHeadLine = courseHeadLine;
    }

    public String getCourseInfo() {
        return CourseInfo;
    }

    public void setCourseInfo(String courseInfo) {
        CourseInfo = courseInfo;
    }

    public String getCourseLang() {
        return CourseLang;
    }

    public void setCourseLang(String courseLang) {
        CourseLang = courseLang;
    }

    public String getCourseStartdate() {
        return CourseStartdate;
    }

    public void setCourseStartdate(String courseStartdate) {
        CourseStartdate = courseStartdate;
    }

    public String getCourseSynopsys() {
        return CourseSynopsys;
    }

    public void setCourseSynopsys(String courseSynopsys) {
        CourseSynopsys = courseSynopsys;
    }

    public String getCoursetime() {
        return Coursetime;
    }

    public void setCoursetime(String coursetime) {
        Coursetime = coursetime;
    }

    public String getMaxNoOfParticipetors() {
        return MaxNoOfParticipetors;
    }

    public void setMaxNoOfParticipetors(String maxNoOfParticipetors) {
        MaxNoOfParticipetors = maxNoOfParticipetors;
    }

    public String getNumberPrticipate() {
        return numberPrticipate;
    }

    public void setNumberPrticipate(String numberPrticipate) {
        this.numberPrticipate = numberPrticipate;
    }

    public String getStatusIsValidDate() {
        return StatusIsValidDate;
    }

    public void setStatusIsValidDate(String statusIsValidDate) {
        StatusIsValidDate = statusIsValidDate;
    }

    @Exclude
    String Key;

    @Exclude
    public String getKey() {
        return Key;
    }

    @Exclude
    public void setKey(String key) {
        Key = key;
    }




    @Exclude
    public HashMap<String,Object> Objecttofirebase(){
        HashMap<String,Object> course = new HashMap<>();

        course.put("CourseHeadLine",CourseHeadLine);
        course.put("CourseStartdate",CourseStartdate);
        course.put("Coursetime",Coursetime);
        course.put("CourseSynopsys",CourseSynopsys);
        course.put("CourseInfod",CourseInfo);
        course.put("CourseEndDate",CourseEndDate);
        course.put("MaxNoOfParticipetors",MaxNoOfParticipetors);
        course.put("numberPrticipate",numberPrticipate);
        course.put("CourseCost",CourseCost);
        course.put("CourseAduience",CourseAduience);
        course.put("CourseLang",CourseLang);
        course.put("StatusIsValidDate", StatusIsValidDate);

        return course;

    }


}
