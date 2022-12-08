package com.solvit.mobile.model;



import java.io.Serializable;
import java.util.List;

/**
 * Model notification class
 */
public class NotificationModelIT extends NotificationModel implements Serializable {

    private long pcNumber;

    public NotificationModelIT() {}


    public NotificationModelIT(String id, Completed completed, boolean pending, Role role, String title, String room, String building, String description, String user, List<String> fowardTo, long pcNumber) {
        super(id, completed, pending, role, title, room, building, description, user, fowardTo);
        this.pcNumber = pcNumber;
    }

    public NotificationModelIT(Completed completed, boolean pending, Role role, String title, String room, String building, String description, String user, List<String> fowardTo, long pcNumber) {
        super(completed, pending, role, title, room, building, description, user, fowardTo);
        this.pcNumber = pcNumber;
    }

    public long getPcNumber() {
        return pcNumber;
    }

    public void setPcNumber(long pcNumber) {
        this.pcNumber = pcNumber;
    }
}
