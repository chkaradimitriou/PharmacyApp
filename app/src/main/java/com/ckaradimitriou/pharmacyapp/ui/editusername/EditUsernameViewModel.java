package com.ckaradimitriou.pharmacyapp.ui.editusername;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditUsernameViewModel extends ViewModel {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String userId = auth.getCurrentUser().getUid().toString();

    MutableLiveData<Boolean> usernameUpdated = new MutableLiveData<>();

    void updateUsername(String newUsername) {
        Map<String, Object> data = new HashMap<>();
        data.put("username", newUsername);

        firestore.collection("users")
                .document(userId)
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            usernameUpdated.postValue(true);
                        } else {
                            usernameUpdated.postValue(false);
                        }
                    }
                });
    }
}