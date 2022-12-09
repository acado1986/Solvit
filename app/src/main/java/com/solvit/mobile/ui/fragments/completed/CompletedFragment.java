package com.solvit.mobile.ui.fragments.completed;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solvit.mobile.adapters.NotificationAdapter;
import com.solvit.mobile.activities.NotificationDetailsActivity;
import com.solvit.mobile.databinding.FragmentCompletedBinding;
import com.solvit.mobile.model.NotificationModel;

import java.util.List;

public class CompletedFragment extends Fragment {

    private FragmentCompletedBinding binding;
    private RecyclerView rvCompletedNotifications;
    private NotificationAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CompletedViewModel completedViewModel =
                new ViewModelProvider(this).get(CompletedViewModel.class);

        //start listener

        completedViewModel.startListener(getCollectionPath());

        binding = FragmentCompletedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvCompletedNotifications = binding.rvCompletedNotifications;

        completedViewModel.getNotificationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<NotificationModel>>() {
            @Override
            public void onChanged(List<NotificationModel> notificationModelList) {
                updateRecyclerView(notificationModelList);
            }
        });
        return root;
    }

    private String getCollectionPath() {
        SharedPreferences sharedPref = getContext().getSharedPreferences("loginPref", Context.MODE_PRIVATE);
        int collectionPath = sharedPref.getInt("collectionPath", 0);;
        return getResources().getString(collectionPath);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateRecyclerView(List<NotificationModel> notificationModelList){
        adapter = new NotificationAdapter(getActivity().getApplicationContext(), notificationModelList, new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NotificationModel notification) {
                // get to notifications activity
                Intent intent = new Intent(getActivity(), NotificationDetailsActivity.class);
                Log.d(ContentValues.TAG, String.valueOf(notification));
                intent.putExtra("notification", notification);
                startActivity(intent);
            }
        });
        adapter.notifyDataSetChanged();

        rvCompletedNotifications.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCompletedNotifications.setItemAnimator(new DefaultItemAnimator());
        rvCompletedNotifications.setAdapter(adapter);
    }
}