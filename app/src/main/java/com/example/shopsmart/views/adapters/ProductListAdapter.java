package com.example.shopsmart.views.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    protected static final String TAG = "PRODUCT_LIST_A";
    protected User user;
    protected Activity activity;
    protected ArrayList<Product> products;
    protected TextView tvNoProducts;
    protected RecyclerView rvProductList;
    protected int expandedPosition = -1;
    protected int previousExpandedPosition = -1;
    protected FirebaseAuth fa;
    protected FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public ProductListAdapter(Activity activity, ArrayList<Product> products) {
        this.activity = activity;
        this.products = products;
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

        // initialize firebase
        fa = FirebaseAuth.getInstance();

        // initialize user
        getUser();

        return productListView;
    }

    protected void initializeUIComponents() {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize components
        tvNoProducts = activity.findViewById(R.id.tv_no_products);
        rvProductList = activity.findViewById(R.id.rv_product_list);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder pvh, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        // initialize view of product
        Picasso.get().load(products.get(position).getImageURL()).into(pvh.ivProductImage); // printing image
        pvh.tvProductName.setText(products.get(position).getName());
        pvh.tvProductPrice.setText(products.get(position).getPriceToStringWithEuroSymbol());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return products.size();
    }

    protected void getUser() {
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

    /*
     * ProductViewHolder class
     */
     protected class ProductViewHolder extends RecyclerView.ViewHolder {
        protected RelativeLayout rlProduct;
        protected RelativeLayout rlProductStandard;
        protected RelativeLayout rlProductExpanded;
        protected ImageView ivProductImage;
        protected TextView tvProductName;
        protected TextView tvProductPrice;
        protected TextView tvProductSupermarket;
        protected Button btnExtend;
        protected Button btnAdd;
        protected Button btnRemove;

        protected ProductViewHolder(View productView) {
            super(productView);
            rlProduct = productView.findViewById(R.id.rl_product);
            rlProductStandard = productView.findViewById(R.id.rl_product_standard);
            rlProductExpanded = productView.findViewById(R.id.rl_product_expanded);
            ivProductImage = productView.findViewById(R.id.iv_product_image);
            tvProductName = productView.findViewById(R.id.tv_product_name);
            tvProductPrice = productView.findViewById(R.id.tv_product_price);
            tvProductSupermarket = productView.findViewById(R.id.tv_product_supermarket);
            btnExtend = productView.findViewById(R.id.btn_extend);
            btnAdd = productView.findViewById(R.id.btn_add);
            btnRemove = productView.findViewById(R.id.btn_remove);
        }
    }
}
