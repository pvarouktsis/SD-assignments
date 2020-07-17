package com.example.shopsmart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopsmart.R;
import com.example.shopsmart.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "REGISTER_ACTIVITY";
    private User user;
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;
    private Button btnLogin;
    private FirebaseAuth fa;
    private FirebaseFirestore ffdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        // initialize ui
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeUIComponents();
        // initialize Firebase
        fa = FirebaseAuth.getInstance();
        ffdb = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: called");
        super.onStart();
        FirebaseUser fu = fa.getCurrentUser();
        updateUI(fu);
    }

    private void initializeUIComponents() {
        Log.d(TAG, "initializeUIComponents: called");
        // initialize components
        etUsername = findViewById(R.id.input_username);
        etEmail = findViewById(R.id.input_email);
        etPassword = findViewById(R.id.input_password);
        btnRegister = findViewById(R.id.button_register);
        btnLogin = findViewById(R.id.button_login);
        // on click
        btnRegister.setOnClickListener(registerListener);
        btnLogin.setOnClickListener(loginListener);
    }

    private void registerUser() {
        Log.d(TAG, "registerUser: called");
        fa.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmailAndPasswordTask: succeeded");
                            FirebaseUser fu = fa.getCurrentUser();
                            writeUser();
                            updateUI(fu);
                        } else {
                            Log.w(TAG, "createUserWithEmailAndPasswordTask: failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "sign up failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void writeUser() {
        Log.d(TAG, "writeUser: called");
        ffdb.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "writeUser: succeeded" + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "writeUser: failure");
                    }
                });
    }

    private void createUser() {
        Log.d(TAG, "createUser: called");
        user = new User(
                etUsername.getText().toString().trim(),
                etEmail.getText().toString().trim(),
                etPassword.getText().toString().trim()
        );
    }

    private void updateUI(FirebaseUser fu) {
        Log.d(TAG, "updateUI: called");
        if (fu != null) {
            Log.d(TAG, "signInWithEmailAndPassword: succeeded");
            Toast.makeText(RegisterActivity.this, "sign up succeeded", Toast.LENGTH_SHORT).show();
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        Log.d(TAG, "goToMainActivity: called");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void goToLoginActivity() {
        Log.d(TAG, "goToLoginActivity: called");
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    /*
     * Register's button OnClickListener
     */
    private View.OnClickListener registerListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "registerListener: called");
                    createUser();
                    registerUser();
                }
            };

    /*
     * Login's button OnClickListener
     */
    private View.OnClickListener loginListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "loginListener: called");
                    goToLoginActivity();
                }
            };

}