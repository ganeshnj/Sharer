package com.ganesh.sharer.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ganesh-XPS13 on 12/14/2015.
 */
public class Settlement implements Parcelable {
    private static AtomicInteger nextId = new AtomicInteger();
    private int settlementID;
    private User giver;
    private User taker;
    private double amount;

    public Settlement(){
        this.settlementID = nextId.incrementAndGet();
    }

    public Settlement(User giver, User taker, double amount) {
        this.settlementID = nextId.incrementAndGet();
        this.giver = giver;
        this.taker = taker;
        this.amount = amount;
    }


    public int getSettlementID() {
        return settlementID;
    }

    public User getGiver() {
        return giver;
    }

    public void setGiver(User giver) {
        this.giver = giver;
    }

    public User getTaker() {
        return taker;
    }

    public void setTaker(User taker) {
        this.taker = taker;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.settlementID);
        dest.writeParcelable(this.giver, 0);
        dest.writeParcelable(this.taker, 0);
        dest.writeDouble(this.amount);
    }

    protected Settlement(Parcel in) {
        this.settlementID = in.readInt();
        this.giver = in.readParcelable(User.class.getClassLoader());
        this.taker = in.readParcelable(User.class.getClassLoader());
        this.amount = in.readDouble();
    }

    public static final Creator<Settlement> CREATOR = new Creator<Settlement>() {
        public Settlement createFromParcel(Parcel source) {
            return new Settlement(source);
        }

        public Settlement[] newArray(int size) {
            return new Settlement[size];
        }
    };
}
