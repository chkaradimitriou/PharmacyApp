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
}
