package com.example.shopsmart.fragments.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopsmart.R;

public class HomeFragment extends Fragment {
    @SuppressLint("WrongViewCast")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View home_view = inflater.inflate(R.layout.fragment_home, container, false);

        // TODO
        // ImageButton

        return home_view;
    }

}