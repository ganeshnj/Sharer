package com.ganesh.sharer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.ganesh.sharer.DatabaseContext;
import com.ganesh.sharer.Repository;

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

    private ArrayList<User> groupMembers;

    public Group(int id){
        this.groupID = id;
        this.groupMembers = new ArrayList<>();
    }

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
        this.groupMembers = new ArrayList<>();
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
        ArrayList<Event> events = new ArrayList<>();
        for (Event event: Repository.getAllEvents()) {
            if (event.getGroup()!= null && event.getGroup().getGroupID() == getGroupID()){
                events.add(event);
            }
        }
        return events;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.groupID);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeTypedList(groupMembers);
    }

    protected Group(Parcel in) {
        this.groupID = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.groupMembers = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        public Group createFromParcel(Parcel source) {
            return new Group(source);
        }

        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
