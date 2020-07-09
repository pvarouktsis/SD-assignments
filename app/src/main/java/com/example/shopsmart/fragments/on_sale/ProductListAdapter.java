package com.example.shopsmart.fragments.on_sale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shopsmart.R;
import com.example.shopsmart.classes.Product;

import java.util.ArrayList;

public class ProductListAdapter extends ArrayAdapter<Product> {
    //private static final String TAG = "TAG_PRODUCT_LIST_ADAPTER";
    private Context plaContext;
    private int plaResource;

    public ProductListAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
        plaContext = context;
        plaResource = resource;
    }

    // TODO
    // implement connection with Firebase Storage

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO
        String productID = getItem(position).getProductID();
        String productImageURL = getItem(position).getProductImageURL();
        String productName = getItem(position).getProductName();
        double productPrice = getItem(position).getProductPrice();

        Product p = new Product(productID, productImageURL, productName, productPrice);

        LayoutInflater inflater = LayoutInflater.from(plaContext);
        convertView = inflater.inflate(plaResource, parent, false);

        TextView tvProductName = (TextView) convertView.findViewById(R.id.product_name);
        TextView tvProductPrice = (TextView) convertView.findViewById(R.id.product_price);

        tvProductName.setText(productName);
        tvProductPrice.setText(Double.toString(productPrice));

        return convertView;
    }

}
