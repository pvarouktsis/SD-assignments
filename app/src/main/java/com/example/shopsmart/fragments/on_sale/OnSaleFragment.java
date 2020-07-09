package com.example.shopsmart.fragments.on_sale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopsmart.R;

public class OnSaleFragment extends Fragment {

    private OnSaleViewModel onSaleViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View on_sale_fragment = inflater.inflate(R.layout.fragment_on_sale, container, false);

        return on_sale_fragment;
    }
}