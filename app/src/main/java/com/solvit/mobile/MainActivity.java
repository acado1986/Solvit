package com.solvit.mobile;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prueba = findViewById(R.id.prueba);

        Bundle user = getIntent().getExtras();

        prueba.setText(user.getString("username"));


        FirebaseFirestore db= FirebaseFirestore.getInstance();
        // Create a reference to the cities collection
        db.collection("events/it/it_events")
//                .whereEqualTo("role", "admin")
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

        db.collection("user")
               .whereEqualTo("id", "admin")
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


    }
}