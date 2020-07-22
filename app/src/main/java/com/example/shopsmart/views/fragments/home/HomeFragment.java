package com.example.shopsmart.views.fragments.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class HomeFragment extends Fragment {
    private static final String TAG = "HOME_FRAGMENT";
    private static final int VERTICAL_SPACE = 20;
    private ArrayList<Product> products = new ArrayList<>();
    private String sSearch;
    private RelativeLayout rlHomeContainer;
    private RelativeLayout rlSearchContainer;
    private RelativeLayout rlSearchForm;
    private EditText etSearch;
    private ImageButton ibtnSearch;
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
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        initializeUIComponents(homeView);

        return homeView;
    }

    private void initializeUIComponents(View homeView) {
        Log.d(TAG, "initializeUIComponents: called");

        // initialize components
        rlHomeContainer = homeView.findViewById(R.id.rl_home_container);
        rlSearchContainer = homeView.findViewById(R.id.rl_search_container);
        rlSearchForm = homeView.findViewById(R.id.rl_search_form);
        etSearch = homeView.findViewById(R.id.et_search);
        ibtnSearch = homeView.findViewById(R.id.ibtn_search);
        svProductListContainer = homeView.findViewById(R.id.sv_product_list_container);
        rlProductListContainer = homeView.findViewById(R.id.rl_product_list_container);
        tvTitle = homeView.findViewById(R.id.tv_title);
        btnFilter = homeView.findViewById(R.id.btn_filter);
        tvNoProducts = homeView.findViewById(R.id.tv_no_products);
        rvProductList = homeView.findViewById(R.id.rv_product_list);

        // initialize visibility
        rlSearchContainer.setVisibility(View.VISIBLE);
        svProductListContainer.setVisibility(View.GONE);
        tvNoProducts.setVisibility(View.GONE);
        rvProductList.setVisibility(View.VISIBLE);

        // initialize title
        tvTitle.setText(R.string.title_home);

        // on click
        ibtnSearch.setOnClickListener(searchListener);
    }

    private void searchProducts(final View homeView) {
        Log.d(TAG, "searchProducts: called");

        // TODO
        // full-text search

        ffdb.collection("products")
            .whereEqualTo("name", sSearch)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "searchProducts: succeeded");
                        for (QueryDocumentSnapshot d : task.getResult()) {
                            if (!d.getId().equals("prototype")) { // if it is NOT the prototype document
                                addProduct(d);
                            }
                        }
                        showProducts(homeView);
                    } else {
                        Log.w(TAG, "searchProducts: failed", task.getException());
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

        // update visibility
        rlSearchContainer.setVisibility(View.GONE);
        svProductListContainer.setVisibility(View.VISIBLE);

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

    private void convertEditTextToString() {
        Log.d(TAG, "convertEditTextToString: called");
        sSearch = etSearch.getText().toString().trim();
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