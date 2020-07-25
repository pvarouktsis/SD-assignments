package com.example.shopsmart.views.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.shopsmart.R;
import com.example.shopsmart.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends Activity {
    protected static final String TAG = "REGISTER_A";
    protected User user;
    protected EditText etUsername;
    protected EditText etEmail;
    protected EditText etPassword;
    protected Button btnRegister;
    protected Button btnLogin;
    protected FirebaseAuth fa;
    protected FirebaseFirestore ffdb = FirebaseFirestore.getInstance();
    protected int errorCode = 0;        // if errorCode < 7 then something went wrong

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

    protected void initializeUIComponents() {
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

    protected void registerUser() {
        Log.d(TAG, "registerUser: called");
        fa.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmailAndPassword: succeeded");
                        errorCode += 1;
                        FirebaseUser fu = fa.getCurrentUser();
                        setProfileUser(fu);
                    } else {
                        Log.w(TAG, "createUserWithEmailAndPassword: failed", task.getException());
                        FirebaseUser fu = fa.getCurrentUser();
                        updateUI(fu);
                    }
                }
            });
    }

    protected void setProfileUser(FirebaseUser fu) {
        Log.d(TAG, "setProfileUser: called");

        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
            .setDisplayName(user.getUsername())
            .build();

        fu.updateProfile(profile)
            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "updateProfile: succeeded");
                        errorCode += 2;
                        FirebaseUser fu = fa.getCurrentUser();
                        writeUser(fu);
                    } else {
                        Log.w(TAG, "updateProfile: failed", task.getException());
                        FirebaseUser fu = fa.getCurrentUser();
                        updateUI(fu);
                    }
                }
            });
    }

    protected void writeUser(FirebaseUser fu) {
        Log.d(TAG, "writeUser: called");
        ffdb.collection("users")
            .document(fu.getUid())
            .set(user)
            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "writeUserInFirebaseFirestore: succeeded");
                        errorCode += 4;
                        FirebaseUser fu = fa.getCurrentUser();
                        updateUI(fu);
                    } else {
                        Log.w(TAG, "writeUserInFirebaseFirestore: failed");
                        FirebaseUser fu = fa.getCurrentUser();
                        updateUI(fu);
                    }
                }
            });
    }

    // TODO
    // manage errorCode

    protected void updateUI(FirebaseUser fu) {
        Log.d(TAG, "updateUI: called");
        Log.d(TAG, "errorCode: " + errorCode);
        if (fu != null) {
            Log.d(TAG, "registerUser: succeeded");
            showToast(RegisterActivity.this,"Signed up successfully");
            goToMainActivity(RegisterActivity.this);
        } else {
            Log.d(TAG, "registerUser: failed");
            showToast(RegisterActivity.this, "Sign up failed");
            goToRegisterActivity(RegisterActivity.this);
        }
    }

    protected void createUser() {
        Log.d(TAG, "createUser: called");
        user = new User(
            etUsername.getText().toString().trim(),
            etEmail.getText().toString().trim(),
            etPassword.getText().toString().trim()
        );
    }

    protected View.OnClickListener registerListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "registerListener: called");
                createUser();
                registerUser();
            }
        };

    protected View.OnClickListener loginListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "loginListener: called");
                goToLoginActivity(RegisterActivity.this);
            }
        };

}