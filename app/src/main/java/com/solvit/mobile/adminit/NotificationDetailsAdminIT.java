package com.solvit.mobile.adminit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.solvit.mobile.R;
import com.solvit.mobile.model.NotificationModelIT;

public class NotificationDetailsAdminIT extends AppCompatActivity {

    TextView tvRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details_admin_it);

        tvRole = findViewById(R.id.tvRole);

        NotificationModelIT notification = (NotificationModelIT)getIntent().getSerializableExtra("notification");
        tvRole.setText(notification.getRole().toString());

    }
}