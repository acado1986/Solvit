package com.solvit.mobile.ui.pending;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solvit.mobile.model.Completed;
import com.solvit.mobile.model.NotificationModelIT;
import com.solvit.mobile.repositories.FirebaseRepository;

import java.util.HashMap;
import java.util.List;

public class PendingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<List<NotificationModelIT>> notificationsLiveData;
    private FirebaseRepository mRepo;


    public PendingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        mRepo = new FirebaseRepository<NotificationModelIT>();
        notificationsLiveData = mRepo.getNotificationsDataSet();
        mRepo.startNotificationsChangeListener(
                "events/it/it_events",
                new HashMap<String, String>(){{put("completed", Completed.WORKER.toString());}},
                NotificationModelIT.class);
       Log.d(TAG, "PendingViewModel: new model");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<NotificationModelIT>> getNotificationsLiveData() {

        return notificationsLiveData;
    }
}