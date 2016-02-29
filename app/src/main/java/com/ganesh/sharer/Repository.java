package com.ganesh.sharer;

import com.ganesh.sharer.models.Currency;
import com.ganesh.sharer.models.Event;
import com.ganesh.sharer.models.Group;
import com.ganesh.sharer.models.Settlement;
import com.ganesh.sharer.models.Share;
import com.ganesh.sharer.models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ganesh-XPS13 on 2/9/2016.
 */
public class Repository {
    
    private static DatabaseContext context;
    
    static  {
        context = new DatabaseContext();
    }

    public static boolean createUser(User user) {
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
            u.setFirstname(user.getFirstname());
            u.setLastname(user.getLastname());
            u.setEmail(user.getEmail());
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
            g.setTitle(group.getTitle());
            g.setDescription(group.getDescription());
            g.setGroupMembers(group.getGroupMembers());
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

    public static ArrayList<Event> getEventsByUserId(int userId) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event: context.getEvents()) {
            for (Share share: event.getSharers()) {
                if(share.getSharer().getUserId() == userId){
                    events.add(event);
                }
            }
        }
        return events;
    }

    public static boolean updateEvent(Event event) {
        Event e = getEventById(event.getEventID());
        if (e != null){
            e.setTitle(event.getTitle());
            e.setDescription(event.getDescription());
            e.setSharers(event.getSharers());
            e.setGroup(event.getGroup());
            e.setTotalAmount(event.getTotalAmount());
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

    public static boolean deleteEventById(int eventId) {
        Event e = getEventById(eventId);
        if (e != null) {
            context.getEvents().remove(e);
            return true;
        }
        return false;
    }

    public static ArrayList<Event> getAllEvents() {
        return context.getEvents();
    }

    public static boolean createSettlement(Settlement settlement) {
        context.getSettlements().add(settlement);
        return true;
    }

    public static Settlement getSettlementById(int settlementId) {
        for (Settlement settlement: context.getSettlements()) {
            if (settlement.getSettlementID() == settlementId){
                return settlement;
            }
        }
        return null;
    }

    public static boolean updateSettlement(Settlement settlement) {
        Settlement s = getSettlementById(settlement.getSettlementID());
        if (s != null){
            s.setTaker(settlement.getTaker());
            s.setGiver(settlement.getGiver());
            s.setAmount(settlement.getAmount());
            return true;
        }
        return false;
    }

    public static boolean deleteSettlement(Settlement settlement) {
        if (context.getSettlements().contains(settlement)){
            context.getSettlements().remove(settlement);
            return true;
        }
        return false;
    }


    public static ArrayList<Settlement> getAllSettlements() {
        return context.getSettlements();
    }

    public static User getLoginedUser(){
        return context.getMe();
    }

    public static boolean updateLoginedUser(User user) {
        User u = getLoginedUser();
        u.setFirstname(user.getFirstname());
        u.setLastname(user.getLastname());
        u.setEmail(user.getEmail());
        return true;
    }

    public static Currency getCurrency() {
        return context.getCurrency();
    }

    public static boolean updateCurrency(Currency c) {
        Currency currency = getCurrency();
        currency.setDesc(c.getDesc());
        currency.setSymbol(c.getSymbol());
        return true;
    }

    public static double getAllPositive() {
       double value = 0;
        for (Event event:  getAllEvents()) {
            if (event.isEqual() > 0) {
                value+= event.isEqual();
            }
        }
        return value;
    }

    public static double getAllPositive(ArrayList<Event> events) {
        double value = 0;
        for (Event event:  events) {
            if (event.isEqual() > 0) {
                value+= event.isEqual();
            }
        }
        return value;
    }

    public static double getAllNegative() {
        double value = 0;
        for (Event event:  getAllEvents()) {
            if (event.isEqual() < 0) {
                value+= event.isEqual();
            }
        }
        return value;
    }

    public static double getAllNegative(ArrayList<Event> events) {
        double value = 0;
        for (Event event:  events) {
            if (event.isEqual() < 0) {
                value+= event.isEqual();
            }
        }
        return value;
    }

    public static double getSettlementByUserId(int userId){
        double total = 0;
        for (Settlement settlement: getAllSettlements()) {
            if (settlement.getTaker().getUserId() == userId)
                total += settlement.getAmount();
        }
        return total;
    }

}
