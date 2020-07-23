package com.example.shopsmart.views.fragments.my_account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopsmart.R;
import com.example.shopsmart.views.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyAccountFragment extends Fragment {
    private static final String TAG = "MY_ACCOUNT_FRAGMENT";
    private Button btnUserEdit;
    private Button btnUserDelete;
    private Button btnUserLogout;
    private FirebaseAuth fa;
    private FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

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

    private void initializeUIComponents(View myAccountView) {
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

    // TODO

    private void deleteUserFromFirebaseFirestore() {
        Log.d(TAG, "deleteUserFromFirebaseFirestore: called");
        FirebaseUser fu = fa.getCurrentUser();
        ffdb.collection("users")
            .document(fu.getUid())
            .delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "deleteUserFromFirestore: succeeded");
                        deleteUserFromFirebaseAuthentication();
                    } else {
                        Log.w(TAG, "deleteUserFromFirestore: failed", task.getException());
                    }
                }
            });
    }

    private void deleteUserFromFirebaseAuthentication() {
        Log.d(TAG, "deleteUserFromFirebaseAuthentication: called");
        FirebaseUser fu = fa.getCurrentUser();
        fu.delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "deleteUserFromFirebaseAuthentication: succeeded");
                        Toast.makeText(getContext(), "deleted account successfully", Toast.LENGTH_SHORT).show();
                        updateUI();
                    } else {
                        Log.w(TAG, "deleteUserFromFirebaseAuthentication: failed", task.getException());
                        Toast.makeText(getContext(), "delete account failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    private void logoutUser() {
        Log.d(TAG, "logoutUser: called");
        fa.signOut();
        Log.d(TAG, "logoutUser: succeeded");
        Toast.makeText(getContext(), "signed out successfully", Toast.LENGTH_SHORT).show();
        updateUI();
    }

    private void updateUI() {
        Log.d(TAG, "updateUI: called");
        FirebaseUser fu = fa.getCurrentUser();
        if (fu == null) {
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        Log.d(TAG, "goToMainActivity: called");
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    private void add(int currentID, Fragment fragment) {
        Log.d(TAG, "add: called");
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(currentID, fragment).commit();
    }

    private void replace(int currentID, Fragment fragment) {
        Log.d(TAG, "replace: called");
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(currentID, fragment).commit();
    }

    private View.OnClickListener editUserListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "editUserListener: called");
                Fragment f = new MyAccountEditorFragment();
                replace(R.id.fl_main_container, f);
            }
        };

    private View.OnClickListener deleteUserListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "deleteUserListener: called");
                deleteUserFromFirebaseFirestore();
            }
        };

    private View.OnClickListener logoutUserListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "logoutUserListener: called");
                logoutUser();
            }
        };
}