package com.ckaradimitriou.pharmacyapp.ui.productdetails;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityProductDetailsBinding;
import com.ckaradimitriou.pharmacyapp.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    private ActivityProductDetailsBinding binding;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();

        String productId = getIntent().getStringExtra("PRODUCT_ID");
        if (productId != null) {
            getProduct(productId);
        }
    }

    private void getProduct(String productId) {
        firestore.collection("products")
                .whereEqualTo("productId", productId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                String productDescription = data.get("productDescription").toString();
                                String productId = data.get("productId").toString();
                                String productImg = data.get("productImg").toString();
                                String productName = data.get("productName").toString();
                                Double productPrice = Double.valueOf(data.get("productPrice").toString());
                                Product product = new Product(
                                        productId,
                                        productName,
                                        productDescription,
                                        productImg,
                                        productPrice
                                );
                                binding.setProduct(product);
                            }
                        } else {
                            Toast.makeText(
                                    ProductDetailsActivity.this,
                                    task.getException().getLocalizedMessage().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}