package com.example.shopsmart.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.R;
import com.example.shopsmart.models.Product;
import com.example.shopsmart.utils.VerticalSpaceItemDecoration;
import com.example.shopsmart.views.adapters.ProductListAdapter;
import com.example.shopsmart.views.adapters.ProductListAdapterFactory;

import java.util.ArrayList;

public class ProductListFragment extends Fragment {
    protected static final String TAG = "PRODUCT_LIST_F";
    protected static final int VERTICAL_SPACE = 20;
    protected ArrayList<Product> products;
    protected String tag;
    protected TextView tvNoProducts;
    protected RecyclerView rvProductList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View productListView = inflater.inflate(R.layout.fragment_product_list, container, false);
        initializeUIComponents(productListView);
        getTagFromBundle();
        getProducts();
        showProducts();

        return productListView;
    }

    protected void initializeUIComponents(View productListView) {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize components
        tvNoProducts = productListView.findViewById(R.id.tv_no_products);
        rvProductList = productListView.findViewById(R.id.rv_product_list);
    }

    protected void getTagFromBundle() {
        Log.d(TAG, "getTag: called");
        tag = getArguments().getString("tag");
    }

    protected void getProducts() {
        Log.d(TAG, "getProducts: called");
        products = (ArrayList<Product>) getArguments().getSerializable("products");
    }

    protected void showProducts() {
        Log.d(TAG, "showProducts: called");

        // check if any products
        if (products.isEmpty()) {
            tvNoProducts.setVisibility(View.VISIBLE);
            rvProductList.setVisibility(View.GONE);
        } else {
            rvProductList.setVisibility(View.VISIBLE);
            tvNoProducts.setVisibility(View.GONE);
            setProductListAdapter();
        }
    }

    protected void setProductListAdapter() {
        Log.d(TAG, "setProductListAdapter: called");

        // set ProductListAdapter
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rvProductList.setLayoutManager(llm);
        VerticalSpaceItemDecoration vsid = new VerticalSpaceItemDecoration(VERTICAL_SPACE);
        rvProductList.addItemDecoration(vsid);
        ProductListAdapterFactory plrvf = new ProductListAdapterFactory();
        ProductListAdapter plrv = plrvf.initializeProductListAdapter(getActivity(), products, tag);
        rvProductList.setAdapter(plrv);
    }

}
