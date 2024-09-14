package com.ckaradimitriou.pharmacyapp.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ckaradimitriou.pharmacyapp.R;
import com.ckaradimitriou.pharmacyapp.databinding.ActivityProfileBinding;
import com.ckaradimitriou.pharmacyapp.ui.editusername.EditUsernameActivity;
import com.ckaradimitriou.pharmacyapp.ui.orders.OrdersActivity;
import com.ckaradimitriou.pharmacyapp.ui.splash.SplashActivity;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private ProfileViewModel viewModel;

    private ActivityResultLauncher<Intent> photoIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Bitmap image = (Bitmap) result.getData().getExtras().get("data");
                    viewModel.uploadImage(image);
                }
            }
    );

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

        binding.profileImgView.setOnClickListener(view -> {
            takePhoto();
        });

        binding.editImgView.setOnClickListener(view -> {
            takePhoto();
        });

        binding.myOrdersBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, OrdersActivity.class);
            startActivity(intent);
        });

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
                        getString(R.string.profile_error_message),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoIntent.launch(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getUserInfo();
    }
}