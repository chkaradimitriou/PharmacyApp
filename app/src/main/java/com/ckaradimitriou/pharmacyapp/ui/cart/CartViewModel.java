package com.ckaradimitriou.pharmacyapp.ui.cart;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ckaradimitriou.pharmacyapp.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CartViewModel extends ViewModel {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String userId = Objects.requireNonNull(auth.getCurrentUser().getUid());

    MutableLiveData<List<Product>> products = new MutableLiveData<>();
    MutableLiveData<Double> orderTotalAmount = new MutableLiveData<>();
    private ArrayList<Product> tempProductList = new ArrayList<>();
    private Double totalAmount = 0.0;

    public void getUserCart() {
        firestore.collection("carts")
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult().getData() != null) {
                            tempProductList.clear();
                            totalAmount = 0.0;

                            ArrayList<Map<String, Object>> temp = (ArrayList<Map<String, Object>>) task.getResult().get("cart");
                            temp.forEach(item -> {
                                Double productPrice = Double.valueOf(item.get("productPrice").toString());
                                tempProductList.add(
                                        new Product(
                                                item.get("productId").toString(),
                                                item.get("productName").toString(),
                                                item.get("productDescription").toString(),
                                                item.get("productImg").toString(),
                                                productPrice
                                        )
                                );

                                totalAmount += productPrice;
                            });

                        }

                        products.postValue(tempProductList);
                        orderTotalAmount.postValue(totalAmount);
                    }
                });
    }
}