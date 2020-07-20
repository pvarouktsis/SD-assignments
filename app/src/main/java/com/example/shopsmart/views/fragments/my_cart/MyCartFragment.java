package com.example.shopsmart.views.fragments.my_cart;

import android.os.Bundle;
import android.util.Log;
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

public class MyCartFragment extends Fragment {
    private static final String TAG = "MY_ORDER";
    private MyCartViewModel myCartViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        myCartViewModel =
            ViewModelProviders.of(this).get(MyCartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_cart, container, false);
        final TextView textView = root.findViewById(R.id.title_my_order);
        myCartViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}