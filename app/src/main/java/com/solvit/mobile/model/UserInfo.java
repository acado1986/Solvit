package com.solvit.mobile.model;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

/**
 * Aditional user information
 */
public class UserInfo implements com.google.firebase.auth.UserInfo, Serializable, Uid {

    private Role role;
    private boolean active;
    private String displayName;
    private String email;
    private String phoneNumber;
    private Uri photoUrl;
    private String providerId;
    private String uid;
    private boolean emailVerified;

    public UserInfo() {
    }

    public UserInfo(Role role, Boolean active, String displayName, String email, String phoneNumber, Uri photoUrl, String providerId, String uid, boolean emailVerified) {
        this.role = role;
        this.active = active;
        this.displayName = displayName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
        this.providerId = providerId;
        this.uid = uid;
        this.emailVerified = emailVerified;
    }

    public UserInfo(FirebaseUser firebaseUser, Role role, boolean active) {
        this.role = role;
        this.active = active;
        this.displayName = firebaseUser.getDisplayName();
        this.email = firebaseUser.getEmail();
        this.phoneNumber = firebaseUser.getPhoneNumber();
        this.photoUrl = firebaseUser.getPhotoUrl();
        this.providerId = firebaseUser.getProviderId();
        this.uid = firebaseUser.getUid();
        this.emailVerified = firebaseUser.isEmailVerified();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Nullable
    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Nullable
    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Nullable
    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Nullable
    @Override
    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    @NonNull
    @Override
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @NonNull
    @Override
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
