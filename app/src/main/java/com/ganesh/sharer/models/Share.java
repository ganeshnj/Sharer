package com.ganesh.sharer.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ganesh-XPS13 on 2/9/2016.
 */
public class Share implements Parcelable {
    private User sharer;
    private double amount;

    public Share(User sharer, double amount) {
        this.sharer = sharer;
        this.amount = amount;
    }

    public User getSharer() {
        return sharer;
    }

    public void setSharer(User sharer) {
        this.sharer = sharer;
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
        dest.writeParcelable(this.sharer, 0);
        dest.writeDouble(this.amount);
    }

    protected Share(Parcel in) {
        this.sharer = in.readParcelable(User.class.getClassLoader());
        this.amount = in.readDouble();
    }

    public static final Parcelable.Creator<Share> CREATOR = new Parcelable.Creator<Share>() {
        public Share createFromParcel(Parcel source) {
            return new Share(source);
        }

        public Share[] newArray(int size) {
            return new Share[size];
        }
    };
}
