package com.ckaradimitriou.pharmacyapp.ui.orders;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ckaradimitriou.pharmacyapp.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrdersViewModel extends ViewModel {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String userId = Objects.requireNonNull(auth.getCurrentUser().getUid());

    MutableLiveData<List<Order>> orders = new MutableLiveData<>();

    private ArrayList<Order> tempOrderList = new ArrayList<>();

    public void getOrders() {
        firestore.collection("orders")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            tempOrderList.clear();

                            task.getResult().getDocuments().forEach(item -> {
                                Order order = item.toObject(Order.class);
                                tempOrderList.add(order);
                            });

                            orders.postValue(tempOrderList);
                        } else {
                            orders.postValue(null);
                        }
                    }
                });
    }
}