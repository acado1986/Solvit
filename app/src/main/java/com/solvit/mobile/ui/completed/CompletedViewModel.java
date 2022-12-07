package com.solvit.mobile.ui.completed;

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

public class CompletedViewModel extends ViewModel {

    private MutableLiveData<List<NotificationModelIT>> notificationsLiveData;
    private FirebaseRepository mRepo;

    public CompletedViewModel() {
        mRepo = new FirebaseRepository<NotificationModelIT>();
        notificationsLiveData = mRepo.getNotificationsDataSet();
        mRepo.startNotificationsChangeListener(
                "events/it/it_events",
                new HashMap<String, String>(){{put("completed", Completed.ADMIN.toString());}},
                NotificationModelIT.class);
        Log.d(TAG, "CompletedViewModel: new model");
    }

    public LiveData<List<NotificationModelIT>> getNotificationsLiveData() {

        return notificationsLiveData;
    }
}