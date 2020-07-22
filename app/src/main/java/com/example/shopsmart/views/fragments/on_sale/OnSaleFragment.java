package com.example.shopsmart.views.fragments.on_sale;

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
import com.example.shopsmart.models.Product;
import com.example.shopsmart.utils.VerticalSpaceItemDecoration;
import com.example.shopsmart.views.adapters.product_list.ProductListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OnSaleFragment extends Fragment {
    private static final String TAG = "ON_SALE_FRAGMENT";
    private static final int VERTICAL_SPACE = 20;
    private ArrayList<Product> products = new ArrayList<>();
    private RelativeLayout rlOnSale;
    private ScrollView svProductListContainer;
    private RelativeLayout rlProductListContainer;
    private TextView tvTitle;
    private Button btnFilter;
    private TextView tvNoProducts;
    private RecyclerView rvProductList;
    private FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View onSaleView = inflater.inflate(R.layout.fragment_on_sale, container, false);
        initializeUIComponents(onSaleView);

        // read and show products
        readProducts(onSaleView);

        return onSaleView;
    }

    private void initializeUIComponents(View onSaleView) {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize components
        rlOnSale = onSaleView.findViewById(R.id.rl_on_sale_container);
        svProductListContainer = onSaleView.findViewById(R.id.sv_product_list_container);
        rlProductListContainer = onSaleView.findViewById(R.id.rl_product_list_container);
        tvTitle = onSaleView.findViewById(R.id.tv_title);
        btnFilter = onSaleView.findViewById(R.id.btn_filter);
        tvNoProducts = onSaleView.findViewById(R.id.tv_no_products);
        rvProductList = onSaleView.findViewById(R.id.rv_product_list);

        //initialize visibility
        svProductListContainer.setVisibility(View.VISIBLE);
        tvNoProducts.setVisibility(View.GONE);
        rvProductList.setVisibility(View.VISIBLE);

        //initialize title
        tvTitle.setText(R.string.title_on_sale);
    }

    private void readProducts(final View onSaleView) {
        Log.d(TAG, "readProducts: called");
        ffdb.collection("products")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "readProducts: succeeded");
                        for (QueryDocumentSnapshot d : task.getResult()) {
                            if (!d.getId().equals("prototype")) { // if it is NOT the prototype document
                                addProduct(d);
                            }
                        }
                        showProducts(onSaleView);
                    } else {
                        Log.w(TAG, "readProducts: failed", task.getException());
                        Toast.makeText(getContext(), "loading products failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void addProduct(DocumentSnapshot d) {
        Log.d(TAG, "addProduct: called");
        Product p = new Product();
        p.setId(d.getId());
        p.setName(d.getString("name"));
        p.setPrice(d.getDouble("price"));
        p.setImageURL(d.getString("image_url"));
        products.add(p);
    }

    private void showProducts(View homeView) {
        Log.d(TAG, "showProducts: called");

        // check if any products
        if (products.isEmpty()) {
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
        ProductListAdapter plrv = new ProductListAdapter(getContext(), products, TAG);
        rvProductList.setAdapter(plrv);
    }

}