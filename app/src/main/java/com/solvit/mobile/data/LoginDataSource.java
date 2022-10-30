package com.solvit.mobile.data;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.solvit.mobile.MainActivity;
import com.solvit.mobile.data.model.LoggedInUser;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private FirebaseAuth mAuth;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // Create a new ThreadPoolExecutor with 2 threads for each processor on the
            // device and a 60 second keep-alive time.
            int numCores = Runtime.getRuntime().availableProcessors();
            ThreadPoolExecutor executor = new ThreadPoolExecutor(numCores * 2, numCores *2,
                    60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

            mAuth = FirebaseAuth.getInstance();
            Task<AuthResult> firebaseUser = mAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(executor, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail: success");
                                FirebaseUser user = mAuth.getCurrentUser();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            }
                        }
                    });
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            firebaseUser.getResult().getUser().toString());
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}