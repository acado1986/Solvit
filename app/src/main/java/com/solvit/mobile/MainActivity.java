package com.solvit.mobile;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView prueba;
    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prueba = findViewById(R.id.prueba);
        btnTest = findViewById(R.id.btnTest);

        Bundle user = getIntent().getExtras();

        prueba.setText(user.getString("username"));

        FirebaseFirestore db= FirebaseFirestore.getInstance();
        btnTest.setOnClickListener(view -> {

            // Create a reference to the cities collection
            db.collection("events/it/it_events")
//                .whereEqualTo("role", "admin")
                    .whereEqualTo("completed","done")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document :
                                    task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
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