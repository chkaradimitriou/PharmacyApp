package com.ckaradimitriou.pharmacyapp.adapters.products;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.ckaradimitriou.pharmacyapp.model.Product;

public class ProductDiffCallback extends DiffUtil.ItemCallback<Product> {

    @Override
    public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
        return oldItem == newItem;
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
        return oldItem == newItem;
    }
}