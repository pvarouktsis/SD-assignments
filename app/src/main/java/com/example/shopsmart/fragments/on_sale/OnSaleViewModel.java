package com.example.shopsmart.fragments.on_sale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OnSaleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OnSaleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is \"on sale\" fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}