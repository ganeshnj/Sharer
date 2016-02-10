package com.ganesh.sharer.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ganesh-XPS13 on 12/14/2015.
 */
public class User implements Parcelable {
    private static AtomicInteger nextId = new AtomicInteger();
    private int userId;
    private String username;
    private String password;
    private String email;

    private String firstname;
    private String lastname;

    public User(int userId, String firstname, String lastname, String email) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public User(String firstname, String lastname, String email) {
        userId = nextId.incrementAndGet();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public User(String username, String firstname, String lastname, String password, String email) {
        userId = nextId.incrementAndGet();
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return firstname + " " + lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.email);
        dest.writeString(this.firstname);
        dest.writeString(this.lastname);
    }

    protected User(Parcel in) {
        this.userId = in.readInt();
        this.username = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        this.firstname = in.readString();
        this.lastname = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" UserId:" + getUserId());
        builder.append(" Firstname:" + getFirstname());
        builder.append(" Lastname:" + getLastname());
        builder.append(" Email:" + getEmail());
        return builder.toString();
    }

    public String getFormatted() {
        return firstname + " " + lastname + " (" + email + ")";
    }
}
