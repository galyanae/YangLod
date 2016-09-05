package com.goodthinking.younglod.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

@IgnoreExtraProperties
public class Event implements Parcelable {
    private String EventName;
    private String EventDate;
    private String EventEndDate;
    private String time;
    private String EventTutor;      // MADRICH
    private String EventTime;
    private String EventSynopsys;
    private String EventInformation;
    private String EventHost;
    private Boolean EventIsNotValid;
    private int EventParticipatorsno;
    private int EventMaxParticipants;
    private Boolean EventIsClosed;
    private String StatusIsValidDate;
    private String eventCost;
    private String eventAudience;
    private String eventLang;
    private String image;
    
    public String getEventEndDate() {
        return EventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        EventEndDate = eventEndDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEventTutor() {
        return EventTutor;
    }

    public void setEventTutor(String eventTutor) {
        EventTutor = eventTutor;
    }

    public int getEventMaxParticipants() {
        return EventMaxParticipants;
    }

    public void setEventMaxParticipants(int eventMaxParticipants) {
        EventMaxParticipants = eventMaxParticipants;
    }

    public String getEventCost() {
        return eventCost;
    }

    public void setEventCost(String eventCost) {
        this.eventCost = eventCost;
    }

    public String getEventAudience() {
        return eventAudience;
    }

    public void setEventAudience(String eventAudience) {
        this.eventAudience = eventAudience;
    }

    public String getEventLang() {
        return eventLang;
    }

    public void setEventLang(String eventLang) {
        this.eventLang = eventLang;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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



    public Event(String eventName, String eventDate,
                 String eventTime, String eventSynopsys,
                 String eventInformation, String eventHost,
                 Boolean eventIsNotValid, int eventParticipatorsno,
                 Boolean eventIsClosed, String statusIsValidDate, String image) {
        EventName = eventName;
        EventDate = eventDate;
        EventTime = eventTime;
        EventSynopsys = eventSynopsys;
        EventInformation = eventInformation;
        EventHost = eventHost;
        EventIsNotValid = eventIsNotValid;
        EventParticipatorsno = eventParticipatorsno;
        EventIsClosed = eventIsClosed;
        StatusIsValidDate = statusIsValidDate;
        image = image;
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

    public Boolean getEventIsClosed() {
        return EventIsClosed;
    }

    public void setEventIsClosed(Boolean eventIsClosed) {
        EventIsClosed = eventIsClosed;
    }

    public String getStatusIsValidDate() {
        return StatusIsValidDate;
    }

    public void setStatusIsValidDate(String statusIsValidDate) {
        StatusIsValidDate = statusIsValidDate;
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
        event.put("EventIsClosed",EventIsClosed);
        event.put("StatusIsValidDate",StatusIsValidDate);
        event.put("image", image);
        return event;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.EventName);
        dest.writeString(this.EventDate);
        dest.writeString(this.EventEndDate);
        dest.writeString(this.time);
        dest.writeString(this.EventTutor);
        dest.writeString(this.EventTime);
        dest.writeString(this.EventSynopsys);
        dest.writeString(this.EventInformation);
        dest.writeString(this.EventHost);
        dest.writeValue(this.EventIsNotValid);
        dest.writeInt(this.EventParticipatorsno);
        dest.writeInt(this.EventMaxParticipants);
        dest.writeValue(this.EventIsClosed);
        dest.writeString(this.StatusIsValidDate);
        dest.writeString(this.eventCost);
        dest.writeString(this.eventAudience);
        dest.writeString(this.eventLang);
        dest.writeString(this.image);
        dest.writeString(this.Key);
    }

    protected Event(Parcel in) {
        this.EventName = in.readString();
        this.EventDate = in.readString();
        this.EventEndDate = in.readString();
        this.time = in.readString();
        this.EventTutor = in.readString();
        this.EventTime = in.readString();
        this.EventSynopsys = in.readString();
        this.EventInformation = in.readString();
        this.EventHost = in.readString();
        this.EventIsNotValid = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.EventParticipatorsno = in.readInt();
        this.EventMaxParticipants = in.readInt();
        this.EventIsClosed = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.StatusIsValidDate = in.readString();
        this.eventCost = in.readString();
        this.eventAudience = in.readString();
        this.eventLang = in.readString();
        this.image = in.readString();
        this.Key = in.readString();
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
