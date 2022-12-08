package com.solvit.mobile.ui.fragments.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.solvit.mobile.databinding.FragmentProfileBinding;
import com.solvit.mobile.model.Role;
import com.solvit.mobile.model.UserInfo;


import java.util.List;

public class ProfileFragment extends Fragment {

    private TextView tvUserEmail;
    private TextView tvUserRole;
    private EditText etDisplayName;
    private EditText etPhoneNumber;
    private Button btnSave;
    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etDisplayName = binding.etDisplayNameProfile;
        tvUserEmail = binding.tvEmailProfile;
        tvUserRole = binding.tvRoleUserProfile;
        etPhoneNumber = binding.etPhoneUserProfile;
        btnSave = binding.btnSaveProfile;

        profileViewModel.getUserInfoLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> userInfos) {
                fillProfile(userInfos.get(0));
            }
        });

        btnSave.setOnClickListener(view1 -> {
            UserInfo user = profileViewModel.getUserInfoLiveData().getValue().get(0);
            updateUser(user);
            profileViewModel.updateUser(user);
            Toast.makeText(getContext(), "Usuario actualizado", Toast.LENGTH_SHORT).show();
        });

    }

    private void updateUser(UserInfo user) {
        user.setEmail(tvUserEmail.getText().toString());
        user.setRole(Role.valueOf(tvUserRole.getText().toString()));
        user.setDisplayName(etDisplayName.getText().toString());
        user.setPhoneNumber(etPhoneNumber.getText().toString());
    }

    private void fillProfile(UserInfo user) {
        tvUserEmail.setText(user.getEmail());
        tvUserRole.setText(user.getRole().toString());
        etDisplayName.setText(user.getDisplayName());
        etPhoneNumber.setText(user.getPhoneNumber());
    }
}