package com.ckaradimitriou.pharmacyapp.ui.productdetails;

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
import java.util.Map;
import java.util.Objects;

public class ProductDetailsViewModel extends ViewModel {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String userId = Objects.requireNonNull(auth.getCurrentUser().getUid());

    MutableLiveData<Boolean> isAddedToCart = new MutableLiveData<>();
    MutableLiveData<Boolean> productAddedToCart = new MutableLiveData<>();
    private ArrayList<Product> tempProductList = new ArrayList<>();

    void onAddToCartBtnClick(Product product) {
        if (isAddedToCart.getValue()) {
            removeProductFromCart(product);
        } else {
            addProductToCart(product);
        }
    }

    void checkIfProductExistsInCart(Product product) {
        firestore.collection("carts")
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult().getData() != null) {
                            tempProductList.clear();

                            ArrayList<Map<String, Object>> temp = (ArrayList<Map<String, Object>>) task.getResult().get("cart");
                            temp.forEach(item -> {
                                tempProductList.add(
                                        new Product(
                                                item.get("productId").toString(),
                                                item.get("productName").toString(),
                                                item.get("productDescription").toString(),
                                                item.get("productImg").toString(),
                                                Double.valueOf(item.get("productPrice").toString())

                                        )
                                );
                            });

                            Boolean existsInCart = tempProductList
                                    .stream()
                                    .anyMatch(item ->
                                            item.getProductId().equals(product.getProductId())
                                    );

                            isAddedToCart.postValue(existsInCart);
                        } else {
                            isAddedToCart.postValue(false);
                        }
                    }
                });
    }

    private void addProductToCart(Product product) {
        tempProductList.add(product);
        Map<String, Object> data = new HashMap<>();
        data.put("cart", tempProductList);

        firestore.collection("carts")
                .document(userId)
                .set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        productAddedToCart.postValue(true);
                        checkIfProductExistsInCart(product);
                    }
                });
    }

    private void removeProductFromCart(Product product) {
        tempProductList.removeIf(item -> item.getProductId().equals(product.getProductId()));
        Map<String, Object> data = new HashMap<>();
        data.put("cart", tempProductList);

        firestore.collection("carts")
                .document(userId)
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        productAddedToCart.postValue(false);
                        checkIfProductExistsInCart(product);
                    }
                });
    }
}
