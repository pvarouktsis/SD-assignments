package com.example.shopsmart.fragments.on_sale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.R;
import com.example.shopsmart.classes.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductListAdapter  extends RecyclerView.Adapter<ProductViewHolder> {
    private static final String TAG = "PRODUCT_LIST_ADAPTER";
    private Context context;
    private ArrayList<Product> products = new ArrayList<>();

    public ProductListAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_product, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Picasso.get().load(products.get(position).getProductImageURL()).into(holder.getIVProductImageURL()); // printing image
        holder.getTVProductName().setText(products.get(position).getProductName());
        holder.getTVProductPrice().setText(products.get(position).getProductPriceToString());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return products.size();
    }
}
