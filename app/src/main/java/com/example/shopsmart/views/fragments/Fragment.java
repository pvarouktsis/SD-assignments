package com.example.shopsmart.views.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopsmart.R;
import com.example.shopsmart.views.activities.MainActivity;

public abstract class Fragment extends androidx.fragment.app.Fragment {
    protected static final String TAG = "FRAGMENT";
    protected ProgressDialog progressDialog;

    protected abstract void initializeUIComponents(View view);

    protected void goToMainActivity() {
        Log.d(TAG, "goToMainActivity: called");
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    protected void replace(int cid, Fragment f) {
        Log.d(TAG, "replace: called");
        if (getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(cid, f).commit();
        }
    }

    protected void showLoading() {
        Log.d(TAG, "showLoading: called");
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.text_please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void dismissLoading() {
        Log.d(TAG, "dismissLoading: called");
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    protected void showToast(String message) {
        Log.d(TAG, "showToast: called");
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    protected void showToastLong(String message) {
        Log.d(TAG, "showToast: called");
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }
}
