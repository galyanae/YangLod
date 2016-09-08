package com.goodthinking.youngloduser;

import com.goodthinking.youngloduser.model.Event;

import java.util.ArrayList;

/**
 * Created by youngloduser on 19/07/2016.
 */
public class EventArraydata {
    private static EventArraydata ourInstance = new EventArraydata();

    public static EventArraydata getInstance() {
        return ourInstance;
    }

    private EventArraydata() {
    }
    private ArrayList<Event> Events=new ArrayList<>();

    public ArrayList<Event> getEvents() {
        return Events;
    }
}
