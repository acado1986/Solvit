package com.solvit.mobile.repositories;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.solvit.mobile.model.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseRepository<T> {

    private static FirebaseRepository instance;
    // Firebase connection
    FirebaseFirestore db;
    private MutableLiveData<List<T>> dataSet;
    private MutableLiveData<T> data;
    private Query query;
    private ListenerRegistration registration;

    public FirebaseRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.dataSet = new MutableLiveData<>();
        this.data = new MutableLiveData<>();
    }


    public MutableLiveData<List<T>> getDataSet(){
        return dataSet;
    }

    public MutableLiveData<T> getData() {
        return data;
    }

    public void startChangeListener(String collectionPath, Map<String, String> whereFilters, Class<T> cls){
        // Create a reference to the cities collection
        query = db.collection(collectionPath);
        for (Map.Entry<String, String> filter: whereFilters.entrySet()) {
            query = query.whereEqualTo(filter.getKey(), filter.getValue());
        }
         registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "listen:error", error);
                            return;
                        }
                        List<T> notificationsList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : value) {
                            T notification = document.toObject(cls);
                            notificationsList.add(notification);
                        }
                        dataSet.postValue(notificationsList);
                    }
                });
    }

    public void stopListener(){
        if(registration != null){
            registration.remove();
        }
    }

    public void createUserInfo(String userUID, UserInfo userInfo){
        db.collection("users")
                .document(userUID)
                .set(userInfo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "UserInfo created with ID: " + userUID);
                        } else {
                            Log.e(TAG, "Error adding user");
                        }
                    }
                });
    }

    public void checkUserInfo(String userUid, Class<T> cls){
        DocumentReference docRef = db.collection("users").document(userUid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        T obj = document.toObject(cls);
                        data.setValue(obj);

                      } else {
                        Log.d(TAG, "No such document");
                                           }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void activateUser(String userUid, String role, boolean active) {
        Map<String, Object> data = new HashMap<String, Object>(){{put("role", role); put("active", active);}};
        DocumentReference docRef = db.collection("users").document(userUid);
        docRef.update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }
}
