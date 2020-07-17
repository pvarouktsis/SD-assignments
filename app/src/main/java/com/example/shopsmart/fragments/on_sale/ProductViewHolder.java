package com.example.shopsmart.fragments.on_sale;

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
    private ImageView ivProductImageURL;
    private TextView tvProductName;
    private TextView tvProductPrice;
    private Button btnExtend;

    public ProductViewHolder(View productView) {
        super(productView);
        rlProduct = productView.findViewById(R.id.product);
        ivProductImageURL = productView.findViewById(R.id.image_product);
        tvProductName = productView.findViewById(R.id.product_name);
        tvProductPrice = productView.findViewById(R.id.product_price);
        btnExtend = productView.findViewById(R.id.button_extend);
    }

    public RelativeLayout getRLFrameProduct() {
        return rlProduct;
    }

    public void setRLFrameProduct(RelativeLayout rlFrameProduct) {
        this.rlProduct = rlFrameProduct;
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

    public Button getBtnExtend() {
        return btnExtend;
    }

    public void setBtnExtend(Button btnExtend) {
        this.btnExtend = btnExtend;
    }
}
