package com.example.shopsmart.fragments.on_sale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.shopsmart.R;

public class OnSaleFragment extends Fragment {

    private OnSaleViewModel onSaleViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        onSaleViewModel =
                ViewModelProviders.of(this).get(OnSaleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_on_sale, container, false);
        final TextView textView = root.findViewById(R.id.text_on_sale);
        onSaleViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}