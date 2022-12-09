package com.solvit.mobile.ui.fragments.pending;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solvit.mobile.activities.NavigationDrawerActivity;
import com.solvit.mobile.model.RevisedBy;
import com.solvit.mobile.model.NotificationModel;
import com.solvit.mobile.model.Status;
import com.solvit.mobile.repositories.FirebaseRepository;

import java.util.HashMap;
import java.util.List;

public class PendingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<List<NotificationModel>> notificationsLiveData;
    private FirebaseRepository<NotificationModel> mRepo;


    public PendingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        mRepo = new FirebaseRepository<NotificationModel>();
        notificationsLiveData = mRepo.getDataSet();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<NotificationModel>> getNotificationsLiveData() {

        return notificationsLiveData;
    }

    public void startListener(String collectionPath){
        mRepo.startChangeListener(
                collectionPath,
                new HashMap<String, String>(){{put("status", Status.PENDING.toString());}},
                NotificationModel.class);
    }
}