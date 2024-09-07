package com.ckaradimitriou.pharmacyapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityLoginBinding;
import com.ckaradimitriou.pharmacyapp.ui.dashboard.DashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        binding.loginAccountBtn.setOnClickListener(view -> {
            String email = binding.emailEditTxt.getText().toString();
            String password = binding.passwordEditTxt.getText().toString();
            loginUserWithCredentials(email, password);
        });
    }

    private void loginUserWithCredentials(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(
                            LoginActivity.this,
                            task.getException().getLocalizedMessage().toString(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }
}