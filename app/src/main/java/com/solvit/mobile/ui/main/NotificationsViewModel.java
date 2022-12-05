package com.solvit.mobile.ui.main;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.solvit.mobile.model.NotificationModelIT;
import com.solvit.mobile.repositories.NotificationRepository;

import java.util.List;

public class NotificationsViewModel extends ViewModel {

    static String TAG = "ViewModel";

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    private MutableLiveData<List<NotificationModelIT>> notificationsLiveData;
    private NotificationRepository mRepo;

    public void init() {
        if(notificationsLiveData != null){
            return;
        }
        mRepo = NotificationRepository.getInstance();
        notificationsLiveData = new MutableLiveData<>();
        notificationsLiveData.setValue(mRepo.getNotificationsDataSet("events/it/it_events"));
        Log.d(TAG, "init: " + mRepo.getNotificationsDataSet("events/it/it_events"));


    }




    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<NotificationModelIT>> getNotificationsLiveData() {
        return notificationsLiveData;
    }
}