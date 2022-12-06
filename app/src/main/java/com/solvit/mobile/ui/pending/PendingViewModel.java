package com.solvit.mobile.ui.pending;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solvit.mobile.model.NotificationModelIT;
import com.solvit.mobile.repositories.NotificationRepository;

import java.util.List;

public class PendingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<List<NotificationModelIT>> notificationsLiveData;
    private NotificationRepository mRepo;


    public PendingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        mRepo = NotificationRepository.getInstance();
        notificationsLiveData = mRepo.getNotificationsDataSet();
     //   Log.d(TAG, "PendingViewModel: notifications " + mRepo.getNotificationsDataSet("events/it/it_events"));
       // mRepo.getNotificationsDataSet(notificationsLiveData);
        mRepo.startNotificationsChangeListener();
       Log.d(TAG, "PendingViewModel: new model");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<NotificationModelIT>> getNotificationsLiveData() {

        return notificationsLiveData;
    }
}