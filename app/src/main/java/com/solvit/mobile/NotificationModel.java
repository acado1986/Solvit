package com.solvit.mobile;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model notification class
 */
public class NotificationModel implements Serializable {
    private String id;
    private String completed;
    private long pcNumber;
    private String role;
    private String room;
    private String building;
    private String description;
    private String user;
    private List<String> fowardTo;

    public NotificationModel() {}

    public NotificationModel(String id, String completed, long pcNumber, String role, String room, String building, String description, String user, List<String> fowardTo) {
        this.id = id;
        this.completed = completed;
        this.pcNumber = pcNumber;
        this.role = role;
        this.room = room;
        this.building = building;
        this.description = description;
        this.user = user;
        this.fowardTo = fowardTo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public long getPcNumber() {
        return pcNumber;
    }

    public void setPcNumber(long pcNumber) {
        this.pcNumber = pcNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getFowardTo() {
        return fowardTo;
    }

    public void setFowardTo(List<String> fowardTo) {
        this.fowardTo = fowardTo;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}
