package com.example.shopsmart.fragments.on_sale;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopsmart.R;
import com.example.shopsmart.classes.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OnSaleFragment extends Fragment {
    private static final String TAG = "TAG_ON_SALE";
    private List<Product> products = new ArrayList<>();
    private FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // initialize ui
        View onSaleView = inflater.inflate(R.layout.fragment_on_sale, container, false);

        // read and show products
        readProducts(onSaleView);

        return onSaleView;
    }

    private void readProducts(final View onSaleView) {
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
                            showProducts(onSaleView);
                            Log.d(TAG, "data retrieved");
                        } else {
                            Log.w(TAG, "error retrieving data", task.getException());
                        }
                    }
                });
    }

    private void showProducts(final View onSaleView) {
        ListView productList = onSaleView.findViewById(R.id.product_list);
        ProductListAdapter pla = new ProductListAdapter(getActivity(), R.layout.frame_product, products);
        productList.setAdapter(pla);
    }

    private void addProduct(DocumentSnapshot d) {
        Product p = new Product();
        p.setProductID(d.getId());
        p.setProductName(d.getString("productName"));
        p.setProductPrice(d.getDouble("productPrice"));
        p.setProductImageURL(d.getString("productImageURL"));
        products.add(p);
    }

}