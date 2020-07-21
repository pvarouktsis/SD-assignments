package com.example.shopsmart.views.fragments.on_sale;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.R;
import com.example.shopsmart.models.Product;
import com.example.shopsmart.views.adapters.product_list.ProductListAdapter;
import com.example.shopsmart.utils.VerticalSpaceItemDecoration;
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
    private RelativeLayout rlProductList;
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
        rlProductList = onSaleView.findViewById(R.id.layout_product_list);
        rvProductList = onSaleView.findViewById(R.id.product_list);
    }

    private void readProducts(final View onSaleView) {
        Log.d(TAG, "readProducts: called");
        ffdb.collection("products")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot d : task.getResult()) {
                            if (!d.getId().equals("prototype")) { // if it is NOT the prototype document
                                addProduct(d);
                            }
                        }
                        Log.d(TAG, "retrieveProductsFromFirestore: succeeded");
                        showProducts(onSaleView);
                    } else {
                        Log.w(TAG, "retrieveProductsFromFirestore: failed", task.getException());
                    }
                }
            });
    }

    private void showProducts(View onSaleView) {
        Log.d(TAG, "showProducts: called");
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rvProductList.setLayoutManager(llm);
        VerticalSpaceItemDecoration vsid = new VerticalSpaceItemDecoration(VERTICAL_SPACE);
        rvProductList.addItemDecoration(vsid);
        ProductListAdapter plrv = new ProductListAdapter(getContext(), products, TAG);
        rvProductList.setAdapter(plrv);
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

}