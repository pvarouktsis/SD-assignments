package com.example.shopsmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "TAG_LOGIN";

    private EditText inputUsername;
    private EditText inputPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeUIComponents();
    }

    void initializeUIComponents() {
        // TODO
        // et variables
        btnLogin = findViewById(R.id.button_login);
        btnRegister = findViewById(R.id.button_register);
        // on click
        btnLogin.setOnClickListener(loginListener);
        btnRegister.setOnClickListener(registerListener);
    }

    private void goToRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    /*
     * Login's button OnClickListener
     */
    private View.OnClickListener loginListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO
                }
            };

    /*
     * Register's button OnClickListener
     */
    private View.OnClickListener registerListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToRegisterActivity();
                }
            };

}