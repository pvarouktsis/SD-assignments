package com.example.shopsmart.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.shopsmart.R;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        View login_view = inflater.inflate(R.layout.fragment_login, container, false);
        return login_view;
    }


}