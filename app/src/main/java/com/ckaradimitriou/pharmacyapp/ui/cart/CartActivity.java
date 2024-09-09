package com.ckaradimitriou.pharmacyapp.ui.cart;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.ckaradimitriou.pharmacyapp.R;
import com.ckaradimitriou.pharmacyapp.databinding.ActivityCartBinding;
import com.ckaradimitriou.pharmacyapp.databinding.ActivityProfileBinding;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}