package com.solvit.mobile.ui.fragments.completed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solvit.mobile.model.NotificationModel;
import com.solvit.mobile.model.Status;
import com.solvit.mobile.repositories.FirebaseRepository;

import java.util.HashMap;
import java.util.List;

public class CompletedViewModel extends ViewModel {

    private MutableLiveData<List<NotificationModel>> notificationsLiveData;
    private FirebaseRepository<NotificationModel> mRepo;

    public CompletedViewModel() {
        mRepo = new FirebaseRepository<NotificationModel>();
        notificationsLiveData = mRepo.getDataSet();
    }

    public LiveData<List<NotificationModel>> getNotificationsLiveData() {

        return notificationsLiveData;
    }

    public void startListener(String collectionPath){
        mRepo.startChangeListener(
                collectionPath,
                new HashMap<String, String>(){{put("status", Status.DONE.toString());}},
                NotificationModel.class);
    }




}