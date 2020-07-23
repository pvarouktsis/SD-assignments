package com.example.shopsmart.views.fragments.my_cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.R;
import com.example.shopsmart.models.User;
import com.example.shopsmart.utils.VerticalSpaceItemDecoration;
import com.example.shopsmart.views.adapters.product_list.ProductListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyCartFragment extends Fragment {
    private static final String TAG = "MY_CART";
    private static final int VERTICAL_SPACE = 20;
    private User user;
    private RelativeLayout rlMyCart;
    private ScrollView svProductListContainer;
    private RelativeLayout rlProductListContainer;
    private TextView tvTitle;
    private Button btnFilter;
    private TextView tvNoProducts;
    private RecyclerView rvProductList;
    private FirebaseAuth fa;
    private FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View myCartView = inflater.inflate(R.layout.fragment_my_cart, container, false);
        initializeUIComponents(myCartView);

        // initialize firebase
        fa = FirebaseAuth.getInstance();

        // initialize user
        getUser();

        return myCartView;
    }

    private void initializeUIComponents(View myCartView) {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize components
        rlMyCart = myCartView.findViewById(R.id.rl_my_cart_container);
        svProductListContainer = myCartView.findViewById(R.id.sv_product_list_container);
        rlProductListContainer = myCartView.findViewById(R.id.rl_product_list_container);
        tvTitle = myCartView.findViewById(R.id.tv_title);
        btnFilter = myCartView.findViewById(R.id.btn_filter);
        tvNoProducts = myCartView.findViewById(R.id.tv_no_products);
        rvProductList = myCartView.findViewById(R.id.rv_product_list);

        // initialize visibility
        svProductListContainer.setVisibility(View.VISIBLE);
        tvNoProducts.setVisibility(View.GONE);
        rvProductList.setVisibility(View.VISIBLE);

        // initialize title
        tvTitle.setText(R.string.text_my_cart);
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
                        showCart();
                    } else {
                        Log.w(TAG, "getUser: failed", task.getException());
                        Toast.makeText(getContext(), "loading cart failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void showCart() {
        Log.d(TAG, "showCart: called");

        // check if any products
        if (user.getCart().isEmpty()) {
            tvNoProducts.setVisibility(View.VISIBLE);
            rvProductList.setVisibility(View.GONE);
        } else {
            tvNoProducts.setVisibility(View.GONE);
            rvProductList.setVisibility(View.VISIBLE);
            setProductListAdapter();
        }
    }

    private void setProductListAdapter() {
        Log.d(TAG, "setProductListAdapter: called");
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rvProductList.setLayoutManager(llm);
        VerticalSpaceItemDecoration vsid = new VerticalSpaceItemDecoration(VERTICAL_SPACE);
        rvProductList.addItemDecoration(vsid);
        ProductListAdapter plrv = new ProductListAdapter(getContext(), user.getCart(), TAG);
        rvProductList.setAdapter(plrv);
    }
}