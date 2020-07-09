package com.example.shopsmart.fragments.on_sale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopsmart.R;
import com.example.shopsmart.classes.Product;

import java.util.ArrayList;

public class OnSaleFragment extends Fragment {
    //private static final String TAG = "TAG_ON_SALE";
    private ArrayList<Product> products;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // initialize ui
        View on_sale_fragment = inflater.inflate(R.layout.fragment_on_sale, container, false);
        ListView productList = (ListView) on_sale_fragment.findViewById(R.id.product_list);

        // for testing purposes
        initializeProducts();
        ProductListAdapter pla = new ProductListAdapter(getActivity(), R.layout.frame_product, products);
        productList.setAdapter(pla);

        return on_sale_fragment;
    }

    // for testing purposes
    private void initializeProducts() {
        Product p1 = new Product("id", null, "product_name", 0.00);
        Product p2 = new Product("id", null, "product_name", 0.00);
        Product p3 = new Product("id", null, "product_name", 0.00);
        Product p4 = new Product("id", null, "product_name", 0.00);
        Product p5 = new Product("id", null, "product_name", 0.00);
        Product p6 = new Product("id", null, "product_name", 0.00);

        products = new ArrayList<Product>();
        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);
        products.add(p6);
    }

}