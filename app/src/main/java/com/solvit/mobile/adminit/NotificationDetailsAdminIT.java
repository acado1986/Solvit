package com.solvit.mobile.adminit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.solvit.mobile.R;
import com.solvit.mobile.model.NotificationModelIT;

public class NotificationDetailsAdminIT extends AppCompatActivity {

    TextView tvRole;
    Spinner spSpinner;
    String[]users={"adminIT","admin,Maintenance","AdminSecrtary","worker IT 1","worker IT 2",
    "worker maintenance 1","worker maintenance 2","worker secretary 1","worker secretary 2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details_admin_it);

        tvRole = findViewById(R.id.tvRole);
        spSpinner=findViewById(R.id.spSpinner);

        NotificationModelIT notification = (NotificationModelIT)getIntent().getSerializableExtra("notification");
        tvRole.setText(notification.getRole().toString());

        ArrayAdapter <String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,users);
        spSpinner.setAdapter(adapter);

    }
}