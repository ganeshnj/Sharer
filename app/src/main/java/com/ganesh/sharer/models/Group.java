package com.ganesh.sharer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.ganesh.sharer.DatabaseContext;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ganesh-XPS13 on 12/14/2015.
 */
public class Group implements Parcelable {
    private static AtomicInteger nextId = new AtomicInteger();
    private int groupID;
    private String title;
    private String description;

    private ArrayList<Event> events;

    private ArrayList<User> groupMembers;



    public Group(String title, String description, ArrayList<User> users) {
        this.groupID = nextId.incrementAndGet();
        this.title = title;
        this.description = description;
        this.groupMembers = users;
    }


    public Group(String title, String description) {
        this.groupID = nextId.incrementAndGet();
        this.title = title;
        this.description = description;
        this.events = new ArrayList<>();
    }

    public void setGroupMembers(ArrayList<User> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
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

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.groupID);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeList(this.events);
    }

    protected Group(Parcel in) {
        this.groupID = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.events = new ArrayList<Event>();
        in.readList(this.events, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<Group> CREATOR = new Parcelable.Creator<Group>() {
        public Group createFromParcel(Parcel source) {
            return new Group(source);
        }

        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public ArrayList<User> getGroupMembers(){
        return groupMembers;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" GroupId:" + getGroupID());
        builder.append(" Title:" + getTitle());
        builder.append(" Description:" + getDescription());
        builder.append(" Users:");
        for (User user: getGroupMembers()) {
            builder.append(" UserId:" + user.getUserId());
        }
        return builder.toString();
    }
}
