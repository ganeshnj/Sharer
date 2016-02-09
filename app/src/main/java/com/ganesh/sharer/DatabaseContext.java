package com.ganesh.sharer;

import android.util.Log;

import com.ganesh.sharer.models.Event;
import com.ganesh.sharer.models.Group;
import com.ganesh.sharer.models.Settlement;
import com.ganesh.sharer.models.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ganesh-XPS13 on 12/14/2015.
 */
public class DatabaseContext {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Event> events = new ArrayList<>();
    private static ArrayList<Group> groups = new ArrayList<>();
    private static ArrayList<Settlement> settlements = new ArrayList<>();

    static {
        User userAda = new User("ada", "Ada", "Lovelace", "password", "ada@hotmail.com");
        User userGeorge = new User("george", "George", "Washingtom", "password", "george@hotmail.com");
        User userHarry = new User("harry", "Harry", "Houdini", "password", "harry@hotmail.com");
        User userMarcel = new User("marcel", "Marcel", "Proust", "password", "marcel@hotmail.com");
        User userNellie = new User("nellie", "Nellie", "Bly", "password", "nellie@hotmail.com");
        User userWilbur = new User("wilbur", "Wilbut", "Wright", "password", "wilbur@hotmail.com");
        users.add(userAda);
        users.add(userGeorge);
        users.add(userHarry);
        users.add(userMarcel);
        users.add(userNellie);
        users.add(userWilbur);

        Group groupOurAppartment = new Group("Our Appartment", "Daily expenses of our appartment");
        // groupOurAppartment.getUsers().add(userAda);
        // groupOurAppartment.getUsers().add(userGeorge);
        // groupOurAppartment.getUsers().add(userHarry);

        groups.add(groupOurAppartment);

        HashMap<User, Double> userFloatHashMap1 = new HashMap<>();
        userFloatHashMap1.put(userAda, 70.0);
        userFloatHashMap1.put(userGeorge, 90.0);
        Event event1 = new Event("First event", "First event description", userFloatHashMap1);
        events.add(event1);

        HashMap<User, Double> userFloatHashMap2 = new HashMap<>();
        userFloatHashMap2.put(userHarry, 50.0);
        userFloatHashMap2.put(userAda, 70.0);
        userFloatHashMap2.put(userGeorge, 90.0);
        Event event2 = new Event("Second event", "Second event description", userFloatHashMap2);
        events.add(event2);

    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getUser(int userID){
        for (User user: getUsers()) {
            if (user.getUserId() == userID)
                return user;
        }
        return null;
    }

    public static void setUsers(ArrayList<User> users) {
        DatabaseContext.users = users;
    }

    public static ArrayList<Event> getEvents() {
        return events;
    }

    public static void setEvents(ArrayList<Event> events) {
        DatabaseContext.events = events;
    }

    public static ArrayList<Group> getGroups() {
        return groups;
    }

    public static void setGroups(ArrayList<Group> groups) {
        DatabaseContext.groups = groups;
    }

    public static ArrayList<Settlement> getSettlements() {
        return settlements;
    }

    public static void setSettlements(ArrayList<Settlement> settlements) {
        DatabaseContext.settlements = settlements;
    }

    public static void addUser(User user) {
        getUsers().add(user);
        Log.i(DatabaseContext.class.getSimpleName(), "User saved:" + user.toString());
    }

    public static void editUser(User user) {
        User u = getUser(user.getUserId());
        u.setFirstname(user.getFirstname());
        u.setLastname(user.getLastname());
        u.setEmail(user.getEmail());

        Log.i(DatabaseContext.class.getSimpleName(), "User edited:" + u.toString());
    }


    public static boolean deleteUser(User user) {
        getUsers().remove(user);
        Log.i(DatabaseContext.class.getSimpleName(), "User deleted:" + user.toString());
        return true;
    }

    public static boolean deleteUser(int userId) {
        User user = getUser(userId);

        if (user != null){
            getUsers().remove(user);
            Log.i(DatabaseContext.class.getSimpleName(), "User deleted:" + user.toString());
            return true;
        }

        return false;
    }

    public static boolean addGroup(String title, String description, ArrayList<User> groupMembers) {
        Group group = new Group(title, description, groupMembers);
        getGroups().add(group);
        return true;
    }

    public static boolean addGroup(Group group) {
        getGroups().add(group);
        return true;
    }


}
