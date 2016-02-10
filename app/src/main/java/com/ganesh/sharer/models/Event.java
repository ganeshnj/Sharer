package com.ganesh.sharer.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private double totalAmount;
    
    private ArrayList<Share> sharers;

    private Group group;

    public Event() {

        this.eventID = nextId.incrementAndGet();
        this.createdOn = Calendar.getInstance();
    }

    public Event(int eventID) {

        this.eventID = eventID;
        this.createdOn = Calendar.getInstance();
    }

    public Event(String title, String description) {
        this.eventID = nextId.incrementAndGet();
        this.title = title;
        this.description = description;
        this.sharers = new ArrayList<Share>();
        this.group = null;
        this.createdOn = Calendar.getInstance();
    }

    public Event(String title, String description, ArrayList<Share> sharers) {
        this.eventID = nextId.incrementAndGet();
        this.title = title;
        this.description = description;
        this.sharers = sharers;
        this.group = null;
        this.createdOn = Calendar.getInstance();
    }

    public Event(String title, String description, ArrayList<Share> sharers, Group group) {
        this.eventID = nextId.incrementAndGet();
        this.title = title;
        this.description = description;
        this.sharers = sharers;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public ArrayList<Share> getSharers() {
        return sharers;
    }

    public void setSharers(ArrayList<Share> sharers) {
        this.sharers = sharers;
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
        dest.writeDouble(this.totalAmount);
        dest.writeTypedList(sharers);
        dest.writeParcelable(this.group, 0);
    }

    protected Event(Parcel in) {
        this.eventID = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.createdOn = (Calendar) in.readSerializable();
        this.totalAmount = in.readDouble();
        this.sharers = in.createTypedArrayList(Share.CREATOR);
        this.group = in.readParcelable(Group.class.getClassLoader());
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
