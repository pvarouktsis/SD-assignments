package com.example.shopsmart.fragments.my_order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyOrderViewModel extends ViewModel {
    //private static final String TAG = "TAG_MY_ORDER_VIEW";
    private MutableLiveData<String> mText;

    public MyOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is \"my order\" fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}