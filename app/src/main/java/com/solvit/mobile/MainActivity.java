package com.solvit.mobile;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView prueba;
    private Button btnReadData;
    private Button btnWriteData;
    private Button btnUpdateData;
    private String hola;

    private ArrayList<NotificationModel> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prueba = findViewById(R.id.prueba);
        btnReadData = findViewById(R.id.btnReadData);
        btnWriteData = findViewById(R.id.btnWriteData);
        btnUpdateData = findViewById(R.id.btnUpdateData);

        Bundle user = getIntent().getExtras();

        prueba.setText(user.getString("username"));


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        btnReadData.setOnClickListener(view -> {
            notifications = new ArrayList<NotificationModel>();
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
                                notification.setPc_number((long) data.get("pc_number"));
                                notification.setRole(data.get("role").toString());
                                notification.setRoom(data.get("room").toString());
                                notification.setBuilding(String.valueOf(data.get("building")));
                                notification.setDescription(data.get("description").toString());
                                notification.setUser(((DocumentReference) data.get("user")).getPath());
                                // get the list of references not working
                                // notification.setFowardTo((List<String>)data.get("fowardTo"));
                                notifications.add(notification);
                                // get to notifications activity
                                Intent intent = new Intent(this, NotificationPanelActivity.class);
                                Log.d(TAG, String.valueOf(notifications));
                                intent.putExtra("notifications", notifications);
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

        btnWriteData.setOnClickListener(view -> {
            db.collection("events/it/it_events")
                    .add(new NotificationModel
                            ("done", 130, "admin", "a20", "goya", "es un test", "user/test", Arrays.asList("user1", "user2")))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        });

        btnUpdateData.setOnClickListener((view) -> {
            DocumentReference updateRef = db.collection("events/it/it_events").document("A27VhSNe5BpqsDq2nMHb");
            updateRef
                    .update("id", updateRef.getId())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating document", e);
                        }
                    });
        });
    }
}