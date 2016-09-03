package com.goodthinking.younglod.user;

import com.goodthinking.younglod.user.model.Course;

import java.util.ArrayList;

/**
 * Created by Owner on 03/09/2016.
 */
public class CourseArrayData {
    private static CourseArrayData ourInstance = new CourseArrayData();

    public static CourseArrayData getInstance() {
        return ourInstance;
    }

    private CourseArrayData() {
    }
    private ArrayList<Course> Courses=new ArrayList<>();

    public ArrayList<Course> getCourses() {
        return Courses;
    }
}