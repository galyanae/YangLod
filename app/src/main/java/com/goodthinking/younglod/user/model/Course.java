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
    private String CourseDate;
    private String CourseEndDate;
    private String time;
    private String CourseTutor;      // MADRICH
    private String CourseTime;
    private String CourseSynopsys;
    private String CourseInformation;
    private String CourseHost;
    private Boolean CourseIsNotValid;
    private int CourseParticipatorsno;
    private int CourseMaxParticipants;
    private Boolean CourseIsClosed;
    private String StatusIsValidDate;
    private String courseCost;
    private String courseAudience;
    private String courseLang;
    private String Image;

    public String getCourseEndDate() {
        return CourseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        CourseEndDate = courseEndDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourseTutor() {
        return CourseTutor;
    }

    public void setCourseTutor(String courseTutor) {
        CourseTutor = courseTutor;
    }

    public int getCourseMaxParticipants() {
        return CourseMaxParticipants;
    }

    public void setCourseMaxParticipants(int courseMaxParticipants) {
        CourseMaxParticipants = courseMaxParticipants;
    }

    public String getCourseCost() {
        return courseCost;
    }

    public void setCourseCost(String courseCost) {
        this.courseCost = courseCost;
    }

    public String getCourseAudience() {
        return courseAudience;
    }

    public void setCourseAudience(String courseAudience) {
        this.courseAudience = courseAudience;
    }

    public String getCourseLang() {
        return courseLang;
    }

    public void setCourseLang(String courseLang) {
        this.courseLang = courseLang;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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



    public Course(String courseName, String courseDate,
                 String courseTime, String courseSynopsys,
                 String courseInformation, String courseHost,
                 Boolean courseIsNotValid, int courseParticipatorsno,
                 Boolean courseIsClosed, String statusIsValidDate, String image) {
        CourseName = courseName;
        CourseDate = courseDate;
        CourseTime = courseTime;
        CourseSynopsys = courseSynopsys;
        CourseInformation = courseInformation;
        CourseHost = courseHost;
        CourseIsNotValid = courseIsNotValid;
        CourseParticipatorsno = courseParticipatorsno;
        CourseIsClosed = courseIsClosed;
        StatusIsValidDate = statusIsValidDate;
        Image = image;
    }

    public Course() {

    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseDate() {
        return CourseDate;
    }

    public void setCourseDate(String courseDate) {
        CourseDate = courseDate;
    }

    public String getCourseTime() {
        return CourseTime;
    }

    public void setCourseTime(String courseTime) {
        CourseTime = courseTime;
    }

    public String getCourseSynopsys() {
        return CourseSynopsys;
    }

    public void setCourseSynopsys(String courseSynopsys) {
        CourseSynopsys = courseSynopsys;
    }

    public String getCourseInformation() {
        return CourseInformation;
    }

    public void setCourseInformation(String courseInformation) {
        CourseInformation = courseInformation;
    }
    public String getCourseHost() {  return CourseHost;
    }

    public void setCourseHost(String courseHost) {
        CourseHost = courseHost;
    }



    public Boolean getCourseIsNotValid() {
        return CourseIsNotValid;
    }

    public void setCourseIsNotValid(Boolean courseIsNotValid) {
        CourseIsNotValid = courseIsNotValid;
    }

    public int getCourseParticipatorsno() {
        return CourseParticipatorsno;
    }

    public void setCourseParticipatorsno(int courseParticipatorsno) {
        CourseParticipatorsno = courseParticipatorsno;
    }

    public Boolean getCourseIsClosed() {
        return CourseIsClosed;
    }

    public void setCourseIsClosed(Boolean courseIsClosed) {
        CourseIsClosed = courseIsClosed;
    }

    public String getStatusIsValidDate() {
        return StatusIsValidDate;
    }

    public void setStatusIsValidDate(String statusIsValidDate) {
        StatusIsValidDate = statusIsValidDate;
    }

    @Exclude
    public HashMap<String,Object> Objecttofirebase(){
        HashMap<String,Object> course = new HashMap<>();
        course.put("CourseName",CourseName);
        course.put("CourseDate",CourseDate);
        course.put("CourseTime",CourseTime);
        course.put("CourseSynopsys",CourseSynopsys);
        course.put("CourseInformation",CourseInformation);
        course.put("CourseHost",CourseHost);
        course.put("CourseIsNotValid",CourseIsNotValid);
        course.put("CourseParticipatorsno",CourseParticipatorsno);
        course.put("CourseIsClosed",CourseIsClosed);
        course.put("StatusIsValidDate",StatusIsValidDate);
        course.put("Image", Image);
        return course;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CourseName);
        dest.writeString(this.CourseDate);
        dest.writeString(this.CourseEndDate);
        dest.writeString(this.time);
        dest.writeString(this.CourseTutor);
        dest.writeString(this.CourseTime);
        dest.writeString(this.CourseSynopsys);
        dest.writeString(this.CourseInformation);
        dest.writeString(this.CourseHost);
        dest.writeValue(this.CourseIsNotValid);
        dest.writeInt(this.CourseParticipatorsno);
        dest.writeInt(this.CourseMaxParticipants);
        dest.writeValue(this.CourseIsClosed);
        dest.writeString(this.StatusIsValidDate);
        dest.writeString(this.courseCost);
        dest.writeString(this.courseAudience);
        dest.writeString(this.courseLang);
        dest.writeString(this.Image);
        dest.writeString(this.Key);
    }

    protected Course(Parcel in) {
        this.CourseName = in.readString();
        this.CourseDate = in.readString();
        this.CourseEndDate = in.readString();
        this.time = in.readString();
        this.CourseTutor = in.readString();
        this.CourseTime = in.readString();
        this.CourseSynopsys = in.readString();
        this.CourseInformation = in.readString();
        this.CourseHost = in.readString();
        this.CourseIsNotValid = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.CourseParticipatorsno = in.readInt();
        this.CourseMaxParticipants = in.readInt();
        this.CourseIsClosed = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.StatusIsValidDate = in.readString();
        this.courseCost = in.readString();
        this.courseAudience = in.readString();
        this.courseLang = in.readString();
        this.Image = in.readString();
        this.Key = in.readString();
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
