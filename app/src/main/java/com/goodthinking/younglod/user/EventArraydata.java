package com.goodthinking.younglod.user;

import java.util.ArrayList;

/**
 * Created by user on 19/07/2016.
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
