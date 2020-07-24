package com.example.shopsmart.views.adapters;

import android.app.Activity;
import android.util.Log;

import com.example.shopsmart.models.Product;

import java.util.ArrayList;

public class ProductListAdapterFactory {
    protected static final String TAG = "PRODUCT_LIST_AF";

    public ProductListAdapterFactory() {
        // Required
    }

    public ProductListAdapter initializeProductListAdapter(Activity activity, ArrayList<Product> products, String tag) {
        Log.d(TAG, "initializeProductListAdapter: called");
        ProductListAdapter pla;
        if (tag.equals("HOME_F")) {
            pla = new HomeProductListAdapter(activity, products);
        } else if (tag.equals("MY_CART_F")) {
            pla = new MyCartProductListAdapter(activity, products);
        } else if (tag.equals("ON_SALE_F")) {
            pla = new OnSaleProductListAdapter(activity, products);
        } else {
            pla = null;
        }
        return pla;
    }

}
