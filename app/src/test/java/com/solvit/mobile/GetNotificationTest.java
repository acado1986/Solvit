package com.solvit.mobile;

import static android.content.ContentValues.TAG;
import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Test;

public class GetNotificationTest {
    @Test
    public void notifications_isCorrect() {

        assertEquals(4, 2 + 2);
    }
}
