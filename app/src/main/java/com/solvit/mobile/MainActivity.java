package com.solvit.mobile;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    private TextView prueba;
    private Button btnTest;
    private String hola;

    private ArrayList<NotificationModel> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prueba = findViewById(R.id.prueba);
        btnTest = findViewById(R.id.btnTest);

        Bundle user = getIntent().getExtras();

        prueba.setText(user.getString("username"));

        notifications = new ArrayList<NotificationModel>();
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        btnTest.setOnClickListener(view -> {

            // Create a reference to the cities collection
            db.collection("events/it/it_events")
//                .whereEqualTo("role", "admin")
                   // .whereEqualTo("completed","done")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document :
                                    task.getResult()) {
                               // Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> data = document.getData();
                                NotificationModel notification = new NotificationModel();
                                  notification.setId(document.getId());
                                  notification.setCompleted(data.get("completed").toString());
                                  notification.setPcNumber((long)data.get("pc_number"));
                                  notification.setRole(data.get("role").toString());
                                  notification.setRoom(data.get("room").toString());
                                  notification.setBuilding(String.valueOf(data.get("building")));
                                  notification.setDescription(data.get("description").toString());
                                  notification.setUser(((DocumentReference)data.get("user")).getPath());
                                  // get the list of references not working
                                // notification.setFowardTo((List<String>)data.get("fowardTo"));
                                notifications.add(notification);
                                // get to notifications activity
                                Intent intent = new Intent(this, NotificationPanelActivity.class);
                                Log.d(TAG, String.valueOf(notifications));
                                intent.putExtra("notifications", (ArrayList<NotificationModel>)notifications);
                                startActivity(intent);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ",
                                    task.getException());
                        }
                    });
//
//            db.collection("events/reception/reception_events")
////                .whereEqualTo("role", "admin")
//
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document :
//                                    task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ",
//                                    task.getException());
//                        }
//                    });
//
//            db.collection("user")
//                    .whereEqualTo("role", "admin")
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document :
//                                    task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ",
//                                    task.getException());
//                        }
//                    });
//
//            db.collection("events/maintenance/maintenance_events")
//                    //.whereEqualTo("completed", "true")
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document :
//                                    task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ",
//                                    task.getException());
//                        }
//                    });

        });
    }
}