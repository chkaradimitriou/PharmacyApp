package com.ckaradimitriou.pharmacyapp.ui.editusername;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ckaradimitriou.pharmacyapp.R;
import com.ckaradimitriou.pharmacyapp.databinding.ActivityEditUsernameBinding;
import com.google.android.material.snackbar.Snackbar;

import de.mateware.snacky.Snacky;

public class EditUsernameActivity extends AppCompatActivity {

    private ActivityEditUsernameBinding binding;
    private EditUsernameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditUsernameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(EditUsernameViewModel.class);

        binding.editUsernameBtn.setOnClickListener(view -> {
            String username = binding.editNewUsernameTxtView.getText().toString();
            viewModel.updateUsername(username);
        });

        viewModel.usernameUpdated.observe(this, usernameIsUpdated -> {
            Snackbar snackbar = Snacky.builder()
                    .setActivity(EditUsernameActivity.this)
                    .success()
                    .setDuration(Snacky.LENGTH_LONG);

            if (usernameIsUpdated) {
                snackbar.setText(getString(R.string.edit_username_snackbar_save)).show();
            } else {
                snackbar.setText(getString(R.string.edit_username_snackbar_error_message)).show();
            }
        });
    }
}