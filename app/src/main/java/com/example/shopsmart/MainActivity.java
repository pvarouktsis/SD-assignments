package com.example.shopsmart;

import android.os.Bundle;
import android.view.MenuItem;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopsmart.ui.home.HomeFragment;
import com.example.shopsmart.ui.my_account.MyAccountFragment;
import com.example.shopsmart.ui.my_order.MyOrderFragment;
import com.example.shopsmart.ui.on_sale.OnSaleFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToLoginActivity();

        // TODO
        // if session is expired or there is no
        // session go to sign in activity

        BottomNavigationView bnv = findViewById(R.id.navigation_container);
        bnv.setOnNavigationItemSelectedListener(navigationListener);

    }

    private void goToLoginActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    private void add(int currentID, Fragment fragment) {
        Fragment f = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(currentID, fragment).commit();
    }

    private void replace(int currentID, Fragment fragment) {
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
                    replace(R.id.main_container, f);
                    return true;
                }
            };

}