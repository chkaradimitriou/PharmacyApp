package com.ckaradimitriou.pharmacyapp.ui.productdetails;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityProductDetailsBinding;
import com.ckaradimitriou.pharmacyapp.model.Product;
import com.google.gson.Gson;

public class ProductDetailsActivity extends AppCompatActivity {

    private ActivityProductDetailsBinding binding;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        String productJson = getIntent().getStringExtra("PRODUCT");
        if (productJson != null) {
            Product product = gson.fromJson(productJson, Product.class);
            binding.setProduct(product);
        }
    }
}