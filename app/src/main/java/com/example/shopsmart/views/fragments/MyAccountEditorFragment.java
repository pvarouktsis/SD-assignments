package com.example.shopsmart.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.shopsmart.R;
import com.example.shopsmart.models.User;
import com.example.shopsmart.views.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyAccountEditorFragment extends Fragment {
    protected static final String TAG = "MY_ACCOUNT_EDITOR_F";
    protected User user;
    protected String sUsername;
    protected String sEmail;
    protected String sPassword;
    protected EditText etUsername;
    protected EditText etEmail;
    protected EditText etPassword;
    protected Button btnUpdate;
    protected FirebaseAuth fa;
    protected FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View myAccountEditorView = inflater.inflate(R.layout.fragment_my_account_editor, container, false);
        initializeUIComponents(myAccountEditorView);

        // initialize firebase
        fa = FirebaseAuth.getInstance();

        // show Toast
        showToast("Fill in all the fields");


        return myAccountEditorView;
    }

    protected void initializeUIComponents(View myAccountEditorView) {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize components
        etUsername = myAccountEditorView.findViewById(R.id.et_username);
        etEmail = myAccountEditorView.findViewById(R.id.et_email);
        etPassword = myAccountEditorView.findViewById(R.id.et_password);
        btnUpdate = myAccountEditorView.findViewById(R.id.btn_update);

        // on click
        btnUpdate.setOnClickListener(updateListener);
    }

    protected void getUser() {
        Log.d(TAG, "getUser: called");
        FirebaseUser fu = fa.getCurrentUser();
        ffdb.collection("users")
            .document(fu.getUid())
            .get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "getUser: succeeded");
                        DocumentSnapshot d = task.getResult();
                        user = d.toObject(User.class);
                        updateUsername();
                    } else {
                        Log.w(TAG, "getUser: failed", task.getException());
                    }
                }
            });
    }

    // TODO
    protected void updateUsername() {
        Log.d(TAG, "updateUser: called");
        if (!sUsername.isEmpty()) {
            user.setUsername(sUsername);
            updateEmail();
        } else {
            //updateEmail();
        }
    }

    protected void updateEmail() {
        Log.d(TAG, "updateUserEmailFromFirebaseAuthentication: called");
        FirebaseUser fu = fa.getCurrentUser();
        if (!sEmail.isEmpty()) {
            fu.updateEmail(sEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "updateEmail: succeeded");
                            user.setEmail(sEmail);
                        } else {
                            Log.w(TAG, "updateEmail: failed", task.getException());
                            showToast("Update account failed");
                        }
                        updatePassword();
                    }
                });
        } else {
            //updatePassword();
        }
    }

    protected void updatePassword() {
        Log.d(TAG, "updateUserPasswordFromFirebaseAuthentication: called");
        FirebaseUser fu = fa.getCurrentUser();
        if (!sPassword.isEmpty()) {
            fu.updatePassword(sPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "updatePassword: succeeded");
                            user.setPassword(sPassword);
                        } else {
                            Log.w(TAG, "updatePassword: failed", task.getException());
                            showToast("Update account failed");
                        }
                        writeUser();
                    }
                });
        } else {
            //writeUser();
        }
    }

    protected void writeUser() {
        Log.d(TAG, "writeUser: called");
        FirebaseUser fu = fa.getCurrentUser();
        ffdb.collection("users")
            .document(fu.getUid())
            .set(user)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "writeUser: succeeded");
                        reauthenticateUser();
                    } else {
                        Log.w(TAG, "writeUser: failed", task.getException());
                        showToast("Update account failed");
                    }
                }
            });
    }

    protected void reauthenticateUser() {
        Log.d(TAG, "reauthenticateUser: called");
        FirebaseUser fu = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(sEmail, sPassword);
        fu.reauthenticate(credential)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "reauthendicateUser: succeeded");
                        updateUI();
                    } else {
                        Log.w(TAG, "reauthendicateUser: failed", task.getException());
                    }
                }
            });
    }

    protected void updateUI() {
        Log.d(TAG, "updateUI: called");
        FirebaseUser fu = fa.getCurrentUser();
        if (fu != null) {
            showToast("Updated account successfully");
            goToMainActivity();
        }
    }

    protected void goToMainActivity() {
        Log.d(TAG, "goToMainActivity: called");
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    protected void convertEditTextToString() {
        // TODO
        sUsername = etUsername.getText().toString().trim();
        sEmail = etEmail.getText().toString().trim();
        sPassword = etPassword.getText().toString().trim();
    }

    protected View.OnClickListener updateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "updateListener: called");
            convertEditTextToString();
            getUser();
        }
    };
}
