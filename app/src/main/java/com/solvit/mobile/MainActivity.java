package com.solvit.mobile;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.solvit.mobile.adminit.NotificationPanelAdminITActivity;
import com.solvit.mobile.model.Completed;
import com.solvit.mobile.model.NotificationModelIT;
import com.solvit.mobile.model.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView prueba;
    private Button btnReadData;
    private Button btnWriteData;
    private Button btnUpdateData;
    private Button btnSignOut;


    private ArrayList<NotificationModelIT> notificationsGroup;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check if the user is logged in
       mAuth = FirebaseAuth.getInstance();

        prueba = findViewById(R.id.prueba);
        btnReadData = findViewById(R.id.btnReadData);
        btnWriteData = findViewById(R.id.btnWriteData);
        btnUpdateData = findViewById(R.id.btnUpdateData);
        btnSignOut = findViewById(R.id.btnSignOut);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        btnReadData.setOnClickListener(view -> {
            notificationsGroup = new ArrayList<NotificationModelIT>();
            // Create a reference to the cities collection
            db.collection("events/it/it_events")
//                .whereEqualTo("role", "admin")
                    // .whereEqualTo("completed","done")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document :
                                    task.getResult()) {
                                 Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> data = document.getData();
                                NotificationModelIT notification = new NotificationModelIT();
//                                try {
                                    notification.setId(document.getId());
                                    notification.setCompleted(Completed.valueOf(data.get("completed").toString()));
                                    notification.setPcNumber((long) data.get("pcNumber"));
                                    notification.setRole(Role.valueOf(data.get("role").toString()));
                                    notification.setRoom(data.get("room").toString());
                                    notification.setBuilding(data.get("building").toString());
                                    notification.setDescription(data.get("description").toString());
                                    //notification.setUser(((DocumentReference) data.get("user")).getPath());
                                    // get the list of references not working
                                    //notification.setFowardTo((List<String>)data.get("fowardTo"));
                                    notificationsGroup.add(notification);
//                                } catch (ClassCastException cce){
//                                    Log.d(TAG, "ReadData: " + cce.getMessage());
//                                    Toast.makeText(this, "No fue posible leer los datos", Toast.LENGTH_LONG).show();
//                                }
                                // get to notifications activity
                                Intent intent = new Intent(this, NotificationPanelAdminITActivity.class);
                                Log.d(TAG, String.valueOf(notificationsGroup));
                                intent.putExtra("notifications", notificationsGroup);
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
                    .add(new NotificationModelIT
                            (Completed.ADMIN, Role.ADMIN, "a12", "Goya", "test de lo que ha pasado", "users/LLlNAwGWxYBsvaWUKbiK", Arrays.asList("users/LLlNAwGWxYBsvaWUKbiK", "users/allrdjZJvsLWZszBv49h"),1))
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
            DocumentReference updateRef = db.collection("events/it/it_events").document("duwI44Cx2kWECZEc6bvN");
            updateRef
                    .update("completed", Completed.WORKER)
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

        btnSignOut.setOnClickListener((view) -> {
            FirebaseAuth.getInstance().signOut();
            finishAffinity();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}