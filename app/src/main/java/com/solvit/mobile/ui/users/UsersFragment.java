package com.solvit.mobile.ui.users;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.solvit.mobile.R;
import com.solvit.mobile.adminit.UserAdapter;
import com.solvit.mobile.databinding.FragmentUsersBinding;
import com.solvit.mobile.model.UserInfo;

import java.util.List;

public class UsersFragment extends Fragment {

    private FragmentUsersBinding binding;
    private RecyclerView rvUsers;
    private UserAdapter adapter;
    private UsersViewModel usersViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        binding = FragmentUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // grab de RecycleView
        rvUsers = binding.rvUsers;

        usersViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> usersList) {
                Log.d(TAG, "onChanged: inside" + usersList);
                updateRecyclerView(usersList);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateRecyclerView(List<UserInfo> userList){
        adapter = new UserAdapter(getActivity().getApplicationContext(), userList, new UserAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b, View itemView, UserInfo userInfo) {
                if(userInfo.getUid() != null){
                    Spinner role = itemView.findViewById(R.id.spFillRole);
                    Log.d(TAG, "onCheckedChanged: " + userInfo.getUid());
                    usersViewModel.activateUser(userInfo.getUid(), role.getSelectedItem().toString(), b);
                } else {
                    Toast.makeText(getContext(), "No se puede activar el usuario, no tiene asignado un identificador", Toast.LENGTH_SHORT).show();
                }

            }
        });

        adapter.notifyDataSetChanged();
        rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvUsers.setItemAnimator(new DefaultItemAnimator());
        rvUsers.setAdapter(adapter);
    }
}