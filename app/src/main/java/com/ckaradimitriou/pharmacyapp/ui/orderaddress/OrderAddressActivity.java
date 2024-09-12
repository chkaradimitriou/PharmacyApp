package com.ckaradimitriou.pharmacyapp.ui.orderaddress;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityOrderAdressBinding;
import com.ckaradimitriou.pharmacyapp.model.Product;
import com.ckaradimitriou.pharmacyapp.ui.dashboard.DashboardActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class OrderAddressActivity extends AppCompatActivity {

    private ActivityOrderAdressBinding binding;
    private OrderAddressViewModel viewModel;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderAdressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(OrderAddressViewModel.class);

        String productList = getIntent().getStringExtra("PRODUCTS");
        if (productList != null) {
            Type listType = new TypeToken<List<Product>>() {
            }.getType();

            List<Product> products = gson.fromJson(productList, listType);
            viewModel.setProductList(products);
        }

        binding.continueBtn.setOnClickListener(view -> {
            String fullName = binding.orderNameEditTxt.getText().toString();
            String address = binding.orderAddressEditTxt.getText().toString();
            String city = binding.orderCityEditTxt.getText().toString();
            String postalCode = binding.orderPostalCodeEditTxt.getText().toString();
            String phone = binding.orderPhoneEditTxt.getText().toString();
            viewModel.performOrder(fullName, address, city, postalCode, phone);
        });

        viewModel.orderPlacedSuccessfully.observe(this, orderPlacedSuccessfully -> {
            if (orderPlacedSuccessfully) {
                Intent intent = new Intent(OrderAddressActivity.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Toast.makeText(
                        this,
                        "Generic error. Please Try again!",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}