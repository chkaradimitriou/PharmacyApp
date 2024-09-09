package com.ckaradimitriou.pharmacyapp.adapters.products;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.ckaradimitriou.pharmacyapp.databinding.HolderProductItemBinding;
import com.ckaradimitriou.pharmacyapp.model.Product;

public class ProductListAdapter extends ListAdapter<Product, ProductListViewHolder> {

    private ProductClickListener listener;

    public ProductListAdapter(ProductClickListener listener) {
        super(new ProductDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HolderProductItemBinding view = HolderProductItemBinding.inflate(inflater, parent, false);
        return new ProductListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        holder.show(getItem(position));
    }
}
