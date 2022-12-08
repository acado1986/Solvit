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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.solvit.mobile.model.Uid;
import com.solvit.mobile.model.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseRepository<T extends Uid> {

    // Firebase connection
    private FirebaseFirestore db;
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
        if(query == null){
            return;
        }
        for (Map.Entry<String, String> filter: whereFilters.entrySet()) {
            query = query.whereEqualTo(filter.getKey(), filter.getValue());
        }
         registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "listen:error ", error);
                            return;
                        }
                        List<T> dataList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : value) {
                            T aData = document.toObject(cls);
                            aData.setUid(document.getId());
                            dataList.add(aData);
                        }
                        dataSet.postValue(dataList);
                    }
                });
    }

    public void stopListener(){
        if(registration != null){
            registration.remove();
        }
    }

    public void writeData(String refId, String collectionPath, T data){
        DocumentReference docRef;
        if(refId != null){
           docRef = db.collection(collectionPath).document(refId);
        } else {
            docRef = db.collection(collectionPath).document();
        }
       docRef.set(data, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Document created with ID: " + refId);

                        } else {
                            Log.e(TAG, "Error adding document");
                        }
                    }
                });
        docRef.update("updatedAt", FieldValue.serverTimestamp());
    }

    public void deleteData(String refId, String collectionPath){
        DocumentReference docRef;
        if(refId != null) {
            docRef = db.collection(collectionPath).document(refId);

            docRef.delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error deleting document", e);
                        }
                    });
        }
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
        if(docRef == null){
            return;
        }
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

    public void updateUser(UserInfo userInfo){
        UserProfileChangeRequest userProfile = new UserProfileChangeRequest.Builder()
                .setDisplayName(userInfo.getDisplayName())
                .setPhotoUri(userInfo.getPhotoUrl())
                .build();
        FirebaseAuth.getInstance().getCurrentUser().updateProfile(userProfile)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User profile updated.");
                    }
                });

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(userInfo.getUid());
        docRef.set(userInfo, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User created with ID: " + docRef.getId());

                        } else {
                            Log.e(TAG, "Error adding document");
                        }
                    }
                });
        docRef.update("updatedAt", FieldValue.serverTimestamp());
    }
}
