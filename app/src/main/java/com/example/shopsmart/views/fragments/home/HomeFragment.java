package com.example.shopsmart.views.fragments.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.R;
import com.example.shopsmart.model.Product;
import com.example.shopsmart.views.adapters.product_list.ProductListAdapter;
import com.example.shopsmart.utils.VerticalSpaceItemDecoration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private static final String TAG = "HOME_FRAGMENT";
    private static final int VERTICAL_SPACE = 20;
    private ArrayList<Product> products = new ArrayList<>();
    private LinearLayout llMainContainer;
    private String sInputSearch;
    private EditText etInputSearch;
    private ImageButton btnSearch;
    private RecyclerView rvProductList;
    private FirebaseFirestore ffdb = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        // initialize ui
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        initializeUIComponents(homeView);

        return homeView;
    }

    private void initializeUIComponents(View homeView) {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize components
        etInputSearch = homeView.findViewById(R.id.input_search);
        btnSearch = homeView.findViewById(R.id.button_search);
        llMainContainer = homeView.findViewById(R.id.layout_main_container);
        rvProductList = homeView.findViewById(R.id.product_list);

        // on click
        btnSearch.setOnClickListener(searchListener);
    }

    private void searchProducts(final View homeView) {
        Log.d(TAG, "searchProducts: called");

        // TODO
        // full-text search

        ffdb.collection("products")
            .whereEqualTo("name", sInputSearch)
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
                        showProducts(homeView);
                    } else {
                        Log.w(TAG, "retrieveProductsFromFirestore: failed", task.getException());
                    }
                }
            });
    }

    private void showProducts(View homeView) {
        Log.d(TAG, "showProducts: called");
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rvProductList.setLayoutManager(llm);
        VerticalSpaceItemDecoration vsid = new VerticalSpaceItemDecoration(VERTICAL_SPACE);
        rvProductList.addItemDecoration(vsid);
        ProductListAdapter plrv = new ProductListAdapter(getContext(), products);
        rvProductList.setAdapter(plrv);
        llMainContainer.setVisibility(View.GONE);
        rvProductList.setVisibility(View.VISIBLE);
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


    private void convertEditTextToString() {
        Log.d(TAG, "convertEditTextToString: called");
        sInputSearch = etInputSearch.getText().toString();
    }

    private View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View homeView) {
            Log.d(TAG, "searchListener: called");
            convertEditTextToString();
            searchProducts(homeView);
        }
    };

}