package com.solvit.mobile.repositories;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.solvit.mobile.model.Completed;
import com.solvit.mobile.model.NotificationModelIT;
import com.solvit.mobile.model.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationRepository<T> {

    private static com.solvit.mobile.repositories.NotificationRepository instance;
    // Firebase connection
    FirebaseFirestore db;
    private MutableLiveData<List<T>> notificationsDataSet;
    private Query query;
    ListenerRegistration registration;

    public NotificationRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.notificationsDataSet = new MutableLiveData<>();
    }


    public MutableLiveData<List<T>> getNotificationsDataSet(){
        return notificationsDataSet;
    }

    public void startNotificationsChangeListener(String collectionPath, Map<String, String> whereFilters,Class<T> cls){
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
                        notificationsDataSet.postValue(notificationsList);
                    }
                });
    }

    public void stopListener(){
        if(registration != null){
            registration.remove();
        }
    }
}
