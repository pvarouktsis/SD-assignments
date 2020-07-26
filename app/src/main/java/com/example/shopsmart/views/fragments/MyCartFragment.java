package com.example.shopsmart.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.shopsmart.R;
import com.example.shopsmart.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyCartFragment extends Fragment {
    protected static final String TAG = "MY_CART_F";
    protected User user;
    protected FirebaseAuth fa;
    protected FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View myCartView = inflater.inflate(R.layout.fragment_my_cart, container, false);
        initializeUIComponents(myCartView);

        // initialize firebase
        fa = FirebaseAuth.getInstance();

        // show loading
        showLoading();

        // read user
        getUser();

        return myCartView;
    }

    protected void initializeUIComponents(View myCartView) {
        Log.d(TAG, "initializeUIComponents: called");
        // do nothing
    }

    protected void getUser() {
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
                        showCart();
                    } else {
                        Log.w(TAG, "getUser: failed", task.getException());
                        user = null;
                        dismissLoading();
                        showToast("Loading cart failed");
                    }
                }
            });
    }

    protected void showCart() {
        Log.d(TAG, "showCart: called");

        // initialize ProductListFragment
        Fragment f = new ProductListFragment();

        // pass products
        Bundle b = new Bundle();
        b.putString("tag", TAG);
        b.putSerializable("products", user.getCart());
        f.setArguments(b);

        // dismiss loading
        dismissLoading();

        // replace fragment
        replace(R.id.fl_main_container, f);
    }

}