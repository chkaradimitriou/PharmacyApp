package com.ckaradimitriou.pharmacyapp.ui.productdetails;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityProductDetailsBinding;
import com.ckaradimitriou.pharmacyapp.model.Product;
import com.google.gson.Gson;

public class ProductDetailsActivity extends AppCompatActivity {

    private ActivityProductDetailsBinding binding;
    private ProductDetailsViewModel viewModel;
    private Product product;
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

        viewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);

        String productJson = getIntent().getStringExtra("PRODUCT");
        if (productJson != null) {
            product = gson.fromJson(productJson, Product.class);
            binding.setProduct(product);
            viewModel.checkIfProductExistsInCart(product);
        }

        binding.addToCartBtn.setOnClickListener(view -> {
            viewModel.onAddToCartBtnClick(product);
        });

        viewModel.isAddedToCart.observe(this, existsInCart -> {
            if (existsInCart) {
                binding.setButtonText("Remove from Cart");
            } else {
                binding.setButtonText("Add to Cart");
            }
        });
    }
}