package com.goodthinking.younglod.user;

import com.goodthinking.younglod.user.model.Course;

import java.util.ArrayList;

/**
 * Created by Owner on 03/09/2016.
 */
public class CourseArraydata {
    private static CourseArraydata ourInstance = new CourseArraydata();

    public static CourseArraydata getInstance() {
        return ourInstance;
    }

    private CourseArraydata() {
    }
    private ArrayList<Course> Courses=new ArrayList<>();

    public ArrayList<Course> getCourses() {
        return Courses;
    }
}