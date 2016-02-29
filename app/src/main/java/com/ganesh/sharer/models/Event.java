package com.ganesh.sharer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.ganesh.sharer.Repository;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
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

    public double getShareAmountByUserId(int userId) {
        double amount=0;
        for (Share share: getSharers()) {
            if (share.getSharer().getUserId() == userId)
                amount = share.getAmount();
        }
        return amount;
    }

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

    public double getEventAvg(){
        return getSharedAmount()/getSharers().size();
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

    public double getSharedAmount() {
        double amount = 0;
        for (Share share: getSharers()) {
            amount += share.getAmount();
        }
        return  amount;
    }

    public int isEqual() {
        for (Share share: getSharers()) {
            if (share.getSharer() == Repository.getLoginedUser()){
                return (int) (share.getAmount() - getTotalAmount()/getSharers().size());
            }
        }
        return 0;
    }

    public HashMap<User, Double> getResults(){
        HashMap<User, Double> map = new HashMap<>();
        double avg = getTotalAmount()/getSharers().size();
        double[] difference = new double[getSharers().size()];
        double[] positive = new double[getSharers().size()];
        double[] negative = new double[getSharers().size()];
        double sumN=0, sumP=0;

        int loginIndex = 0;
        for (int i = 0; i < getSharers().size(); i++) {
            User user = getSharers().get(i).getSharer();
            if (user == Repository.getLoginedUser())
                loginIndex = i;

            difference[i] = getSharers().get(i).getAmount() - avg;

            if (difference[i] > 0) {
                positive[i] = difference[i];
                sumP += difference[i];
            }
            else if (difference[i] < 0){
                negative[i] = difference[i];
                sumN +=difference[i];
            }
        }

        if (difference[loginIndex] > 0) {
            for (int i = 0; i < negative.length; i++) {
                if (i != loginIndex){
                    User user = getSharers().get(i).getSharer();
                    double valueToTake = Math.round((negative[i] / sumN) * difference[loginIndex]);
                    map.put(user, valueToTake);
                }
            }    
        }
        else if (difference[loginIndex] < 0) {
            for (int i = 0; i < positive.length; i++) {
                if (i != loginIndex) {
                    User user = getSharers().get(i).getSharer();
                    double valueToGive = Math.round((positive[i]/sumP) * difference[loginIndex]);
                    map.put(user, valueToGive);
                }
            }
        }

        return map;
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
