package com.ckaradimitriou.pharmacyapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityProfileBinding;
import com.ckaradimitriou.pharmacyapp.ui.editusername.EditUsernameActivity;
import com.ckaradimitriou.pharmacyapp.ui.splash.SplashActivity;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding.editUsernameBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, EditUsernameActivity.class);
            startActivity(intent);
        });

        binding.logOutBtn.setOnClickListener(view -> {
            viewModel.logoutUser();
            Intent intent = new Intent(ProfileActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        viewModel.userInfo.observe(this, userInfo -> {
            if (userInfo != null) {
                binding.setUser(userInfo);
            } else {
                Toast.makeText(
                        ProfileActivity.this,
                        "User details not found.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getUserInfo();
    }
}