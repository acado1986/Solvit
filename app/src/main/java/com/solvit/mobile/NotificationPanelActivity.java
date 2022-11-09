package com.solvit.mobile;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationPanelActivity extends AppCompatActivity {


    ArrayList<NotificationModel> notifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_panel);

        // grab de RecycleView
        RecyclerView rvNotifications = findViewById(R.id.rvNotifications);
        // grab the intent extra notificacions array
        notifications = (ArrayList<NotificationModel>)getIntent().getSerializableExtra("notifications");
        Log.d(TAG, "notificationes" + String.valueOf(notifications));

        NotificationAdapter adapter = new NotificationAdapter(this, notifications);

        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        rvNotifications.setItemAnimator(new DefaultItemAnimator());
        rvNotifications.setAdapter(adapter);
    }

}