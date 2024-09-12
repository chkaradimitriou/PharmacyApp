package com.ckaradimitriou.pharmacyapp.ui.editusername;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditUsernameViewModel extends ViewModel {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String userId = auth.getCurrentUser().getUid().toString();

    MutableLiveData<Boolean> usernameUpdated = new MutableLiveData<>();
    MutableLiveData<String> username = new MutableLiveData<>();

    void getUserInfo() {
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        firestore.collection("users")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                String userUsername = data.get("username").toString();
                                username.postValue(userUsername);
                            }
                        } else {
                            username.postValue(null);
                        }
                    }
                });
    }

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