package com.ckaradimitriou.pharmacyapp.util;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.ckaradimitriou.pharmacyapp.R;
import com.ckaradimitriou.pharmacyapp.model.Order;
import com.ckaradimitriou.pharmacyapp.model.Product;

import java.util.List;

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

    @BindingAdapter("setOrderId")
    public static void setOrderId(TextView txtView, String orderId) {
        if (orderId != null && !orderId.isEmpty()) {
            txtView.setText("Order: " + "#" + orderId);
        } else {
            txtView.setText("No Order ID provided");
        }
    }

    @BindingAdapter("setOrderFullAddress")
    public static void setOrderId(TextView txtView, Order order) {
        if (order != null) {
            String address = order.getAddress();
            String city = order.getCity();
            String postalCode = order.getPostalCode();

            String fullAddress = address + ", " + city + ", " + postalCode;
            txtView.setText(fullAddress);
        } else {
            txtView.setText("No Address found.");
        }
    }

    @BindingAdapter("setOrderProductCount")
    public static void setOrderProductCount(TextView txtView, List<Product> products) {
        if (products != null) {
            int totalProducts = products.size();
            txtView.setText(totalProducts + " Products ordered.");
        } else {
            txtView.setText("No Address found.");
        }
    }

    @BindingAdapter("setOrderTotal")
    public static void setOrderTotal(TextView txtView, List<Product> products) {
        if (products != null) {
            Double total = products.stream().mapToDouble(o -> o.getProductPrice()).sum();
            txtView.setText("Total amount: " + total + "€");
        } else {
            txtView.setText("No Address found.");
        }
    }
}
