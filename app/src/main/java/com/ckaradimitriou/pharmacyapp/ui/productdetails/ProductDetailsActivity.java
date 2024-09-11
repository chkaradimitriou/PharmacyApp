package com.ckaradimitriou.pharmacyapp.ui.productdetails;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ckaradimitriou.pharmacyapp.R;
import com.ckaradimitriou.pharmacyapp.databinding.ActivityProductDetailsBinding;
import com.ckaradimitriou.pharmacyapp.model.Product;
import com.google.gson.Gson;

import de.mateware.snacky.Snacky;

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
                binding.setButtonText(getString(R.string.remove_button_text));
            } else {
                binding.setButtonText(getString(R.string.add_button_text));
            }
        });

        viewModel.productAddedToCart.observe(this, productAddedToCart -> {
            String snackBarText;
            if (productAddedToCart) {
                snackBarText = (getString(R.string.add_snackbar_text));
            } else {
                snackBarText = (getString(R.string.remove_snackbar_text));
            }

            Snacky.builder()
                    .setActivity(ProductDetailsActivity.this)
                    .setText(snackBarText)
                    .success()
                    .setDuration(Snacky.LENGTH_LONG)
                    .show();
        });
    }
}