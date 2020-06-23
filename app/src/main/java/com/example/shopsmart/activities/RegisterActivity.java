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
    private String TAG = "TAG_REGISTER";
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
        super.onStart();
        FirebaseUser fu = fa.getCurrentUser();
        updateUI(fu);
    }

    private void initializeUIComponents() {
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
        fa.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmailAndPassword:success");
                            FirebaseUser fu = fa.getCurrentUser();
                            writeUser();
                            updateUI(fu);
                        } else {
                            Log.w(TAG, "createUserWithEmailAndPassword:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "register failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void writeUser() {
        ffdb.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void createUser() {
        user = new User(
                etUsername.getText().toString().trim(),
                etEmail.getText().toString().trim(),
                etPassword.getText().toString().trim()
        );

    }

    private void updateUI(FirebaseUser fu) {
        if (fu != null) {
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void goToLoginActivity() {
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
                    goToLoginActivity();
                }
            };

}