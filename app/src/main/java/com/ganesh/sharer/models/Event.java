package com.ganesh.sharer.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ganesh-XPS13 on 12/14/2015.
 */
public class Event implements Parcelable {
    private static AtomicInteger nextId = new AtomicInteger();
    private int eventID;
    private String title;
    private String description;
    private Calendar createdOn;
    private HashMap<User, Double> sharedBy;

    private Group group;

    public Event(String title, String description) {
        this.eventID = nextId.incrementAndGet();
        this.title = title;
        this.description = description;
        this.sharedBy = new HashMap<>();
        this.group = null;
        this.createdOn = Calendar.getInstance();
    }

    public Event(String title, String description, HashMap<User, Double> sharedBy) {
        this.eventID = nextId.incrementAndGet();
        this.title = title;
        this.description = description;
        this.sharedBy = sharedBy;
        this.group = null;
        this.createdOn = Calendar.getInstance();
    }

    public Event(String title, String description, HashMap<User, Double> sharedBy, Group group) {
        this.eventID = nextId.incrementAndGet();
        this.title = title;
        this.description = description;
        this.sharedBy = sharedBy;
        this.group = group;
        this.createdOn = Calendar.getInstance();
    }

    public int getEventID() {
        return eventID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getCreatedOn() {
        return createdOn;
    }

    public HashMap<User, Double> getSharedBy() {
        return sharedBy;
    }

    public void setSharedBy(HashMap<User, Double> sharedBy) {
        this.sharedBy = sharedBy;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.eventID);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeSerializable(this.createdOn);
        dest.writeSerializable(this.sharedBy);
        dest.writeParcelable(this.group, 0);
    }

    protected Event(Parcel in) {
        this.eventID = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.createdOn = (Calendar) in.readSerializable();
        this.sharedBy = (HashMap<User, Double>) in.readSerializable();
        this.group = in.readParcelable(Group.class.getClassLoader());
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
