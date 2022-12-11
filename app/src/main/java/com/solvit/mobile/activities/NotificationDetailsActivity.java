package com.solvit.mobile.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.solvit.mobile.R;
import com.solvit.mobile.model.NotificationModel;
import com.solvit.mobile.model.NotificationType;
import com.solvit.mobile.model.RevisedBy;
import com.solvit.mobile.model.Status;
import com.solvit.mobile.model.UserInfo;
import com.solvit.mobile.repositories.FirebaseRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NotificationDetailsActivity extends AppCompatActivity {

    private EditText etDetailsUser;
    private EditText etDetailsBuilding;
    private EditText etDetailsRoom;
    private EditText etDetailsDescription;
    private EditText etDetailsPcNumber;
    private Spinner spDetailsStatus;
    private Spinner spDetailsRevisedBy;
    private Spinner spDetailsFowardTo;
    private Button btnSend;
    private Button btnDelete;


    private FirebaseRepository<UserInfo> mRepoUsers;
    private FirebaseRepository<NotificationModel> mRepoNotifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);

        etDetailsUser = findViewById(R.id.etDetailsUser);
        etDetailsBuilding = findViewById(R.id.etDetailsBuild);
        etDetailsRoom = findViewById(R.id.etDetailsRoom);
        etDetailsDescription = findViewById(R.id.etDetailsDescription);
        etDetailsPcNumber = findViewById(R.id.etDetailsPcNumber);
        spDetailsStatus = findViewById(R.id.spDetailsStatus);
        spDetailsRevisedBy = findViewById(R.id.spDetailsRevisedBy);
        spDetailsFowardTo = findViewById(R.id.spDetailsFowardTo);
        btnSend = findViewById(R.id.btnSend);
        btnDelete = findViewById(R.id.btnDeleteNotification);

        // deactivate feature if not admin
        if(getRole() != null){
            if(!getRole().contains("ADMIN")){
                spDetailsRevisedBy.setEnabled(false);
                spDetailsFowardTo.setEnabled(false);
            }
        }
        // get user list
        mRepoUsers = new FirebaseRepository<>();
        mRepoNotifications = new FirebaseRepository<>();
        LiveData<List<UserInfo>> usersInfo = mRepoUsers.getDataSet();
        mRepoUsers.startChangeListener("users",
                new HashMap<String,String>(),
                UserInfo.class);
        usersInfo.observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> userInfos) {
                HashMap<String, String> nombres = new HashMap<>();
                userInfos.stream().forEach(e->nombres.put(e.getUid(), e.getDisplayName() == null? e.getEmail() : e.getDisplayName()));
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, new ArrayList(nombres.values()));
                spDetailsFowardTo.setAdapter(adapter);
            }
        });

        // set the spinners data
        spDetailsStatus.setAdapter(new ArrayAdapter<Status>(this, android.R.layout.simple_spinner_dropdown_item, Status.values()));
        spDetailsRevisedBy.setAdapter(new ArrayAdapter<RevisedBy>(this, android.R.layout.simple_spinner_dropdown_item, RevisedBy.values()));



        NotificationModel notification = (NotificationModel)getIntent().getSerializableExtra("notification");

        setNotificationDetails(notification);

        btnSend.setOnClickListener(view -> {
            resetNotificationDetails(notification);
            mRepoNotifications.writeData(notification.getUid(), getCollectionPath(), notification);
            Toast.makeText(this, "Se ha modificado los detalles de la notification", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, NavigationDrawerActivity.class);
            startActivity(intent);
            finish();
        });

        btnDelete.setOnClickListener(view1 -> {
            mRepoNotifications.deleteData(notification.getUid(), getCollectionPath());
            Toast.makeText(this, "Notifiacion borrada", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, NavigationDrawerActivity.class);
            startActivity(intent);
            finish();
        });

    }


    private void resetNotificationDetails(NotificationModel notification) {
        notification.setBuilding(etDetailsBuilding.getText().toString());
        notification.setRoom(etDetailsRoom.getText().toString());
        notification.setDescription(etDetailsDescription.getText().toString());
        notification.setPcNumber(Long.valueOf(etDetailsPcNumber.getText().toString()));
        notification.setStatus(spDetailsStatus.getSelectedItem().toString());
        notification.setRevisedBy(spDetailsRevisedBy.getSelectedItem().toString());
        notification.setFowardTo(new ArrayList<String>(){{add(spDetailsFowardTo.getSelectedItem().toString());}});
    }

    private void setNotificationDetails(NotificationModel notification) {
        etDetailsUser.setText(notification.getUser());
        etDetailsBuilding.setText(notification.getBuilding());
        etDetailsRoom.setText(notification.getRoom());
        etDetailsDescription.setText(notification.getDescription());
        etDetailsPcNumber.setText(String.valueOf(notification.getPcNumber()));
        spDetailsStatus.setSelection(Status.valueOf(notification.getStatus()).ordinal());
        spDetailsRevisedBy.setSelection(RevisedBy.valueOf(notification.getRevisedBy()).ordinal());
    }

    private String getCollectionPath() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("loginPref", Context.MODE_PRIVATE);
        int collectionPath = sharedPref.getInt("collectionPath", R.string.collectionIt);;
        return getResources().getString(collectionPath);
    }

    private String getRole(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("loginPref", Context.MODE_PRIVATE);
        String role = sharedPref.getString("role", "TIC");;
        return role;
    }
}