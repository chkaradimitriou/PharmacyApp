package com.ckaradimitriou.pharmacyapp.adapters.orders;

import androidx.recyclerview.widget.RecyclerView;

import com.ckaradimitriou.pharmacyapp.databinding.HolderOrderItemBinding;
import com.ckaradimitriou.pharmacyapp.model.Order;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    private HolderOrderItemBinding binding;
    private OrderClickListener listener;

    public OrderViewHolder(HolderOrderItemBinding binding, OrderClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void show(Order order) {
        binding.setOrder(order);

        binding.getRoot().setOnClickListener(view -> {
            listener.onOrderClick(order);
        });
    }
}