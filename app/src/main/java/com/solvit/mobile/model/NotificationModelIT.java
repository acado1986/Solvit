package com.solvit.mobile.model;



import java.io.Serializable;
import java.util.List;

/**
 * Model notification class
 */
public class NotificationModelIT extends NotificationModel implements Serializable {

    private long pcNumber;

    public NotificationModelIT() {}

    public NotificationModelIT(String id, Completed completed, Role role, String room, String building, String description, String user, List<String> fowardTo, long pcNumber) {
        super(id, completed, role, room, building, description, user, fowardTo);
        this.pcNumber = pcNumber;
    }

    public NotificationModelIT(Completed completed, Role role, String room, String building, String description, String user, List<String> fowardTo, long pcNumber) {
        super(completed, role, room, building, description, user, fowardTo);
        this.pcNumber = pcNumber;
    }

    public long getPcNumber() {
        return pcNumber;
    }

    public void setPcNumber(long pcNumber) {
        this.pcNumber = pcNumber;
    }
}
