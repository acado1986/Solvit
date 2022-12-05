package com.solvit.mobile;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solvit.mobile.adminit.NotificationAdapter;
import com.solvit.mobile.adminit.NotificationDetailsAdminIT;
import com.solvit.mobile.model.NotificationModelIT;

import java.util.ArrayList;

public class PendingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // grab de RecycleView
        RecyclerView rvNotifications = view.findViewById(R.id.rvNotifications);
        // grab the intent extra notificacions array
        ArrayList<NotificationModelIT> notifications = (ArrayList<NotificationModelIT>)getActivity().getIntent().getSerializableExtra("notifications");
        Log.d(TAG, "notificationes" + String.valueOf(notifications));

        NotificationAdapter adapter = new NotificationAdapter(getActivity(), notifications, new NotificationAdapter.OnItemClickListener() {
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

        rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNotifications.setItemAnimator(new DefaultItemAnimator());
        rvNotifications.setAdapter(adapter);
    }
}
