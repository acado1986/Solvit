package com.solvit.mobile.ui.main;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solvit.mobile.adminit.NotificationAdapter;
import com.solvit.mobile.adminit.NotificationDetailsAdminIT;
import com.solvit.mobile.databinding.FragmentTabbedBinding;
import com.solvit.mobile.model.NotificationModelIT;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private NotificationsViewModel pageViewModel;
    private FragmentTabbedBinding binding;
    // grab de RecycleView
    private RecyclerView rvNotifications;
    private NotificationAdapter adapter;
    private ActionBar actionBar;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        pageViewModel.init();
        Log.d(TAG, "onCreate: " + pageViewModel);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentTabbedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        actionBar = getActivity().getActionBar();

        pageViewModel.getNotificationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<NotificationModelIT>>() {
            @Override
            public void onChanged(List<NotificationModelIT> notificationModelITList) {
                if(adapter != null){
                   // adapter.updateData(notificationModelITList);
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "onChanged: ");
                }

            }
        });
        initNotificationsRecyclerView();
        Log.d(TAG, "onViewCreated: " + pageViewModel.getNotificationsLiveData());




    final TextView textView = binding.sectionLabel;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initNotificationsRecyclerView(){
        // grab de RecycleView
        RecyclerView rvNotifications = binding.rvNotifications1;
        NotificationAdapter adapter = new NotificationAdapter(getActivity().getApplicationContext(), pageViewModel.getNotificationsLiveData().getValue(),new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NotificationModelIT notification) {
                Log.d(TAG, "onItemClick: " + notification);
                // get to notifications activity
                Intent intent = new Intent(getActivity(), NotificationDetailsAdminIT.class);
                Log.d(ContentValues.TAG, String.valueOf(notification));
                intent.putExtra("notification", notification);
                startActivity(intent);
            }
        });

        rvNotifications.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNotifications.setItemAnimator(new DefaultItemAnimator());
        rvNotifications.setAdapter(adapter);
    }
}