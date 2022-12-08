package com.solvit.mobile.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.solvit.mobile.R;
import com.solvit.mobile.activities.LoginActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etResetEmail;
    private Button btnSendReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etResetEmail = findViewById(R.id.etResetEmail);
        btnSendReset = findViewById(R.id.btnSendReset);

        btnSendReset.setOnClickListener((view)->{
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(etResetEmail.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Se ha mandado un correo para restablecer su contraseña", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Email sent.");
                            Intent intent = new Intent(this, LoginActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    });
        });

    }
}