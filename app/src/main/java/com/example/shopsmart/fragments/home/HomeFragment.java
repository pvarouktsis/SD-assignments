package com.example.shopsmart.fragments.home;

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
import com.example.shopsmart.classes.Product;
import com.example.shopsmart.helpers.ProductListAdapter;
import com.example.shopsmart.helpers.VerticalSpaceItemDecoration;
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
    private String sInputSearch;
    private EditText etInputText;
    private ImageButton btnSearch;
    private LinearLayout llFrameSearch;
    private RecyclerView rv;
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
        etInputText = homeView.findViewById(R.id.input_search);
        btnSearch = homeView.findViewById(R.id.button_search);
        llFrameSearch = homeView.findViewById(R.id.frame_search);
        rv = homeView.findViewById(R.id.product_list);

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
        rv.setLayoutManager(llm);
        VerticalSpaceItemDecoration vsid = new VerticalSpaceItemDecoration(VERTICAL_SPACE);
        rv.addItemDecoration(vsid);
        ProductListAdapter plrv = new ProductListAdapter(getContext(), products);
        rv.setAdapter(plrv);

        llFrameSearch.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }

    private void addProduct(DocumentSnapshot d) {
        Log.d(TAG, "addProduct: called");
        Product p = new Product();
        p.setProductID(d.getId());
        p.setProductName(d.getString("name"));
        p.setProductPrice(d.getDouble("price"));
        p.setProductImageURL(d.getString("image_url"));
        products.add(p);
    }


    private void convertEditTextToString() {
        Log.d(TAG, "convertEditTextToString: called");
        sInputSearch = etInputText.getText().toString();
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