package com.solvit.mobile.ui.fragments.pending;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Intent;
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
import com.solvit.mobile.databinding.FragmentPendingBinding;
import com.solvit.mobile.model.NotificationModel;

import java.util.List;

public class PendingFragment extends Fragment {

    private FragmentPendingBinding binding;
    private RecyclerView rvPendingNotifications;
    private NotificationAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        PendingViewModel pendingViewModel = new ViewModelProvider(this).get(PendingViewModel.class);

        binding = FragmentPendingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // grab de RecycleView
        rvPendingNotifications = binding.rvPendingNotifications;

        pendingViewModel.getNotificationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<NotificationModel>>() {
            @Override
            public void onChanged(List<NotificationModel> NotificationModelList) {
                Log.d(TAG, "onChanged: inside" + NotificationModelList);
                updateRecyclerView(NotificationModelList);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateRecyclerView(List<NotificationModel> NotificationModelList){
        adapter = new NotificationAdapter(getActivity().getApplicationContext(), NotificationModelList, new NotificationAdapter.OnItemClickListener() {
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

        rvPendingNotifications.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPendingNotifications.setItemAnimator(new DefaultItemAnimator());
        rvPendingNotifications.setAdapter(adapter);
    }
}