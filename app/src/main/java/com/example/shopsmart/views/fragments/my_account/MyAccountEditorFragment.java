package com.example.shopsmart.views.fragments.my_account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopsmart.R;
import com.example.shopsmart.models.User;
import com.example.shopsmart.views.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyAccountEditorFragment extends Fragment {
    private static final String TAG = "MY_ACCOUNT_EDITOR_F";
    private static final String NONE = "";
    private User user;
    private String sUsername;
    private String sEmail;
    private String sPassword;
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnUpdate;
    private FirebaseAuth fa;
    private FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View myAccountEditorView = inflater.inflate(R.layout.fragment_my_account_editor, container, false);
        initializeUIComponents(myAccountEditorView);

        // initialize firebase
        fa = FirebaseAuth.getInstance();

        return myAccountEditorView;
    }

    private void initializeUIComponents(View myAccountEditorView) {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize components
        etUsername = myAccountEditorView.findViewById(R.id.et_username);
        etEmail = myAccountEditorView.findViewById(R.id.et_email);
        etPassword = myAccountEditorView.findViewById(R.id.et_password);
        btnUpdate = myAccountEditorView.findViewById(R.id.btn_update);

        // on click
        btnUpdate.setOnClickListener(updateListener);
    }

    // TODO

    private void getUser() {
        Log.d(TAG, "getUser: called");
        FirebaseUser fu = fa.getCurrentUser();
        ffdb.collection("users")
            .document(fu.getUid())
            .get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "getUser: succeeded");
                        DocumentSnapshot d = task.getResult();
                        user = d.toObject(User.class);
                        updateUser();
                    } else {
                        Log.w(TAG, "getUser: failed", task.getException());
                    }
                }
            });
    }

    private void updateUser() {
        Log.d(TAG, "updateUser: called");
        if (!sUsername.isEmpty()) {
            user.setUsername(sUsername);
        }
        updateUserEmailFromFirebaseAuthentication();
    }

    private void updateUserEmailFromFirebaseAuthentication() {
        Log.d(TAG, "updateUserEmailFromFirebaseAuthentication: called");
        FirebaseUser fu = fa.getCurrentUser();
        if (!sEmail.isEmpty()) {
            fu.updateEmail(sEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "updateEmail: succeeded");
                            user.setEmail(sEmail);
                        } else {
                            Log.w(TAG, "updateEmail: failed", task.getException());
                            Toast.makeText(getContext(), "update account failed", Toast.LENGTH_SHORT).show();
                        }
                        updateUserPasswordFromFirebaseAuthentication();
                    }
                });
        } else {
            updateUserPasswordFromFirebaseAuthentication();
        }
    }

    private void updateUserPasswordFromFirebaseAuthentication() {
        Log.d(TAG, "updateUserPasswordFromFirebaseAuthentication: called");
        FirebaseUser fu = fa.getCurrentUser();
        if (!sPassword.isEmpty()) {
            fu.updatePassword(sPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "updatePassword: succeeded");
                            user.setPassword(sPassword);
                        } else {
                            Log.w(TAG, "updatePassword: failed", task.getException());
                            Toast.makeText(getContext(), "update account failed", Toast.LENGTH_SHORT).show();
                        }
                        writeUser();
                    }
                });
        } else {
            writeUser();
        }
    }

    private void writeUser() {
        Log.d(TAG, "writeUser: called");
        FirebaseUser fu = fa.getCurrentUser();
        ffdb.collection("users")
            .document(fu.getUid())
            .set(user)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "writeUser: succeeded");
                        updateUI();
                    } else {
                        Log.w(TAG, "writeUser: failed", task.getException());
                        Toast.makeText(getContext(), "update account failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void updateUI() {
        Log.d(TAG, "updateUI: called");
        FirebaseUser fu = fa.getCurrentUser();
        if (fu != null) {
            Toast.makeText(getContext(), "updated account successfully", Toast.LENGTH_SHORT).show();
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        Log.d(TAG, "goToMainActivity: called");
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    private void convertEditTextToString() {
        sUsername = etUsername.getText().toString().trim();
        sEmail = etEmail.getText().toString().trim();
        sPassword = etPassword.getText().toString().trim();
    }

    private View.OnClickListener updateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "updateListener: called");
            convertEditTextToString();
            getUser();
        }
    };

}
