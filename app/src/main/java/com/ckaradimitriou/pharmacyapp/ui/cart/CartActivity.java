package com.ckaradimitriou.pharmacyapp.ui.cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ckaradimitriou.pharmacyapp.adapters.products.ProductClickListener;
import com.ckaradimitriou.pharmacyapp.adapters.products.ProductListAdapter;
import com.ckaradimitriou.pharmacyapp.databinding.ActivityCartBinding;
import com.ckaradimitriou.pharmacyapp.model.Product;
import com.ckaradimitriou.pharmacyapp.ui.orderaddress.OrderAddressActivity;
import com.ckaradimitriou.pharmacyapp.ui.productdetails.ProductDetailsActivity;
import com.google.gson.Gson;

public class CartActivity extends AppCompatActivity implements ProductClickListener {

    private ActivityCartBinding binding;
    private ProductListAdapter adapter = new ProductListAdapter(this);
    private CartViewModel viewModel;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CartViewModel.class);

        binding.productsRecyclerView.setAdapter(adapter);

        binding.cartOrderBtn.setOnClickListener(view -> {
            String products = gson.toJson(viewModel.products.getValue());
            Intent intent = new Intent(CartActivity.this, OrderAddressActivity.class);
            intent.putExtra("PRODUCTS", products);
            startActivity(intent);
        });

        viewModel.products.observe(this, products -> {
            adapter.submitList(products);
        });

        viewModel.orderTotalAmount.observe(this, totalAmount -> {
            binding.setOrderTotalAmount(totalAmount);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getUserCart();
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
        String jsonProduct = gson.toJson(product);
        intent.putExtra("PRODUCT", jsonProduct);
        startActivity(intent);
    }
}