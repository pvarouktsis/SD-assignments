package com.example.shopsmart.ui.login;

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
import com.example.shopsmart.ui.register.*;

public class LoginFragment extends Fragment {
    private String TAG = "TAG_LOGIN";

    private EditText inputUsername;
    private EditText inputPassword;
    private Button btnLogin;
    private Button btnRegister;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View login_view = inflater.inflate(R.layout.fragment_login, container, false);

        // Login button action
        btnLogin = login_view.findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        btnRegister = login_view.findViewById(R.id.button_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, new RegisterFragment())
                        .commit();
            }
        });


        return login_view;
    }


}