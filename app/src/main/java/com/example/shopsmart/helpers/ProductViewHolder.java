package com.example.shopsmart.helpers;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    //private static final String TAG="PRODUCT_VIEW_HOLDER";
    private RelativeLayout rlProduct;
    private RelativeLayout rlProductExpanded;
    private ImageView ivProductImageURL;
    private TextView tvProductName;
    private TextView tvProductPrice;
    private TextView tvProductSupermarket;
    private Button btnExtend;

    public ProductViewHolder(View productView) {
        super(productView);
        rlProduct = productView.findViewById(R.id.product);
        rlProductExpanded = productView.findViewById(R.id.product_expanded);
        ivProductImageURL = productView.findViewById(R.id.product_image);
        tvProductName = productView.findViewById(R.id.product_name);
        tvProductPrice = productView.findViewById(R.id.product_price);
        tvProductSupermarket = productView.findViewById(R.id.product_supermarket);
        btnExtend = productView.findViewById(R.id.button_extend);
    }

    public RelativeLayout getRLProduct() {
        return rlProduct;
    }

    public void setRLProduct(RelativeLayout rlProduct) {
        this.rlProduct = rlProduct;
    }

    public RelativeLayout getRLProductExpanded() {
        return rlProductExpanded;
    }

    public void setRLProductExpanded(RelativeLayout rlProductExpanded) {
        this.rlProductExpanded = rlProductExpanded;
    }

    public ImageView getIVProductImageURL() {
        return ivProductImageURL;
    }

    public void setIVProductImageURL(ImageView ivProductImageURL) {
        this.ivProductImageURL = ivProductImageURL;
    }

    public TextView getTVProductName() {
        return tvProductName;
    }

    public void setTVProductName(TextView tvProductName) {
        this.tvProductName = tvProductName;
    }

    public TextView getTVProductPrice() {
        return tvProductPrice;
    }

    public void setTVProductPrice(TextView tvProductPrice) {
        this.tvProductPrice = tvProductPrice;
    }

    public TextView getTvProductSupermarket() {
        return tvProductSupermarket;
    }

    public void setTvProductSupermarket(TextView tvProductSupermarket) {
        this.tvProductSupermarket = tvProductSupermarket;
    }

    public Button getButtonExtend() {
        return btnExtend;
    }

    public void setButtonExtend(Button btnExtend) {
        this.btnExtend = btnExtend;
    }
}
