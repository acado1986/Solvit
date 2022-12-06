package com.solvit.mobile.repositories;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.solvit.mobile.model.Completed;
import com.solvit.mobile.model.NotificationModelIT;
import com.solvit.mobile.model.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationRepository {

    private static com.solvit.mobile.repositories.NotificationRepository instance;
    // Firebase connection
    FirebaseFirestore db;
    public MutableLiveData<List<NotificationModelIT>> notificationsDataSet;

    private NotificationRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.notificationsDataSet = new MutableLiveData<>();
    }

    /**
     * Singelton pattern
     *
     * @return repository
     */
    public static com.solvit.mobile.repositories.NotificationRepository getInstance() {
        if (instance == null) {
            instance = new com.solvit.mobile.repositories.NotificationRepository();
        }
        return instance;
    }

    public MutableLiveData<List<NotificationModelIT>> getNotificationsDataSet(){
        return notificationsDataSet;
    }

    public void startNotificationsChangeListener(){

        // Create a reference to the cities collection
        db.collection("events/it/it_events")
                //                .whereEqualTo("role", "admin")
                // .whereEqualTo("completed","done")

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "listen:error", error);
                            return;
                        }
                        List<NotificationModelIT> notificationsList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : value) {
                            NotificationModelIT notification = document.toObject(NotificationModelIT.class);
                            notificationsList.add(notification);
                        }
                        notificationsDataSet.postValue(notificationsList);
                    }
                });
    }
}
