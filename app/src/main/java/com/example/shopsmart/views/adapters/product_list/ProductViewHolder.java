package com.example.shopsmart.views.adapters.product_list;

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
    private RelativeLayout rlProductNormal;
    private RelativeLayout rlProductExpanded;
    private ImageView ivProductImage;
    private TextView tvProductName;
    private TextView tvProductPrice;
    private TextView tvProductSupermarket;
    private Button btnExtend;
    private Button btnAdd;
    private Button btnRemove;

    public ProductViewHolder(View productView) {
        super(productView);
        rlProduct = productView.findViewById(R.id.rl_product);
        rlProductNormal = productView.findViewById(R.id.rl_product_standard);
        rlProductExpanded = productView.findViewById(R.id.rl_product_expanded);
        ivProductImage = productView.findViewById(R.id.iv_product_image);
        tvProductName = productView.findViewById(R.id.tv_product_name);
        tvProductPrice = productView.findViewById(R.id.tv_product_price);
        tvProductSupermarket = productView.findViewById(R.id.tv_product_supermarket);
        btnExtend = productView.findViewById(R.id.btn_extend);
        btnAdd = productView.findViewById(R.id.btn_add);
        btnRemove = productView.findViewById(R.id.btn_remove);
    }

    public RelativeLayout getRLProduct() {
        return rlProduct;
    }

    public void setRLProduct(RelativeLayout rlProduct) {
        this.rlProduct = rlProduct;
    }

    public RelativeLayout getRLProductNormal() {
        return rlProductNormal;
    }

    public void setRLProductNormal(RelativeLayout rlProductNormal) {
        this.rlProductNormal = rlProductNormal;
    }

    public RelativeLayout getRLProductExpanded() {
        return rlProductExpanded;
    }

    public void setRLProductExpanded(RelativeLayout rlProductExpanded) {
        this.rlProductExpanded = rlProductExpanded;
    }

    public ImageView getIVProductImageURL() {
        return ivProductImage;
    }

    public void setIVProductImageURL(ImageView ivProductImageURL) {
        this.ivProductImage = ivProductImageURL;
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

    public TextView getTVProductSupermarket() {
        return tvProductSupermarket;
    }

    public void setTVProductSupermarket(TextView tvProductSupermarket) {
        this.tvProductSupermarket = tvProductSupermarket;
    }

    public Button getBtnExtend() {
        return btnExtend;
    }

    public void setBtnExtend(Button btnExtend) {
        this.btnExtend = btnExtend;
    }

    public Button getBtnAdd() { return btnAdd; }

    public void setBtnAdd(Button btnAdd) { this.btnAdd = btnAdd; }

    public Button getBtnRemove() {
        return btnRemove;
    }

    public void setBtnRemove(Button btnRemove) {
        this.btnRemove = btnRemove;
    }
}
