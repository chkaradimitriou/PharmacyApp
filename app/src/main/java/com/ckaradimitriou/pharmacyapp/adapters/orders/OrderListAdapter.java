package com.ckaradimitriou.pharmacyapp.adapters.orders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.ckaradimitriou.pharmacyapp.databinding.HolderOrderItemBinding;
import com.ckaradimitriou.pharmacyapp.model.Order;

public class OrderListAdapter extends ListAdapter<Order, OrderViewHolder> {

    private OrderClickListener listener;

    public OrderListAdapter(OrderClickListener listener) {
        super(new OrderDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HolderOrderItemBinding view = HolderOrderItemBinding.inflate(inflater, parent, false);
        return new OrderViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.show(getItem(position));
    }
}