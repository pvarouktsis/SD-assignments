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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyAccountDeleteFragment extends Fragment {
    protected static final String TAG = "MY_ACCOUNT_DELETE_F";
    protected User user;
    protected EditText etEmail;
    protected EditText etPassword;
    protected Button btnDelete;
    protected FirebaseAuth fa;
    protected FirebaseFirestore ffdb = FirebaseFirestore.getInstance();
    protected int errorCode = 0;        // if errorCode < 7 then something went wrong

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View myAccountDeleteView = inflater.inflate(R.layout.fragment_my_account_delete, container, false);
        initializeUIComponents(myAccountDeleteView);

        // initialize firebase
        fa = FirebaseAuth.getInstance();

        // show toast
        showToast("Validate your account details");

        return myAccountDeleteView;
    }

    @Override
    protected void initializeUIComponents(View myAccountDeleteView) {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize components
        etEmail = myAccountDeleteView.findViewById(R.id.et_email);
        etPassword = myAccountDeleteView.findViewById(R.id.et_password);
        btnDelete = myAccountDeleteView.findViewById(R.id.btn_delete);

        // on click
        btnDelete.setOnClickListener(deleteListener);
    }

    protected void deleteUser() {
        Log.d(TAG, "deleteUser: called");
        FirebaseUser fu = fa.getCurrentUser();
        reauthenticateUser(fu);
    }

    protected void reauthenticateUser(final FirebaseUser fu) {
        Log.d(TAG, "reauthenticateUser: called");
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), user.getPassword());
        fu.reauthenticate(credential)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "reauthendicateUser: succeeded");
                        errorCode += 1;
                        deleteUserFromFirebaseFirestore(fu);
                    } else {
                        Log.w(TAG, "reauthendicateUser: failed", task.getException());
                        showMessage(task.getException());
                        updateUI();
                    }
                }
            });
    }

    protected void deleteUserFromFirebaseFirestore(final FirebaseUser fu) {
        Log.d(TAG, "deleteUserFromFirebaseFirestore: called");
        ffdb.collection("users")
            .document(fu.getUid())
            .delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "deleteUserFromFirebaseFirestore: succeeded");
                        errorCode += 2;
                        deleteUserFromFirebaseAuthentication(fu);
                    } else {
                        Log.w(TAG, "deleteUserFromFirebaseFirestore: failed", task.getException());
                        showMessage(task.getException());
                        updateUI();
                    }
                }
            });
    }

    protected void deleteUserFromFirebaseAuthentication(final FirebaseUser fu) {
        Log.d(TAG, "deleteUserFromFirebaseAuthentication: called");
        fu.delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "deleteUserFromFirebaseAuthentication: succeeded");
                        errorCode += 4;
                    } else {
                        Log.w(TAG, "deleteUserFromFirebaseAuthentication: failed", task.getException());
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
        } catch (FirebaseAuthInvalidCredentialsException e) {
            showToast("Authentication failed");
        } catch (FirebaseAuthException e) {
            showToast("Delete account failed");
        } catch (Exception e) {
            showToast("Delete account failed");
        }
    }

    protected void updateUI() {
        Log.d(TAG, "updateUI: called");
        Log.d(TAG, "errorCode: " + errorCode);
        dismissLoading();
        if (errorCode == 7) {
            Log.d(TAG, "deleteUser: succeeded");
            showToast("Deleted account successfully");
            goToMainActivity();
        } else if (errorCode < 7) {
            Log.d(TAG, "deleteUser: failed");
            // TODO
            // sync or cancel delete process
        }
    }

    protected void createUser() {
        Log.d(TAG, "createUser: called");
        user = new User(
            etEmail.getText().toString().trim(),
            etPassword.getText().toString().trim()
        );
    }

    protected View.OnClickListener deleteListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "deleteUserListener: called");
                showLoading();
                createUser();
                deleteUser();
            }
        };

}
