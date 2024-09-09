package com.ckaradimitriou.pharmacyapp.adapters.products;

import androidx.recyclerview.widget.RecyclerView;

import com.ckaradimitriou.pharmacyapp.databinding.HolderProductItemBinding;
import com.ckaradimitriou.pharmacyapp.model.Product;

public class ProductListViewHolder extends RecyclerView.ViewHolder {

    private HolderProductItemBinding binding;
    private ProductClickListener listener;

    public ProductListViewHolder(HolderProductItemBinding binding, ProductClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void show(Product product) {
        binding.setProduct(product);

        binding.nameConstraint.setOnClickListener(view -> {
            listener.onProductClick(product);
        });
    }
}