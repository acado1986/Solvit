package com.solvit.mobile.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.solvit.mobile.R;
import com.solvit.mobile.model.Role;
import com.solvit.mobile.model.UserInfo;
import com.solvit.mobile.repositories.FirebaseRepository;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnRegister;
    private EditText etRegEmail;
    private EditText etRegPassword;
    private EditText etRegPasswordConfirm;
    private Spinner spRegister;
    private FirebaseRepository<UserInfo> mRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mRepo = new FirebaseRepository();

        btnRegister = findViewById(R.id.btnRegister);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegPasswordConfirm = findViewById(R.id.etRegPasswordConfirm);
        spRegister = findViewById(R.id.spRegister);

        // add values to spinner
        spRegister.setAdapter(new ArrayAdapter<Role>(this, R.layout.spinner_item, Role.values()));



        btnRegister.setOnClickListener(view -> {
            // comprobar si las contraseñas coinciden
            if(!TextUtils.equals(etRegPassword.getText().toString(), etRegPasswordConfirm.getText().toString())){
                Log.d(TAG, "Register: contase;a " + etRegPassword.getText().toString() + " = " + etRegPasswordConfirm.getText().toString());
                Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_LONG).show();
                etRegPasswordConfirm.requestFocus();

            } else if(TextUtils.isEmpty(etRegEmail.getText()) ||
                    TextUtils.isEmpty(etRegPassword.getText()) ||
                    TextUtils.isEmpty(spRegister.getSelectedItem().toString())){
                Toast.makeText(this, "Tienes que facilitar un correo, una contraseña y su rol para crear un usuario", Toast.LENGTH_LONG).show();
                etRegEmail.requestFocus();
            }
            else {
                mAuth.createUserWithEmailAndPassword(etRegEmail.getText().toString(), etRegPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Register success
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    UserInfo userInfo = new UserInfo(firebaseUser, Role.valueOf(spRegister.getSelectedItem().toString()), false);
                                    mRepo.writeData(firebaseUser.getUid(), "users", userInfo);

                                    // inform the user about the administrator checks
                                    new AlertDialog.Builder(RegisterActivity.this)
                                            .setTitle("Informacion")
                                            .setMessage("Usuario credo con exito. Se necesita comprobar la validez de su cuenta por un Administrador.")
                                            .setIcon(android.R.drawable.ic_dialog_info)
                                            .setPositiveButton("Ok", (dialogInterface, i) -> {
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finishAffinity();
                                            })
                                            .show();


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "No se puede crear el usuario." + task.getException().getLocalizedMessage(),
                                            Toast.LENGTH_LONG).show();

                                }
                            }
                        });
            }
        });
    }
}