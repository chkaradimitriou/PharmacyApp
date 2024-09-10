package com.ckaradimitriou.pharmacyapp.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityRegisterBinding;
import com.ckaradimitriou.pharmacyapp.ui.dashboard.DashboardActivity;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.createAccountBtn.setOnClickListener(view -> {
            String email = binding.emailEditTxt.getText().toString();
            String password = binding.passwordEditTxt.getText().toString();
            viewModel.registerNewUserWithCredentials(email, password);
        });

        viewModel.userHasBeenCreated.observe(this, userHasBeenCreated -> {
            if (userHasBeenCreated) {
                Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(
                        this,
                        "User has not been created. Try again",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}