package com.example.shopsmart.views.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopsmart.R;

public abstract class Activity extends AppCompatActivity {
    protected static final String TAG = "ACTIVITY";
    protected ProgressDialog progressDialog;

    protected abstract void initializeUIComponents();

    protected void goToMainActivity(Activity activity) {
        Log.d(TAG, "goToMainActivity: called");
        startActivity(new Intent(activity, MainActivity.class));
        finish();
    }

    protected void goToLoginActivity(Activity activity) {
        Log.d(TAG, "goToLoginActivity: called");
        startActivity(new Intent(activity, LoginActivity.class));
        finish();
    }

    protected void goToRegisterActivity(Activity activity) {
        Log.d(TAG, "goToRegisterActivity: called");
        startActivity(new Intent(activity, RegisterActivity.class));
        finish();
    }

    protected void add(int currentID, Fragment fragment) {
        Log.d(TAG, "replace: called");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(currentID, fragment).commit();
    }

    protected void replace(int currentID, Fragment fragment) {
        Log.d(TAG, "replace: called");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(currentID, fragment).commit();
    }

    protected void showLoading(Activity activity) {
        Log.d(TAG, "showLoading: called");
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
        }
        progressDialog.setMessage(getString(R.string.text_please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void dismissLoading() {
        Log.d(TAG, "dismissLoading: called");
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected void showToast(Activity activity, String message) {
        Log.d(TAG, "showToast: called");
        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    protected void showToastLong(Activity activity, String message) {
        Log.d(TAG, "showToastLong: called");
        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

}
