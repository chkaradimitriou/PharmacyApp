package com.ckaradimitriou.pharmacyapp.ui.orders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ckaradimitriou.pharmacyapp.adapters.orders.OrderClickListener;
import com.ckaradimitriou.pharmacyapp.adapters.orders.OrderListAdapter;
import com.ckaradimitriou.pharmacyapp.databinding.ActivityOrdersBinding;
import com.ckaradimitriou.pharmacyapp.model.Order;
import com.ckaradimitriou.pharmacyapp.ui.orderdetails.OrderDetailsActivity;
import com.google.gson.Gson;

public class OrdersActivity extends AppCompatActivity implements OrderClickListener {

    private ActivityOrdersBinding binding;
    private OrderListAdapter adapter = new OrderListAdapter(this);
    private OrdersViewModel viewModel;
    private Gson gson = new Gson();

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

        binding.ordersRecyclerView.setAdapter(adapter);

        viewModel.getOrders();

        viewModel.orders.observe(this, orders -> {
            adapter.submitList(orders);
        });
    }

    @Override
    public void onOrderClick(Order order) {
        String data = gson.toJson(order);
        Intent intent = new Intent(OrdersActivity.this, OrderDetailsActivity.class);
        intent.putExtra("ORDER_DETAILS", data);
        startActivity(intent);
    }
}
