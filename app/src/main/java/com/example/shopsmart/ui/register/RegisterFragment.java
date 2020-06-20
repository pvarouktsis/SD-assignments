package com.example.shopsmart.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopsmart.R;
import com.example.shopsmart.ui.login.*;

public class RegisterFragment extends Fragment {
    private String TAG = "TAG_REGISTER";

    private EditText inputUsername;
    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnRegister;
    private Button btnLogin;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View register_view = inflater.inflate(R.layout.fragment_register, container, false);
        // EditText variables here

        // Register button action
        btnRegister = register_view.findViewById(R.id.button_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        // Login button action
        btnLogin = register_view.findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, new LoginFragment())
                        .commit();
            }
        });

        return register_view;
    }


}