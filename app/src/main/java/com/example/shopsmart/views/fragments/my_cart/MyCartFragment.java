package com.example.shopsmart.views.fragments.my_cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.R;
import com.example.shopsmart.models.User;
import com.example.shopsmart.utils.VerticalSpaceItemDecoration;
import com.example.shopsmart.views.adapters.product_list.ProductListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyCartFragment extends Fragment {
    private static final String TAG = "MY_CART";
    private static final int VERTICAL_SPACE = 20;
    private User user;
    private RelativeLayout rlProductList;
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
        rlProductList = myCartView.findViewById(R.id.layout_product_list);
        tvTitle = myCartView.findViewById(R.id.title_fragment);
        btnFilter = myCartView.findViewById(R.id.button_filter);
        tvNoProducts = myCartView.findViewById(R.id.tv_no_products);
        rvProductList = myCartView.findViewById(R.id.product_list);

        // initialize visibility
        tvNoProducts.setVisibility(View.GONE);

        // initialize title
        tvTitle.setText(R.string.title_my_cart);
    }

    private void getUser() {
        Log.d(TAG, "getUser: called");
        FirebaseUser fu = fa.getCurrentUser();
        ffdb.collection("users")
            .document(fu.getUid())
            .get()
            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Log.d(TAG, "getUserTask: succeeded");
                    user = documentSnapshot.toObject(User.class);
                    showCart();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "getUserTask: failed");
                }
            });
    }

    private void showCart() {
        Log.d(TAG, "showCart: called");
        if (user.getCart().isEmpty()) {
            tvNoProducts.setVisibility(View.VISIBLE);
        } else {
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            rvProductList.setLayoutManager(llm);
            VerticalSpaceItemDecoration vsid = new VerticalSpaceItemDecoration(VERTICAL_SPACE);
            rvProductList.addItemDecoration(vsid);
            ProductListAdapter plrv = new ProductListAdapter(getContext(), user.getCart(), TAG);
            rvProductList.setAdapter(plrv);
        }
    }
}