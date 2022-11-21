package com.solvit.mobile.model;

import java.io.Serializable;
import java.util.List;

public class NotificationModel implements Serializable {

    private String id;
    private Completed completed;
    private Role role;
    private String room;
    private String building;
    private String description;
    private String user;
    private List<String> fowardTo;

    public NotificationModel(){}

    public NotificationModel(String id, Completed completed, Role role, String room, String building, String description, String user, List<String> fowardTo) {
        this.id = id;
        this.completed = completed;
        this.role = role;
        this.room = room;
        this.building = building;
        this.description = description;
        this.user = user;
        this.fowardTo = fowardTo;
    }

    public NotificationModel(Completed completed, Role role, String room, String building, String description, String user, List<String> fowardTo) {
        this.completed = completed;
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

    public Completed getCompleted() {
        return completed;
    }

    public void setCompleted(Completed completed) {
        this.completed = completed;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
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
}