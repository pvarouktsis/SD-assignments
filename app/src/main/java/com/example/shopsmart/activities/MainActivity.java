package com.example.shopsmart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopsmart.R;
import com.example.shopsmart.fragments.home.HomeFragment;
import com.example.shopsmart.fragments.my_account.MyAccountFragment;
import com.example.shopsmart.fragments.my_order.MyOrderFragment;
import com.example.shopsmart.fragments.on_sale.OnSaleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN_ACTIVITY";
    private FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        // initialize ui
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replace(R.id.main_frame_container, new HomeFragment());
        // initialize bottom navigation bar
        BottomNavigationView bnv = findViewById(R.id.navigation_container);
        bnv.setOnNavigationItemSelectedListener(navigationListener);
        // initialize Firebase
        fa = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: called");
        super.onStart();
        FirebaseUser fu = fa.getCurrentUser();
        updateUI(fu);
    }

    private void updateUI(FirebaseUser fu) {
        Log.d(TAG, "updateUI: called");
        if (fu == null) {
            goToLoginActivity();
        }
    }

    private void goToLoginActivity() {
        Log.d(TAG, "goToLoginActivity: called");
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void add(int currentID, Fragment fragment) {
        Log.d(TAG, "add: called");
        Fragment f = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(currentID, fragment).commit();
    }

    private void replace(int currentID, Fragment fragment) {
        Log.d(TAG, "replace: called");
        Fragment f = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(currentID, fragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                Fragment f = null;
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Log.d(TAG, "navigationListener: called");
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            f = new HomeFragment();
                            break;
                        case R.id.navigation_my_order:
                            f = new MyOrderFragment();
                            break;
                        case R.id.navigation_on_sale:
                            f = new OnSaleFragment();
                            break;
                        case R.id.navigation_my_account:
                            f = new MyAccountFragment();
                            break;
                        default:
                            return false;
                    }
                    replace(R.id.main_frame_container, f);
                    return true;
                }
            };

}