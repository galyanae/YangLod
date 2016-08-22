package com.goodthinking.younglod.user;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by user on 19/07/2016.
 */
public class Event implements Serializable {
    private String EventName;
    private String EventDate;
    private String EventTime;
    private String EventSynopsys;
    private String EventInformation;
    private String EventHost;
    private Boolean EventIsNotValid;
    private int EventParticipatorsno;


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



    public Event(String eventName, String eventDate,
                 String eventTime, String eventSynopsys,
                 String eventInformation, String eventHost,
                 Boolean eventIsNotValid, int eventParticipatorsno) {
        EventName = eventName;
        EventDate = eventDate;
        EventTime = eventTime;
        EventSynopsys = eventSynopsys;
        EventInformation = eventInformation;
        EventHost = eventHost;
        EventIsNotValid = eventIsNotValid;
        EventParticipatorsno = eventParticipatorsno;
    }

    public Event() {

    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventTime() {
        return EventTime;
    }

    public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }

    public String getEventSynopsys() {
        return EventSynopsys;
    }

    public void setEventSynopsys(String eventSynopsys) {
        EventSynopsys = eventSynopsys;
    }

    public String getEventInformation() {
        return EventInformation;
    }

    public void setEventInformation(String eventInformation) {
        EventInformation = eventInformation;
    }
    public String getEventHost() {  return EventHost;
    }

    public void setEventHost(String eventHost) {
        EventHost = eventHost;
    }



    public Boolean getEventIsNotValid() {
        return EventIsNotValid;
    }

    public void setEventIsNotValid(Boolean eventIsNotValid) {
        EventIsNotValid = eventIsNotValid;
    }

    public int getEventParticipatorsno() {
        return EventParticipatorsno;
    }

    public void setEventParticipatorsno(int eventParticipatorsno) {
        EventParticipatorsno = eventParticipatorsno;
    }
    @Exclude
    public HashMap<String,Object> Objecttofirebase(){
        HashMap<String,Object> event = new HashMap<>();
        event.put("EventName",EventName);
        event.put("EventDate",EventDate);
        event.put("EventTime",EventTime);
        event.put("EventSynopsys",EventSynopsys);
        event.put("EventInformation",EventInformation);
        event.put("EventHost",EventHost);
        event.put("EventIsNotValid",EventIsNotValid);
        event.put("EventParticipatorsno",EventParticipatorsno);
        return event;

    }


}
