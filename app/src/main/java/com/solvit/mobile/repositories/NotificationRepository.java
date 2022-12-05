package com.solvit.mobile.repositories;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.solvit.mobile.model.Completed;
import com.solvit.mobile.model.NotificationModelIT;
import com.solvit.mobile.model.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationRepository {

    private static NotificationRepository instance;
    private List<NotificationModelIT> notificationsDataSet = new ArrayList<>();

    // Firebase connection
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    /**
     * Singelton pattern
     * @return repository
     */
    public static NotificationRepository getInstance(){
        if(instance == null){
            instance = new NotificationRepository();
        }
        return instance;
    }

    public List<NotificationModelIT> getNotificationsDataSet(String path) {
        // Create a reference to the cities collection
        db.collection(path)
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
                            notificationsDataSet.add(notification);
                            //                                } catch (ClassCastException cce){
                            //                                    Log.d(TAG, "ReadData: " + cce.getMessage());
                            //                                    Toast.makeText(this, "No fue posible leer los datos", Toast.LENGTH_LONG).show();
                            //                                }
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ",
                                task.getException());
                    }
                });
        Log.d(TAG, "getNotificationsDataSet: " + notificationsDataSet);
        return notificationsDataSet;
    }

}
