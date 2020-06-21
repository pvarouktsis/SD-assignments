package com.example.shopsmart.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopsmart.R;
import com.example.shopsmart.ui.register.RegisterFragment;

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
        btnRegister.setOnClickListener(registerListener);

        return login_view;
    }

    /*
     * Register's button OnClickListener
     */
    private View.OnClickListener registerListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_container, new RegisterFragment())
                            .commit();
                }
            };

}