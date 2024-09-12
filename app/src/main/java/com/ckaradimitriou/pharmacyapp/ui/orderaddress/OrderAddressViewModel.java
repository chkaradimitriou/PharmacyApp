package com.ckaradimitriou.pharmacyapp.ui.orderaddress;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ckaradimitriou.pharmacyapp.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderAddressViewModel extends ViewModel {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String userId = Objects.requireNonNull(auth.getCurrentUser().getUid());

    MutableLiveData<Boolean> orderPlacedSuccessfully = new MutableLiveData<>();

    private List<Product> products;

    void setProductList(List<Product> products) {
        this.products = products;
    }

    void performOrder(String fullName, String address, String city, String postalCode, String phone) {
        if (!products.isEmpty()) {
            Double total = products.stream().mapToDouble(o -> o.getProductPrice()).sum();

            Map<String, Object> data = new HashMap<>();
            data.put("userId", userId);
            data.put("fullName", fullName);
            data.put("address", address);
            data.put("city", city);
            data.put("postalCode", postalCode);
            data.put("phone", phone);
            data.put("products", products);
            data.put("orderTotal", total);

            firestore.collection("orders")
                    .document(userId)
                    .set(data)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                deleteCartEntry();
                            } else {
                                orderPlacedSuccessfully.postValue(false);
                            }
                        }
                    });
        }
    }

    private void deleteCartEntry() {
        firestore.collection("carts")
                .document(userId)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        orderPlacedSuccessfully.postValue(task.isSuccessful());
                    }
                });
    }
}
