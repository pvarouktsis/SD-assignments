package com.example.shopsmart.views.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopsmart.R;
import com.example.shopsmart.views.fragments.HomeFragment;
import com.example.shopsmart.views.fragments.MyAccountFragment;
import com.example.shopsmart.views.fragments.MyCartFragment;
import com.example.shopsmart.views.fragments.OnSaleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {
    protected static final String TAG = "MAIN_A";
    protected FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");

        // initialize ui
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replace(R.id.fl_main_container, new HomeFragment());
        initializeUIComponents();

        // initialize firebase
        fa = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: called");
        super.onStart();
        FirebaseUser fu = fa.getCurrentUser();
        updateUI(fu);
    }

    protected void initializeUIComponents() {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize bottom navigation bar
        BottomNavigationView bnv = findViewById(R.id.bottom_navbar_view);
        bnv.setOnNavigationItemSelectedListener(navigationListener);
    }

    protected void updateUI(FirebaseUser fu) {
        Log.d(TAG, "updateUI: called");
        if (fu == null) {
            goToLoginActivity(MainActivity.this);
        }
    }

    protected BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
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
                        f = new MyCartFragment();
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
                replace(R.id.fl_main_container, f);
                return true;
            }
        };

}