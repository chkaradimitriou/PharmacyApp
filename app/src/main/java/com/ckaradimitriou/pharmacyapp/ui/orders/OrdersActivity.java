package com.ckaradimitriou.pharmacyapp.ui.orders;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityOrdersBinding;

public class OrdersActivity extends AppCompatActivity {

    private ActivityOrdersBinding binding;
    private OrdersViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
    }
}
