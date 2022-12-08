package com.solvit.mobile.ui.pending;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solvit.mobile.model.Completed;
import com.solvit.mobile.model.NotificationModel;
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
        mRepo.startChangeListener(
                "events/it/it_events",
                new HashMap<String, String>(){{put("completed", Completed.WORKER.toString());}},
                NotificationModel.class);
       Log.d(TAG, "PendingViewModel: new model");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<NotificationModel>> getNotificationsLiveData() {

        return notificationsLiveData;
    }
}