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

public class LoginActivity extends AppCompatActivity {
    private final static String TAG = "LOGIN_A";
    private User user;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");

        // initialize ui
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        Log.d(TAG, "initializeComponents: called");

        // initialize components
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        // on click
        btnLogin.setOnClickListener(loginListener);
        btnRegister.setOnClickListener(registerListener);
    }

    private void loginUser() {
        Log.d(TAG, "loginUser: called");
        fa.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "loginUser: succeeded");
                        FirebaseUser fu = fa.getCurrentUser();
                        updateUI(fu);
                    } else {
                        Log.w(TAG, "loginUser: failed", task.getException());
                        Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                    }

                }
            });
    }

    private void updateUI(FirebaseUser fu) {
        Log.d(TAG, "updateUI: called");
        if (fu != null) {
            Toast.makeText(LoginActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        Log.d(TAG, "goToMainActivity: called");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void goToRegisterActivity() {
        Log.d(TAG, "goToRegisterActivity: called");
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    private void createUser() {
        Log.d(TAG, "createUser: called");
        user = new User(
            etEmail.getText().toString().trim(),
            etPassword.getText().toString().trim()
        );
    }

    private View.OnClickListener loginListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "loginListener: called");
                createUser();
                loginUser();
            }
        };

    private View.OnClickListener registerListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "registerListener: called");
                goToRegisterActivity();
            }
        };

}