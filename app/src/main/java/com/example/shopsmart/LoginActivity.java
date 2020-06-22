package com.example.shopsmart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "TAG_LOGIN";
    private String sEmail;
    private String sPassword;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initialize layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeUIComponents();
        // initialize Firebase
        fa = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser fu = fa.getCurrentUser();
        updateUI(fu);
    }

    private void initializeUIComponents() {
        // initialize components
        etEmail = findViewById(R.id.input_email);
        etPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.button_login);
        btnRegister = findViewById(R.id.button_register);
        // on click
        btnLogin.setOnClickListener(loginListener);
        btnRegister.setOnClickListener(registerListener);
    }

    private void loginUser() {
        fa.signInWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmailAndPassword:success");
                            FirebaseUser fu = fa.getCurrentUser();
                            updateUI(fu);
                        } else {
                            Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void convertEditTextToString() {
        sEmail = etEmail.getText().toString().trim();
        sPassword = etPassword.getText().toString().trim();
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

    private void goToRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    /*
     * Login's button OnClickListener
     */
    private View.OnClickListener loginListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    convertEditTextToString();
                    loginUser();
                }
            };

    /*
     * Register's button OnClickListener
     */
    private View.OnClickListener registerListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToRegisterActivity();
                }
            };

}