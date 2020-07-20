package com.example.shopsmart.views.fragments.my_order;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyOrderViewModel extends ViewModel {
    private static final String TAG = "MY_ORDER_VIEW_MODEL";
    private MutableLiveData<String> mText;

    public MyOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is \"my order\" fragment");
    }

    public LiveData<String> getText() {
        Log.d(TAG, "getText: called");
        return mText;
    }
}