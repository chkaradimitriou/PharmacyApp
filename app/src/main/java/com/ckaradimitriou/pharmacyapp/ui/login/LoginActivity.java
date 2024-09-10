package com.ckaradimitriou.pharmacyapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityLoginBinding;
import com.ckaradimitriou.pharmacyapp.ui.dashboard.DashboardActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.loginAccountBtn.setOnClickListener(view -> {
            String email = binding.emailEditTxt.getText().toString();
            String password = binding.passwordEditTxt.getText().toString();
            viewModel.loginUserWithCredentials(email, password);
        });

        viewModel.userHasLoggedIn.observe(this, userHasLoggedIn -> {
            if (userHasLoggedIn) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(
                        this,
                        "Wrong Credentials. Try again",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}