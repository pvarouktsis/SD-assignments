package com.example.shopsmart.views.adapters.product_list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.R;
import com.example.shopsmart.models.Product;
import com.example.shopsmart.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private static final String TAG = "PRODUCT_LIST_ADAPTER";
    private User user;
    private Context context;
    private ArrayList<Product> products;
    private String tag;
    private int expandedPosition = -1;
    private int previousExpandedPosition = -1;
    private FirebaseAuth fa;
    private FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public ProductListAdapter(Context context, ArrayList<Product> products, String tag) {
        this.context = context;
        this.products = products;
        this.tag = tag;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");

        // initialize ui
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_product, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(view);

        // initialize firebase
        fa = FirebaseAuth.getInstance();

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
        holder.getRLProductExpanded().setVisibility(isExpanded ? View.VISIBLE : View.GONE); // expand or contract product based on current state
        holder.getBtnExtend().setVisibility(!isExpanded ? View.VISIBLE : View.GONE); // remove extend_button based on current state

        if (tag.equals("MY_CART_F")) {
            holder.getBtnAdd().setVisibility(View.GONE);
            holder.getBtnRemove().setVisibility(View.VISIBLE);
        } else {
            holder.getBtnRemove().setVisibility(View.GONE);
            holder.getBtnAdd().setVisibility(View.VISIBLE);
        }

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

        // get user
        getUser();

        // add product
        holder.getBtnAdd().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToCart(products.get(position));
            }
        });

        // remove product
        holder.getBtnRemove().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeProductFromCart(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return products.size();
    }

    private void getUser() {
        Log.d(TAG, "getUser: called");
        FirebaseUser fu = fa.getCurrentUser();
        ffdb.collection("users")
            .document(fu.getUid())
            .get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "getUser: succeeded");
                        DocumentSnapshot d = task.getResult();
                        user = d.toObject(User.class);
                    } else {
                        Log.w(TAG, "getUser: failed", task.getException());
                    }
                }
            });
    }

    private void addProductToCart(Product product) {
        Log.d(TAG, "addProductToCart: called");

        // add product to user's cart
        FirebaseUser fu = fa.getCurrentUser();
        user.getCart().add(product);
        ffdb.collection("users")
            .document(fu.getUid())
            .set(user, SetOptions.merge());

        // toast for product added
        Toast.makeText(context, "product added", Toast.LENGTH_SHORT).show();
    }

    private void removeProductFromCart(int position) {
        Log.d(TAG, "removeProductFromCart: called");

        // remove product from user's cart
        FirebaseUser fu = fa.getCurrentUser();
        user.getCart().remove(position);
        ffdb.collection("users")
            .document(fu.getUid())
            .set(user, SetOptions.merge());

        // toast for product removed
        Toast.makeText(context, "product removed", Toast.LENGTH_SHORT).show();

        // refresh products of user's cart
        refreshCart();
    }

    private void refreshCart() {
        Log.d(TAG, "refreshCart: called");
        products = user.getCart();
        notifyDataSetChanged();
    }

}
