package com.ckaradimitriou.pharmacyapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityProfileBinding;
import com.ckaradimitriou.pharmacyapp.model.User;
import com.ckaradimitriou.pharmacyapp.ui.dashboard.DashboardActivity;
import com.ckaradimitriou.pharmacyapp.ui.login.LoginActivity;
import com.ckaradimitriou.pharmacyapp.ui.splash.SplashActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        getUserInformation();

        binding.logOutBtn.setOnClickListener(view -> {
            auth.signOut();
            Intent intent = new Intent(ProfileActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void getUserInformation() {
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

                                binding.userEmailTxtView.setText(user.getEmail());
                                binding.userIdTxtView.setText(user.getUserId());
                                binding.usernameTxtView.setText(user.getUsername());
                            }
                        } else {
                            Toast.makeText(
                                    ProfileActivity.this,
                                    task.getException().getLocalizedMessage().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}