package com.ckaradimitriou.pharmacyapp.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityRegisterBinding;
import com.ckaradimitriou.pharmacyapp.ui.dashboard.DashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        binding.createAccountBtn.setOnClickListener(view -> {
            String email = binding.emailEditTxt.getText().toString();
            String password = binding.passwordEditTxt.getText().toString();
            registerNewUserWithCredentials(email, password);
        });
    }

    private void registerNewUserWithCredentials(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            createUserInformationInDb(email);
                        } else {
                            Toast.makeText(
                                    RegisterActivity.this,
                                    task.getException().getLocalizedMessage().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createUserInformationInDb(String email) {
        String username = email.substring(0, email.lastIndexOf("@"));
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        Map<String, String> user = new HashMap<>();
        user.put("userId", userId);
        user.put("email", email);
        user.put("username", username);
        user.put("userImg", "");

        firestore.collection("users")
                .add(user)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(
                                    RegisterActivity.this,
                                    task.getException().getLocalizedMessage().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}