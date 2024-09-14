package com.ckaradimitriou.pharmacyapp.adapters.orders;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.ckaradimitriou.pharmacyapp.model.Order;

public class OrderDiffCallback extends DiffUtil.ItemCallback<Order>{

    @Override
    public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
        return oldItem == newItem;
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
        return oldItem == newItem;
    }
}
