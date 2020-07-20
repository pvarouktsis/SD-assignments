package com.example.shopsmart.views.adapters.product_list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.R;
import com.example.shopsmart.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private static final String TAG = "PRODUCT_LIST_ADAPTER";
    private Context context;
    private ArrayList<Product> products = new ArrayList<>();
    private int expandedPosition = -1;
    private int previousExpandedPosition = -1;

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
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        // initialize view of product
        Picasso.get().load(products.get(position).getImageURL()).into(holder.getIVProductImageURL()); // printing image
        holder.getTVProductName().setText(products.get(position).getName());
        holder.getTVProductPrice().setText(products.get(position).getPriceToStringWithEuroSymbol());

        // expand view of product
        final boolean isExpanded = (expandedPosition == position);
        holder.getRLProductExpanded().setVisibility(isExpanded ? View.VISIBLE : View.GONE); // expand product based on current state
        holder.getBtnExtend().setVisibility(!isExpanded ? View.VISIBLE : View.GONE); // remove extend_button based on current state
        holder.getRLProduct().setActivated(isExpanded);

        if (isExpanded) {
            previousExpandedPosition = position;
        }

        holder.getRLProduct().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return products.size();
    }
}
