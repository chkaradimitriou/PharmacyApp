package com.ckaradimitriou.pharmacyapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ckaradimitriou.pharmacyapp.R;
import com.ckaradimitriou.pharmacyapp.adapters.products.ProductClickListener;
import com.ckaradimitriou.pharmacyapp.adapters.products.ProductListAdapter;
import com.ckaradimitriou.pharmacyapp.databinding.ActivityDashboardBinding;
import com.ckaradimitriou.pharmacyapp.model.Product;
import com.ckaradimitriou.pharmacyapp.ui.cart.CartActivity;
import com.ckaradimitriou.pharmacyapp.ui.productdetails.ProductDetailsActivity;
import com.ckaradimitriou.pharmacyapp.ui.profile.ProfileActivity;
import com.google.gson.Gson;

public class DashboardActivity extends AppCompatActivity implements ProductClickListener {

    private ActivityDashboardBinding binding;
    private DashboardViewModel viewModel;
    private ProductListAdapter adapter = new ProductListAdapter(this);
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding.productsRecyclerView.setAdapter(adapter);

        binding.cartImgView.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, CartActivity.class);
            startActivity(intent);
        });

        binding.cartTxtView.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, CartActivity.class);
            startActivity(intent);
        });

        binding.userImgView.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        binding.greetingTxtView.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        binding.usernameTxtView.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        viewModel.user.observe(this, user -> {
            if (user != null) {
                binding.setUser(user);
            } else {
                Toast.makeText(
                        this,
                        (getString(R.string.user_error_message)),
                        Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.products.observe(this, products -> {
            if (products != null) {
                adapter.submitList(products);
            } else {
                Toast.makeText(
                        this,
                        (getString(R.string.products_error_message)),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getDashboardInformation();
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(DashboardActivity.this, ProductDetailsActivity.class);
        String jsonProduct = gson.toJson(product);
        intent.putExtra("PRODUCT", jsonProduct);
        startActivity(intent);
    }
}