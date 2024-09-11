package com.ckaradimitriou.pharmacyapp.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ckaradimitriou.pharmacyapp.model.Product;
import com.ckaradimitriou.pharmacyapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DashboardViewModel extends ViewModel {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public MutableLiveData<User> user = new MutableLiveData<>();
    public MutableLiveData<List<Product>> products = new MutableLiveData<>();

    void getDashboardInformation() {
        getUsername();
        getProductsFromDatabase();
    }

    private void getUsername() {
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        firestore.collection("users")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                String userId = data.get("userId").toString();
                                String email = data.get("email").toString();
                                String username = data.get("username").toString();
                                String userImg = data.get("userImg").toString();
                                User response = new User(userId, email, username, userImg);
                                user.postValue(response);
                            }
                        } else {
                            user.postValue(null);
                        }
                    }
                });
    }

    private void getProductsFromDatabase() {
        firestore.collection("products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Product> tempProducts = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = document.getData();
                        Product product = new Product(
                                data.get("productId").toString(),
                                data.get("productName").toString(),
                                data.get("productDescription").toString(),
                                data.get("productImg").toString(),
                                Double.valueOf(data.get("productPrice").toString())
                        );
                        tempProducts.add(product);
                    }

                    products.postValue(tempProducts);
                } else {
                    products.postValue(null);
                }
            }
        });
    }
}
