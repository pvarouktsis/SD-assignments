package com.example.shopsmart.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopsmart.R;

public class HomeFragment extends Fragment {
    //private static final String TAG = "TAG_HOME_FRAGMENT";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // initialize ui
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);

        // TODO
        // ImageButton

        return homeView;
    }

}