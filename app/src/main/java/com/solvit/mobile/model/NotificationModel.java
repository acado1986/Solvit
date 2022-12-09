package com.solvit.mobile.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NotificationModel implements Uid, Serializable, Comparable<NotificationModel> {

    private String id;
    private String revisedBy;
    private String status;
    private String title;
    private String room;
    private String building;
    private String description;
    private String user;
    private long pcNumber;
    private List<String> fowardTo;

    @ServerTimestamp
    private Date updatedAt;


    public NotificationModel() {
    }

    public NotificationModel(String id, String revisedBy, String status, String title, String room, String building, String description, String user, long pcNumber, List<String> fowardTo) {
        this.id = id;
        this.revisedBy = revisedBy;
        this.status = status;
        this.title = title;
        this.room = room;
        this.building = building;
        this.description = description;
        this.user = user;
        this.pcNumber = pcNumber;
        this.fowardTo = fowardTo;

    }

    public NotificationModel(String revisedBy, String status, String title, String room, String building, String description, String user, long pcNumber, List<String> fowardTo) {
        this.revisedBy = revisedBy;
        this.status = status;
        this.title = title;
        this.room = room;
        this.building = building;
        this.description = description;
        this.user = user;
        this.pcNumber = pcNumber;
        this.fowardTo = fowardTo;
    }

    public String getUid() {
        return id;
    }

    public void setUid(String id) {
        this.id = id;
    }

    public String getRevisedBy() {
        return revisedBy;
    }

    public void setRevisedBy(String revisedBy) {
        this.revisedBy = revisedBy;
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

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public long getPcNumber() {return pcNumber;}

    public void setPcNumber(long pcNumber) {this.pcNumber = pcNumber;}

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int compareTo(NotificationModel notificationModel) {
        return getUpdatedAt().compareTo(notificationModel.getUpdatedAt());
    }
}
