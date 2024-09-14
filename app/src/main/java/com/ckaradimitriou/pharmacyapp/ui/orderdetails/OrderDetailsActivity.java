package com.ckaradimitriou.pharmacyapp.ui.orderdetails;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ckaradimitriou.pharmacyapp.adapters.products.ProductClickListener;
import com.ckaradimitriou.pharmacyapp.adapters.products.ProductListAdapter;
import com.ckaradimitriou.pharmacyapp.databinding.ActivityOrderDetailsBinding;
import com.ckaradimitriou.pharmacyapp.model.Order;
import com.ckaradimitriou.pharmacyapp.model.Product;
import com.ckaradimitriou.pharmacyapp.ui.productdetails.ProductDetailsActivity;
import com.google.gson.Gson;

public class OrderDetailsActivity extends AppCompatActivity implements ProductClickListener {

    private ActivityOrderDetailsBinding binding;
    private Gson gson = new Gson();
    private Order order;
    private ProductListAdapter adapter = new ProductListAdapter(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        binding.productsRecyclerView.setAdapter(adapter);

        String productJson = getIntent().getStringExtra("ORDER_DETAILS");
        if (productJson != null) {
            order = gson.fromJson(productJson, Order.class);
            binding.setOrder(order);
            adapter.submitList(order.getProducts());
        }
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(OrderDetailsActivity.this, ProductDetailsActivity.class);
        String jsonProduct = gson.toJson(product);
        intent.putExtra("PRODUCT", jsonProduct);
        startActivity(intent);
    }
}