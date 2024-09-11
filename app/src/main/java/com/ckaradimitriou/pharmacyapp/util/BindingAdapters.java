package com.ckaradimitriou.pharmacyapp.util;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.ckaradimitriou.pharmacyapp.R;

public class BindingAdapters {

    @BindingAdapter("android:setUserInfo")
    public static void setUserInfo(TextView txtView, String value) {
        if (value != null) {
            txtView.setText(value);
        } else {
            txtView.setText("null");
        }
    }

    @BindingAdapter("android:setUserImage")
    public static void setUserImage(ImageView img, String userImgUrl) {
        if (userImgUrl != null && !userImgUrl.isEmpty()) {
            Glide.with(img)
                    .load(userImgUrl)
                    .centerCrop()
                    .placeholder(R.drawable.empty_user_img)
                    .into(img);
        } else {
            img.setImageResource(R.drawable.empty_user_img);
        }
    }

    @BindingAdapter("android:setProductInfo")
    public static void setProductInfo(TextView txtView, String value) {
        if (value != null) {
            txtView.setText(value);
        } else {
            txtView.setText("null");
        }
    }

    @BindingAdapter("android:setProductId")
    public static void setProductId(TextView txtView, String productId) {
        if (productId != null && !productId.isEmpty()) {
            txtView.setText("Code:" + " " + productId);
        } else {
            txtView.setText("null");
        }
    }

    @BindingAdapter("android:setProductPrice")
    public static void setProductPrice(TextView txtView, Double price) {
        if (price != null) {
            txtView.setText(price + "€");
        } else {
            txtView.setText("null");
        }
    }

    @BindingAdapter("android:setProductImage")
    public static void setProductImage(ImageView img, String productImgUrl) {
        if (productImgUrl != null && !productImgUrl.isEmpty()) {
            Glide.with(img)
                    .load(productImgUrl)
                    .centerCrop()
                    .placeholder(R.drawable.empty_user_img)
                    .into(img);
        } else {
            img.setImageResource(R.drawable.empty_user_img);
        }
    }

    @BindingAdapter("android:setOrderTotalAmount")
    public static void setOrderTotalAmount(TextView txtView, Double totalAmount) {
        if (totalAmount != null && totalAmount > 0) {
            txtView.setText("Your total amount is " + totalAmount + "€");
        } else {
            txtView.setText("Your total amount is 0€");
        }
    }
}
