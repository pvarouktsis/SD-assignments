package com.example.shopsmart.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

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

public class HomeFragment extends Fragment {
    protected static final String TAG = "HOME_F";
    protected ArrayList<Product> products = new ArrayList<>();
    protected String sSearch;
    protected EditText etSearch;
    protected ImageButton ibtnSearch;
    protected FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        initializeUIComponents(homeView);

        return homeView;
    }

    protected void initializeUIComponents(View homeView) {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize components
        etSearch = homeView.findViewById(R.id.et_search);
        ibtnSearch = homeView.findViewById(R.id.ibtn_search);

        // on click
        ibtnSearch.setOnClickListener(searchListener);
    }

    protected void searchProducts() {
        Log.d(TAG, "searchProducts: called");

        // TODO
        // full-text search

        ffdb.collection("products")
            .whereEqualTo("name", sSearch)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "searchProducts: succeeded");
                        for (QueryDocumentSnapshot d : task.getResult()) {
                            addProduct(d);
                        }
                        showProducts();
                    } else {
                        Log.w(TAG, "searchProducts: failed", task.getException());
                        dismissLoading();
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

        // dismiss loading
        dismissLoading();

        // replace fragment
        replace(R.id.fl_main_container, f);
    }

    protected void convertEditTextToString() {
        Log.d(TAG, "convertEditTextToString: called");
        sSearch = etSearch.getText().toString().trim();
    }

    protected View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View homeView) {
            Log.d(TAG, "searchListener: called");

            // show loading
            showLoading();

            // process
            convertEditTextToString();
            searchProducts();
        }
    };

}