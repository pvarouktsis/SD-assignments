package com.example.shopsmart.fragments.my_account;

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

import com.example.shopsmart.R;
import com.example.shopsmart.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyAccountFragment extends Fragment {
    private static final String TAG = "MY_ACCOUNT_FRAGMENT";
    private Button btnLogout;
    private FirebaseAuth fa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        // initialize ui
        View myAccountView = inflater.inflate(R.layout.fragment_my_account, container, false);
        initializeUIComponents(myAccountView);
        // initialize Firebase
        fa = FirebaseAuth.getInstance();

        return myAccountView;
    }

    private void initializeUIComponents(View v) {
        Log.d(TAG, "initializeUIComponents: called");
        // initialize components
        btnLogout = v.findViewById(R.id.button_logout);
        // on click
        btnLogout.setOnClickListener(logoutListener);
    }

    private void logoutUser() {
        Log.d(TAG, "logoutUser: called");
        fa.signOut();
        updateUI();
    }

    private void updateUI() {
        Log.d(TAG, "updateUI: called");
        FirebaseUser fu = fa.getCurrentUser();
        if (fu == null) {
            Log.d(TAG, "logoutUser: succeeded");
            Toast.makeText(getContext(), "sign out successful", Toast.LENGTH_SHORT).show();
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        Log.d(TAG, "goToMainActivity: called");
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    private View.OnClickListener logoutListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "logoutListener: called");
                    logoutUser();
                }
            };
}