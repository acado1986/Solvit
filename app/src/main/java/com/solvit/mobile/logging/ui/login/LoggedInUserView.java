package com.solvit.mobile.logging.ui.login;

import com.google.firebase.auth.FirebaseUser;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private FirebaseUser user;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(FirebaseUser displayName) {
        this.user = displayName;
    }

    FirebaseUser getUser() {
        return user;
    }
}