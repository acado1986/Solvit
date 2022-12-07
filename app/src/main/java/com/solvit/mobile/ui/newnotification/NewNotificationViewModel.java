package com.solvit.mobile.ui.newnotification;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NewNotificationViewModel extends ViewModel {

    //private final MutableLiveData<String> etUserNotification;
    private final FirebaseUser currentUser;

    public NewNotificationViewModel() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

//    public LiveData<String> getEtUserNotifiaction() {
//        return etUserNotification;
//    }
    public String getUserName(){
        if(TextUtils.isEmpty(currentUser.getDisplayName()))
            return currentUser.getEmail();
        else
            return currentUser.getDisplayName();
    }
}