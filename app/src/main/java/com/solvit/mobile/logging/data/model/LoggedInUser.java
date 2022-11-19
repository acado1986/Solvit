package com.solvit.mobile.logging.data.model;

import com.google.firebase.auth.FirebaseUser;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private FirebaseUser firebaseUser;

    public LoggedInUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public LoggedInUser() {
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }
}