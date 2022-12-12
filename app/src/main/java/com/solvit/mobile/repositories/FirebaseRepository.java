package com.solvit.mobile.repositories;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.solvit.mobile.model.Uid;
import com.solvit.mobile.model.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic class to connect to the Firebase Repository for notifications or users.
 * It uses live data for asynchronous calls
 *
 * @param <T>
 */
public class FirebaseRepository<T extends Uid> {

    // Firebase connection
    private final FirebaseFirestore db;
    private final MutableLiveData<List<T>> dataSet;
    private final MutableLiveData<T> data;
    private Query query;
    private ListenerRegistration registration;

    public FirebaseRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.dataSet = new MutableLiveData<>();
        this.data = new MutableLiveData<>();
    }


    public MutableLiveData<List<T>> getDataSet() {
        return dataSet;
    }

    public MutableLiveData<T> getData() {
        return data;
    }

    /**
     * Method to start a listener for changes and update the live data
     *
     * @param collectionPath the path to the resource
     * @param whereFilters   filter the query (only equality)
     * @param cls            class of object to populate with data
     */
    public void startChangeListener(String collectionPath, Map<String, String> whereFilters, Class<T> cls) {
        // Create a reference to the cities collection
        query = db.collection(collectionPath);
        if (query == null) {
            return;
        }
        for (Map.Entry<String, String> filter : whereFilters.entrySet()) {
            query = query.whereEqualTo(filter.getKey(), filter.getValue());
        }
        registration = query.addSnapshotListener((value, error) -> {
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
            Log.d(TAG, "on listener: " + dataList);
        });
    }

    /**
     * Stop the listeners
     */
    public void stopListener() {
        if (registration != null) {
            registration.remove();
        }
    }

    /**
     * Write data to Firestore
     *
     * @param refId          document reference
     * @param collectionPath path to the resources
     * @param data           to write
     */
    public void writeData(String refId, String collectionPath, T data) {
        DocumentReference docRef;
        if (refId != null) {
            docRef = db.collection(collectionPath).document(refId);
        } else {
            docRef = db.collection(collectionPath).document();
        }
        docRef.set(data, SetOptions.merge())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Document created with ID: " + refId);

                    } else {
                        Log.e(TAG, "Error adding document");
                    }
                });
        docRef.update("updatedAt", FieldValue.serverTimestamp());
    }

    /**
     * Delete data from Firestore
     *
     * @param refId          document reference
     * @param collectionPath to to resource
     */
    public void deleteData(String refId, String collectionPath) {
        DocumentReference docRef;
        if (refId != null) {
            docRef = db.collection(collectionPath).document(refId);

            docRef.delete()
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                    .addOnFailureListener(e -> Log.w(TAG, "Error deleting document", e));
        }
    }

    /**
     * Gets aditional data of a user from Firestore
     *
     * @param userUid
     * @param cls     class of the user object to update the live data field
     */
    public void checkUserInfo(String userUid, Class<T> cls) {
        DocumentReference docRef = db.collection("users").document(userUid);
        docRef.get().addOnCompleteListener(task -> {
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
        });
    }

    /**
     * Activate a user that is registered
     *
     * @param userUid
     * @param role
     * @param active
     */
    public void activateUser(String userUid, String role, boolean active) {
        Map<String, Object> data = new HashMap<String, Object>() {{
            put("role", role);
            put("active", active);
        }};
        DocumentReference docRef = db.collection("users").document(userUid);
        if (docRef == null) {
            return;
        }
        docRef.update(data)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
    }

    /**
     * Update a user information in Firestore
     *
     * @param userInfo
     */
    public void updateUser(UserInfo userInfo) {
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
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User created with ID: " + docRef.getId());

                    } else {
                        Log.e(TAG, "Error adding document");
                    }
                });
        docRef.update("updatedAt", FieldValue.serverTimestamp());
    }
}
