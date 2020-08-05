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

public abstract class AbstractActivity extends AppCompatActivity {
    protected static final String TAG = "ACTIVITY";
    protected ProgressDialog progressDialog;

    protected abstract void initializeUIComponents();

    protected void goToMainActivity(AbstractActivity aa) {
        Log.d(TAG, "goToMainActivity: called");
        startActivity(new Intent(aa, MainActivity.class));
        finish();
    }

    protected void goToLoginActivity(AbstractActivity aa) {
        Log.d(TAG, "goToLoginActivity: called");
        startActivity(new Intent(aa, LoginActivity.class));
        finish();
    }

    protected void goToRegisterActivity(AbstractActivity aa) {
        Log.d(TAG, "goToRegisterActivity: called");
        startActivity(new Intent(aa, RegisterActivity.class));
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

    protected void showLoading(AbstractActivity aa) {
        Log.d(TAG, "showLoading: called");
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(aa);
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

    protected void showToast(AbstractActivity aa, String message) {
        Log.d(TAG, "showToast: called");
        if (message.length() < 20) {
            Toast toast = Toast.makeText(aa, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
        } else {
            Toast toast = Toast.makeText(aa, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
        }
    }

}
