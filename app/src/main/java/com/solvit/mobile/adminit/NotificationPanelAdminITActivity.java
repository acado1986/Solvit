package com.solvit.mobile.adminit;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.solvit.mobile.R;
import com.solvit.mobile.model.NotificationModel;
import com.solvit.mobile.model.NotificationModel;

import java.util.ArrayList;

public class NotificationPanelAdminITActivity extends AppCompatActivity {


    ArrayList<NotificationModel> notifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_panel);

        // grab de RecycleView
        RecyclerView rvNotifications = findViewById(R.id.rvNotifications);
        // grab the intent extra notificacions array
        notifications = (ArrayList<NotificationModel>)getIntent().getSerializableExtra("notifications");
        Log.d(TAG, "notificationes" + String.valueOf(notifications.get(0).getRoom()));

        NotificationAdapter adapter = new NotificationAdapter(this, notifications, new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NotificationModel notification) {
                Log.d(TAG, "onItemClick: " + notification);
                // get to notifications activity
                Intent intent = new Intent(getApplicationContext(), NotificationDetailsAdminIT.class);
                Log.d(ContentValues.TAG, String.valueOf(notification));
                intent.putExtra("notification", notification);
                startActivity(intent);
            }
        });

        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        rvNotifications.setItemAnimator(new DefaultItemAnimator());
        rvNotifications.setAdapter(adapter);


    }

}