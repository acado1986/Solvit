package com.solvit.mobile.ui.fragments.newnotification;

import android.text.TextUtils;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.solvit.mobile.model.NotificationModel;
import com.solvit.mobile.repositories.FirebaseRepository;

public class NewNotificationViewModel extends ViewModel {

    //private final MutableLiveData<String> etUserNotification;
    private final FirebaseUser currentUser;
    private FirebaseRepository<NotificationModel> mRepo;

    public NewNotificationViewModel() {
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.mRepo = new FirebaseRepository<>();
    }

    public String getUserName(){
        return currentUser.getEmail();
    }

    public void writeNotification(NotificationModel notification, String collectionPath){
        mRepo.writeData(null, collectionPath, notification);
    }
}