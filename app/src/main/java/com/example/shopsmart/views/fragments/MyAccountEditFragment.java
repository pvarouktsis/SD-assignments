package com.example.shopsmart.views.fragments;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyAccountEditFragment extends Fragment {
    protected static final String TAG = "MY_ACCOUNT_EDITOR_F";
    protected User user;
    protected EditText etUsername;
    protected EditText etEmail;
    protected EditText etPassword;
    protected Button btnUpdate;
    protected FirebaseAuth fa;
    protected FirebaseFirestore ffdb = FirebaseFirestore.getInstance();
    protected int errorCode = 0;        // if errorCode < 15 then something went wrong

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View myAccountEditorView = inflater.inflate(R.layout.fragment_my_account_edit, container, false);
        initializeUIComponents(myAccountEditorView);

        // initialize firebase
        fa = FirebaseAuth.getInstance();

        // show Toast
        showToast("Please fill in all the fields");

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

    // TODO
    // synch update methods

    protected void updateUser() {
        Log.d(TAG, "updateUser: called");
        FirebaseUser fu = fa.getCurrentUser();
        updateEmail(fu);
    }

    protected void updateEmail(final FirebaseUser fu) {
        Log.d(TAG, "updateEmail: called");
        fu.updateEmail(user.getEmail())
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "updateEmail: succeeded");
                        errorCode += 1;
                        updatePassword(fu);
                    } else {
                        Log.w(TAG, "updateEmail: failed", task.getException());
                        showMessage(task.getException());
                        updateUI();
                    }
                }
            });
    }

    protected void updatePassword(final FirebaseUser fu) {
        Log.d(TAG, "updatePassword: called");
        fu.updatePassword(user.getPassword())
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "updatePassword: succeeded");
                        errorCode += 2;
                        updateUsername(fu);
                    } else {
                        Log.w(TAG, "updatePassword: failed", task.getException());
                        showMessage(task.getException());
                        updateUI();
                    }
                }
            });
    }

    protected void updateUsername(final FirebaseUser fu) {
        Log.d(TAG, "updateUser: called");

        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
            .setDisplayName(user.getUsername())
            .build();

        fu.updateProfile(profile)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "updateProfile: succeeded");
                        errorCode += 4;
                        reauthenticateUser(fu);
                    } else {
                        Log.w(TAG, "updateProfile: failed", task.getException());
                        showMessage(task.getException());
                        updateUI();
                    }
                }
            });
    }

    protected void reauthenticateUser(final FirebaseUser fu) {
        Log.d(TAG, "reauthenticateUser: called");
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), user.getPassword());
        fu.reauthenticate(credential)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "reauthenticateUser: succeeded");
                        errorCode += 8;
                    } else {
                        Log.w(TAG, "reauthenticateUser: failed", task.getException());
                        showMessage(task.getException());
                    }
                    updateUI();
                }
            });
    }

    protected void showMessage(Exception exception) {
        Log.d(TAG, "showMessage: called");
        try {
            throw exception;
        } catch (FirebaseAuthUserCollisionException e) {
            showToast("This email already exists");
        } catch (FirebaseAuthWeakPasswordException e) {
            showToast("This password is too weak");
        } catch (FirebaseAuthRecentLoginRequiredException e) {
            showToast("Please sign in and try again");
        } catch (FirebaseAuthException e) {
            showToast("Update account failed");
        } catch (Exception e) {
            showToast("Update account failed");
        }
    }

    protected void updateUI() {
        Log.d(TAG, "updateUI: called");
        Log.d(TAG, "errorCode: " + errorCode);
        dismissLoading();
        if (errorCode == 15) {
            Log.d(TAG, "updateUser: succeeded");
            showToast("Updated account successfully");
            goToMainActivity();
        } else if (errorCode < 15) {
            Log.d(TAG, "updateUser: failed");
            // TODO
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

    protected View.OnClickListener updateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "updateListener: called");
            showLoading();
            createUser();
            updateUser();
        }
    };

}
