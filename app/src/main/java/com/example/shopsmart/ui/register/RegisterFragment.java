package com.example.shopsmart.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopsmart.R;
import com.example.shopsmart.ui.login.LoginFragment;

public class RegisterFragment extends Fragment {
    private String TAG = "TAG_REGISTER";

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;
    private Button btnLogin;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View register_view = inflater.inflate(R.layout.fragment_register, container, false);
        etUsername = register_view.findViewById(R.id.input_username);
        etEmail = register_view.findViewById(R.id.input_email);
        etPassword = register_view.findViewById(R.id.input_password);

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
        btnLogin.setOnClickListener(loginListener);

        return register_view;
    }


    /*
     * Login's button OnClickListener
     */
    private View.OnClickListener loginListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_container, new LoginFragment())
                            .commit();
                }
    };

}