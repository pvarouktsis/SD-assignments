package com.example.shopsmart.fragments.on_sale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shopsmart.R;
import com.example.shopsmart.classes.Product;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {
    //private static final String TAG = "TAG_PRODUCT_LIST_ADAPTER";
    private Context plaContext;
    private int plaResource;

    public ProductListAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
        plaContext = context;
        plaResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) throws NullPointerException {
        String productName = getItem(position).getProductName();
        double productPrice = getItem(position).getProductPrice();
        String productImageURL = getItem(position).getProductImageURL();

        LayoutInflater inflater = LayoutInflater.from(plaContext);
        convertView = inflater.inflate(plaResource, parent, false);

        TextView tvProductName = convertView.findViewById(R.id.product_name);
        TextView tvProductPrice = convertView.findViewById(R.id.product_price);

        tvProductName.setText(productName);
        tvProductPrice.setText(Double.toString(productPrice));

        return convertView;
    }

}
