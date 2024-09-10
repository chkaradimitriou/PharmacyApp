package com.ckaradimitriou.pharmacyapp.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    MutableLiveData<Boolean> userHasLoggedIn = new MutableLiveData<>();

    void loginUserWithCredentials(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userHasLoggedIn.postValue(true);

                } else {
                    userHasLoggedIn.postValue(false);
                }
            }
        });
    }
}
