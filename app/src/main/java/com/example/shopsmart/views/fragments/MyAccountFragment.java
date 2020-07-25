package com.example.shopsmart.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.shopsmart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyAccountFragment extends Fragment {
    protected static final String TAG = "MY_ACCOUNT_F";
    protected Button btnUserEdit;
    protected Button btnUserDelete;
    protected Button btnUserLogout;
    protected FirebaseAuth fa;
    protected FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View myAccountView = inflater.inflate(R.layout.fragment_my_account, container, false);
        initializeUIComponents(myAccountView);

        // initialize firebase
        fa = FirebaseAuth.getInstance();

        return myAccountView;
    }

    protected void initializeUIComponents(View myAccountView) {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize components
        btnUserEdit = myAccountView.findViewById(R.id.btn_user_edit);
        btnUserDelete = myAccountView.findViewById(R.id.btn_user_delete);
        btnUserLogout = myAccountView.findViewById(R.id.btn_user_logout);

        // on click
        btnUserEdit.setOnClickListener(editUserListener);
        btnUserDelete.setOnClickListener(deleteUserListener);
        btnUserLogout.setOnClickListener(logoutUserListener);
    }

    protected void logoutUser() {
        Log.d(TAG, "logoutUser: called");
        fa.signOut();
        FirebaseUser fu = fa.getCurrentUser();
        updateUI(fu);
    }

    protected void updateUI(FirebaseUser fu) {
        Log.d(TAG, "updateUI: called");
        if (fu == null) {
            Log.d(TAG, "logoutUser: succeeded");
            showToast("Signed out successfully");
            goToMainActivity();
        } else {
            Log.d(TAG, "logoutUser: failed");
            showToast("Sign out failed");
        }

    }

    protected View.OnClickListener editUserListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "editUserListener: called");
                Fragment f = new MyAccountEditFragment();
                replace(R.id.fl_main_container, f);
            }
        };

    protected View.OnClickListener deleteUserListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "deleteUserListener: called");
                Fragment f = new MyAccountDeleteFragment();
                replace(R.id.fl_main_container, f);
            }
        };

    protected View.OnClickListener logoutUserListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "logoutUserListener: called");
                logoutUser();
            }
        };
}