package com.solvit.mobile.ui.completed;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solvit.mobile.adminit.NotificationAdapter;
import com.solvit.mobile.adminit.NotificationDetailsAdminIT;
import com.solvit.mobile.databinding.FragmentCompletedBinding;
import com.solvit.mobile.model.Completed;
import com.solvit.mobile.model.NotificationModelIT;

import java.util.List;

public class CompletedFragment extends Fragment {

    private FragmentCompletedBinding binding;
    private RecyclerView rvCompletedNotifications;
    private NotificationAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CompletedViewModel completedViewModel =
                new ViewModelProvider(this).get(CompletedViewModel.class);

        binding = FragmentCompletedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvCompletedNotifications = binding.rvCompletedNotifications;

        completedViewModel.getNotificationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<NotificationModelIT>>() {
            @Override
            public void onChanged(List<NotificationModelIT> notificationModelITList) {
                Log.d(TAG, "onChanged: inside" + notificationModelITList);
                updateRecyclerView(notificationModelITList);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateRecyclerView(List<NotificationModelIT> notificationModelITList){
        adapter = new NotificationAdapter(getActivity().getApplicationContext(), notificationModelITList, new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NotificationModelIT notification) {
                // get to notifications activity
                Intent intent = new Intent(getActivity(), NotificationDetailsAdminIT.class);
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