package com.ckaradimitriou.pharmacyapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityDashboardBinding;
import com.ckaradimitriou.pharmacyapp.model.User;
import com.ckaradimitriou.pharmacyapp.ui.cart.CartActivity;
import com.ckaradimitriou.pharmacyapp.ui.profile.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.Objects;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        getUsername();

        binding.cartImgView.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, CartActivity.class);
            startActivity(intent);
        });

        binding.cartTxtView.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, CartActivity.class);
            startActivity(intent);
        });

        binding.userImgView.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        binding.greetingTxtView.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        binding.usernameTxtView.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
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
                                User user = new User(userId, email, username, userImg);
                                binding.setUser(user);
                            }
                        } else {
                            Toast.makeText(
                                    DashboardActivity.this,
                                    task.getException().getLocalizedMessage().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}