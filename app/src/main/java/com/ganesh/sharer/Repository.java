package com.ganesh.sharer;

import com.ganesh.sharer.models.Event;
import com.ganesh.sharer.models.Group;
import com.ganesh.sharer.models.User;

import java.util.ArrayList;

/**
 * Created by Ganesh-XPS13 on 2/9/2016.
 */
public class Repository {
    
    private static DatabaseContext context;
    
    static  {
        context = new DatabaseContext();
    }

    public boolean createUser(User user) {
        context.getUsers().add(user);
        return true;
    }

    public static User getUserById(int userId) {
        for (User user: context.getUsers()) {
            if (user.getUserId() == userId){
                return user;
            }
        }
        return null;
    }

    public  static boolean updateUser(User user) {
        User u = getUserById(user.getUserId());
        if (u != null){
            u = user;
            return true;
        }
        return false;
    }

    public static boolean deleteUser(User user) {
        if (context.getUsers().contains(user)){
            context.getUsers().remove(user);
            return true;
        }
        return false;
    }

    public static ArrayList<User> getAllUsers() {
        return context.getUsers();
    }

    public static  boolean createGroup(Group group) {
        context.getGroups().add(group);
        return true;
    }

    public static  Group getGroupById(int groupId) {
        for (Group group: context.getGroups()) {
            if (group.getGroupID() == groupId){
                return group;
            }
        }
        return null;
    }

    public static boolean updateGroup(Group group) {
        Group g = getGroupById(group.getGroupID());
        if (g != null){
            g = group;
            return true;
        }
        return false;
    }

    public static boolean deleteGroup(Group group) {
        if (context.getGroups().contains(group)){
            context.getGroups().remove(group);
            return true;
        }
        return false;
    }

    public static ArrayList<Group> getAllGroups() {
        return context.getGroups();
    }

    public static boolean createEvent(Event event) {
        context.getEvents().add(event);
        return true;
    }

    public static Event getEventById(int eventId) {
        for (Event event: context.getEvents()) {
            if (event.getEventID() == eventId){
                return event;
            }
        }
        return null;
    }

    public static boolean updateEvent(Event event) {
        Event e = getEventById(event.getEventID());
        if (e != null){
            e = event;
            return true;
        }
        return false;
    }

    public static boolean deleteEvent(Event event) {
        if (context.getEvents().contains(event)){
            context.getEvents().remove(event);
            return true;
        }
        return false;
    }
    
}
