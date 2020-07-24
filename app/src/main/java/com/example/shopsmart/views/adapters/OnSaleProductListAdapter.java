package com.example.shopsmart.views.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shopsmart.R;
import com.example.shopsmart.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OnSaleProductListAdapter extends ProductListAdapter {
    private static final String TAG = "ON_SALE_PLA";

    public OnSaleProductListAdapter(Activity activity, ArrayList<Product> products) {
        super(activity, products);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");

        // initialize ui
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_product, parent, false);
        ProductViewHolder productListView = new ProductViewHolder(view);
        initializeUIComponents();

        // initialize visibility
        productListView.btnAdd.setVisibility(View.VISIBLE);

        // initialize firebase
        fa = FirebaseAuth.getInstance();

        // initialize user
        getUser();

        return productListView;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder pvh, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        // initialize view of product
        Picasso.get().load(products.get(position).getImageURL()).into(pvh.ivProductImage); // printing image
        pvh.tvProductName.setText(products.get(position).getName());
        pvh.tvProductPrice.setText(products.get(position).getPriceToStringWithEuroSymbol());

        // expand view of product
        final boolean isExpanded = (expandedPosition == position);
        pvh.rlProductExpanded.setVisibility(isExpanded ? View.VISIBLE : View.GONE); // expand or contract product based on current state
        pvh.btnExtend.setVisibility(!isExpanded ? View.VISIBLE : View.GONE); // remove extend_button based on current state
        pvh.btnAdd.setVisibility(View.VISIBLE);
        pvh.btnRemove.setVisibility(View.GONE);

        pvh.rlProduct.setActivated(isExpanded);

        if (isExpanded) {
            previousExpandedPosition = position;
        }

        pvh.rlProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });

        // add product
        pvh.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToCart(products.get(position));
            }
        });
    }

    protected void addProductToCart(Product product) {
        Log.d(TAG, "addProductToCart: called");

        // add product to user's cart
        FirebaseUser fu = fa.getCurrentUser();
        user.getCart().add(product);
        ffdb.collection("users")
            .document(fu.getUid())
            .set(user, SetOptions.merge());

        // toast for product added
        Toast.makeText(activity, "Product added", Toast.LENGTH_SHORT).show();
    }
}
