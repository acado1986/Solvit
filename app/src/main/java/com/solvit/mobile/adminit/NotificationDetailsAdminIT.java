package com.solvit.mobile.adminit;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.solvit.mobile.R;
import com.solvit.mobile.model.NotificationModel;
import com.solvit.mobile.model.NotificationModelIT;
import com.solvit.mobile.model.UserInfo;
import com.solvit.mobile.repositories.FirebaseRepository;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationDetailsAdminIT extends AppCompatActivity {

    TextView tvRole;
    Spinner spSpinner;
    private FirebaseRepository<UserInfo> mRepo;
    String[]users={"adminIT","admin,Maintenance","AdminSecrtary","worker IT 1","worker IT 2",
    "worker maintenance 1","worker maintenance 2","worker secretary 1","worker secretary 2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details_admin_it);

        tvRole = findViewById(R.id.tvRole);
        spSpinner=findViewById(R.id.spSpinner);
        // get user list
        mRepo = new FirebaseRepository<>();
        LiveData<List<UserInfo>> usersInfo = mRepo.getDataSet();
        mRepo.startChangeListener("users",
                new HashMap<String,String>(),
                UserInfo.class);
        usersInfo.observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> userInfos) {
                HashMap<String, String> nombres = new HashMap<>();
                userInfos.stream().forEach(e->nombres.put(e.getUid(), e.getDisplayName() == null? e.getEmail() : e.getDisplayName()));
                Log.d(TAG, "onChangedactivity: " + nombres);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, new ArrayList(nombres.values()));
                spSpinner.setAdapter(adapter);
            }
        });

        NotificationModel notification = (NotificationModel)getIntent().getSerializableExtra("notification");
        tvRole.setText(notification.getRole().toString());

    }
}