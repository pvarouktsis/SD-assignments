package com.example.shopsmart.fragments.on_sale;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.R;
import com.example.shopsmart.classes.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OnSaleFragment extends Fragment {
    private static final String TAG = "ON_SALE_FRAGMENT";
    private static final int VERTICAL_SPACE = 20;
    private ArrayList<Product> products = new ArrayList<>();
    private FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View onSaleView = inflater.inflate(R.layout.fragment_on_sale, container, false);

        // read and show products
        readProducts(onSaleView);

        return onSaleView;
    }

    private void readProducts(final View onSaleView) {
        Log.d(TAG, "readProducts: called");
        ffdb.collection("products")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot d : task.getResult()) {
                            if (!d.getId().equals("prototype")) { // if it is NOT the prototype document
                                addProduct(d);
                            }
                        }
                        Log.d(TAG, "retrieveProductsFromFirestore: succeeded");
                        showProducts(onSaleView);
                    } else {
                        Log.w(TAG, "retrieveProductsFromFirestore: failed", task.getException());
                    }
                }
            });
    }

    private void showProducts(final View onSaleView) {
        Log.d(TAG, "showProducts: called");
        RecyclerView rv = onSaleView.findViewById(R.id.product_list);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        VerticalSpaceItemDecoration vsid = new VerticalSpaceItemDecoration(VERTICAL_SPACE);
        rv.addItemDecoration(vsid);
        ProductListAdapter plrv = new ProductListAdapter(getContext(), products);
        rv.setAdapter(plrv);
    }

    private void addProduct(DocumentSnapshot d) {
        Log.d(TAG, "addProduct: called");
        Product p = new Product();
        p.setProductID(d.getId());
        p.setProductName(d.getString("productName"));
        p.setProductPrice(d.getDouble("productPrice"));
        p.setProductImageURL(d.getString("productImageURL"));
        products.add(p);
    }

}