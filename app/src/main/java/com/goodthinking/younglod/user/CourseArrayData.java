package com.goodthinking.younglod.user;

/**
 * Created by Owner on 30/08/2016.
 */

import com.goodthinking.younglod.user.model.Course;

import java.util.ArrayList;

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
