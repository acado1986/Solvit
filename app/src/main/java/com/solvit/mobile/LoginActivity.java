package com.solvit.mobile;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.solvit.mobile.model.UserInfo;
import com.solvit.mobile.repositories.FirebaseRepository;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnSignIn;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvForgetPassword;
    private TextView tvSignUp;
    private FirebaseRepository<UserInfo> mRepo;
    private MutableLiveData<UserInfo> userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mRepo = new FirebaseRepository<>();
        userInfo = new MutableLiveData<>();

        btnSignIn = findViewById(R.id.btnSignIn);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        tvSignUp = findViewById(R.id.tvSignUp);

        btnSignIn.setOnClickListener(view -> {
            mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                mRepo.checkUserInfo(mAuth.getCurrentUser().getUid(), UserInfo.class);
                                userInfo = mRepo.getData();
                                Log.d(TAG, "signInWithEmail:success " + userInfo);
                                updateUI();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "El usuario no existe en la base de datos.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        tvForgetPassword.setOnClickListener((view)-> {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            startActivity(intent);
        });
        tvSignUp.setOnClickListener((view)-> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void updateUI() {
        Log.d(TAG, "updateUI: " + userInfo);
        userInfo.observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                if (userInfo != null) {
                    Log.d(TAG, userInfo.getEmail());
                    // the user is active
                    if (userInfo.getActive()) {
                        Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                        intent.putExtra("userInfo", userInfo);
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        Toast.makeText(getApplicationContext(), "Su cuenta no esta todavia activada. Contactar con el administrador.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d(TAG, "error login");
                    Toast.makeText(getApplicationContext(), "No existe informacion sobre el usuario. Contactar con el administrador.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}