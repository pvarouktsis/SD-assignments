package com.example.shopsmart.fragments.my_account;

import android.content.Intent;
import android.os.Bundle;
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

public class MyAccountFragment extends Fragment {
    //private static final String TAG = "TAG_MY_ACCOUNT_FRAGMENT";
    private Button btnLogout;
    private FirebaseAuth fa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // initialize ui
        View myAccountView = inflater.inflate(R.layout.fragment_my_account, container, false);
        initializeUIComponents(myAccountView);
        // initialize Firebase
        fa = FirebaseAuth.getInstance();

        return myAccountView;
    }

    private void initializeUIComponents(View v) {
        // initialize components
        btnLogout = v.findViewById(R.id.button_logout);
        // on click
        btnLogout.setOnClickListener(logoutListener);
    }

    private void logoutUser() {
        fa.signOut();
        Toast.makeText(getContext(), "logout successful", Toast.LENGTH_SHORT).show();
        updateUI();
    }

    private void updateUI() {
        goToMainActivity();
    }

    private void goToMainActivity() {
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    private View.OnClickListener logoutListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutUser();
                }
            };
}