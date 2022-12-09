package com.solvit.mobile.ui.fragments.users;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solvit.mobile.model.UserInfo;
import com.solvit.mobile.repositories.FirebaseRepository;

import java.util.HashMap;
import java.util.List;

public class UsersViewModel extends ViewModel {

    private MutableLiveData<List<UserInfo>> usersLiveData;
    private FirebaseRepository<UserInfo> mRepo;


    public UsersViewModel() {
        mRepo = new FirebaseRepository<>();
        usersLiveData = mRepo.getDataSet();
        mRepo.startChangeListener(
                "users",
                new HashMap<String, String>(),
                UserInfo.class);
    }


    public LiveData<List<UserInfo>> getLiveData() {
        return usersLiveData;
    }

    public void activateUser(String userUid, String role, boolean active) {
        mRepo.activateUser(userUid, role, active);
    }
}