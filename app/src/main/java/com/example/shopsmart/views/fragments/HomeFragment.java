package com.example.shopsmart.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    private static final String TAG = "HOME_F";
    private ArrayList<Product> products = new ArrayList<>();
    private String sSearch;
    private EditText etSearch;
    private ImageButton ibtnSearch;
    private FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        initializeUIComponents(homeView);

        return homeView;
    }

    private void initializeUIComponents(View homeView) {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize components
        etSearch = homeView.findViewById(R.id.et_search);
        ibtnSearch = homeView.findViewById(R.id.ibtn_search);

        // on click
        ibtnSearch.setOnClickListener(searchListener);
    }

    private void searchProducts() {
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
                            if (!d.getId().equals("prototype")) { // if it is NOT the prototype document
                                addProduct(d);
                            }
                        }
                        showProducts();
                    } else {
                        Log.w(TAG, "searchProducts: failed", task.getException());
                        Toast.makeText(getActivity(), "loading products failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void addProduct(DocumentSnapshot d) {
        Log.d(TAG, "addProduct: called");
        Product p = new Product();
        p.setId(d.getId());
        p.setName(d.getString("name"));
        p.setPrice(d.getDouble("price"));
        p.setImageURL(d.getString("image_url"));
        products.add(p);
    }

    private void showProducts() {
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

    private void replace(int cid, Fragment f) {
        Log.d(TAG, "replace: called");
        if (getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(cid, f).commit();
        }
    }

    private void convertEditTextToString() {
        Log.d(TAG, "convertEditTextToString: called");
        sSearch = etSearch.getText().toString().trim();
    }

    private View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View homeView) {
            Log.d(TAG, "searchListener: called");
            convertEditTextToString();
            searchProducts();
        }
    };

}