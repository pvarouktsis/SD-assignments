package com.example.shopsmart.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.shopsmart.R;
import com.example.shopsmart.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OnSaleFragment extends Fragment {
    protected static final String TAG = "ON_SALE_F";
    protected ArrayList<Product> products = new ArrayList<>();
    protected FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View onSaleView = inflater.inflate(R.layout.fragment_on_sale, container, false);
        initializeUIComponents(onSaleView);

        // read and show products
        readProducts();

        return onSaleView;
    }

    protected void initializeUIComponents(View onSaleView) {
        Log.d(TAG, "initializeUIComponents: called");
        // do nothing
    }

    protected void readProducts() {
        Log.d(TAG, "readProducts: called");
        ffdb.collection("products")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "readProducts: succeeded");
                        for (QueryDocumentSnapshot d : task.getResult()) {
                            addProduct(d);
                        }
                        showProducts();
                    } else {
                        Log.w(TAG, "readProducts: failed", task.getException());
                        showToast("Loading products failed");
                    }
                }
            });
    }

    protected void addProduct(DocumentSnapshot d) {
        Log.d(TAG, "addProduct: called");
        Product p = new Product();
        p.setId(d.getId());
        p.setName(d.getString("name"));
        p.setPrice(d.getDouble("price"));
        p.setImageURL(d.getString("image_url"));
        products.add(p);
    }

    protected void showProducts() {
        Log.d(TAG, "showProducts: called");

        // initialize ProductListFragment
        Fragment f = new ProductListFragment();

        // pass products
        Bundle b = new Bundle();
        b.putString("tag", TAG);
        b.putSerializable("products", products);
        f.setArguments(b);

        // replace fragment
        replace(R.id.fl_main_container, f);
    }

}