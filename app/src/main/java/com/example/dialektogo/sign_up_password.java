package com.example.dialektogo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sign_up_password extends AppCompatActivity {
    EditText passwordInput;
    Button btnContinue;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_password);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        passwordInput = findViewById(R.id.passwordInput);
        btnContinue = findViewById(R.id.btnContinue);

        // Handle Continue button click
        btnContinue.setOnClickListener(v -> {
            btnContinue.setEnabled(false); // Prevent multiple taps

            String password = passwordInput.getText().toString().trim();
            String email = getIntent().getStringExtra("email");
            String username = getIntent().getStringExtra("username");
            String birthdate = getIntent().getStringExtra("birthdate");

            if (password.length() < 8) {
                Toast.makeText(this, "Password must be at least 8 characters.", Toast.LENGTH_SHORT).show();
                btnContinue.setEnabled(true); // Re-enable on failure
                return;
            }

            // Create the Firebase account with the provided email and password
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser newUser = mAuth.getCurrentUser();

                            // Firestore Add
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("Username", username);
                            userData.put("Email", email);
                            userData.put("Birthday", birthdate);
                            userData.put("Date Account Created", FieldValue.serverTimestamp());
                            userData.put("Subscription", "");

                            // Save user data to Firestore
                            db.collection("users").document(newUser.getUid())
                                    .set(userData)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(this, HomeActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        btnContinue.setEnabled(true); // Re-enable on failure
                                        Toast.makeText(this, "Firestore error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    });
                        } else {
                            btnContinue.setEnabled(true); // Re-enable on failure
                            Toast.makeText(this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}
