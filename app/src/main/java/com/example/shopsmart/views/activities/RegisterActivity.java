package com.example.shopsmart.views.activities;

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
import com.example.shopsmart.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "REGISTER_A";
    private User user;
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;
    private Button btnLogin;
    private FirebaseAuth fa;
    private FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");

        // initialize ui
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeUIComponents();

        // initialize firebase
        fa = FirebaseAuth.getInstance();
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
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);

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
                        Log.d(TAG, "registerUser: succeeded");
                        FirebaseUser fu = fa.getCurrentUser();
                        setProfileUser(fu);
                    } else {
                        Log.w(TAG, "registerUser: failed", task.getException());
                        Toast.makeText(RegisterActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void setProfileUser(final FirebaseUser fu) {
        Log.d(TAG, "setProfileUser: called");

        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
            .setDisplayName(user.getUsername())
            .build();

        fu.updateProfile(profile)
            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "setProfileUser: succeeded");
                        writeUser(fu);
                    } else {
                        Log.w(TAG, "registerUser: failed", task.getException());
                    }
                }
            });
    }

    private void writeUser(final FirebaseUser fu) {
        Log.d(TAG, "writeUser: called");
        ffdb.collection("users")
            .document(fu.getUid())
            .set(user)
            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "writeUser: succeeded");
                        updateUI(fu);
                    } else {
                        Log.w(TAG, "writeUser: failed");
                    }
                }
            });
    }

    private void updateUI(FirebaseUser fu) {
        Log.d(TAG, "updateUI: called");
        if (fu != null) {
            Toast.makeText(RegisterActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        Log.d(TAG, "goToMainActivity: called");
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }

    private void goToLoginActivity() {
        Log.d(TAG, "goToLoginActivity: called");
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    private void createUser() {
        Log.d(TAG, "createUser: called");
        user = new User(
            etUsername.getText().toString().trim(),
            etEmail.getText().toString().trim(),
            etPassword.getText().toString().trim()
        );
    }

    private View.OnClickListener registerListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "registerListener: called");
                createUser();
                registerUser();
            }
        };

    private View.OnClickListener loginListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "loginListener: called");
                goToLoginActivity();
            }
        };

}