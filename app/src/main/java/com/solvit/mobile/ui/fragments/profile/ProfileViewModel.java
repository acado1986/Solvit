package com.solvit.mobile.ui.fragments.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.solvit.mobile.model.UserInfo;
import com.solvit.mobile.repositories.FirebaseRepository;

import java.util.HashMap;
import java.util.List;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<List<UserInfo>> userInfoLiveData;
    private final FirebaseRepository<UserInfo> mRepo;



    public ProfileViewModel() {
        mRepo = new FirebaseRepository<>();
        userInfoLiveData = mRepo.getDataSet();
        mRepo.startChangeListener(
                "users",
                new HashMap<String, String>() {{
                    put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                }},
                UserInfo.class);
    }

    public LiveData<List<UserInfo>> getUserInfoLiveData() {
        return userInfoLiveData;
    }

    public void updateUser(UserInfo user){
        mRepo.updateUser(user);
    }

}