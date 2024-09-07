package com.ckaradimitriou.pharmacyapp.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ckaradimitriou.pharmacyapp.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        /*
         * FIXME: To be added:
         *
         * - Cart imageview & text should be clickable and it will trigger the CartActivity.
         * - Profile imageview should be clickable and it will trigger the ProfileActivity.
         *
         */
    }
}