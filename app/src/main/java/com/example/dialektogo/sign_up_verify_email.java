package com.example.dialektogo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sign_up_verify_email extends AppCompatActivity {

    EditText codeInput;
    TextView resendCode;
    Button btnContinue;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String sentVerificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_verify_email);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        btnContinue = findViewById(R.id.btnContinue);
        resendCode = findViewById(R.id.resendCode);
        codeInput = findViewById(R.id.codeInput);

        // Get the verification code sent to the email
        sentVerificationCode = getIntent().getStringExtra("verificationCode");

        btnContinue.setOnClickListener(v -> {
            String enteredCode = codeInput.getText().toString().trim();

            if (enteredCode.isEmpty()) {
                Toast.makeText(sign_up_verify_email.this, "Please enter the verification code.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (enteredCode.equals(sentVerificationCode)) {
                // Verification code is correct, proceed to the next step
                Intent intent = new Intent(sign_up_verify_email.this, sign_up_password.class);
                intent.putExtra("email", user.getEmail());
                startActivity(intent);
                finish();
            } else {
                // Code does not match
                Toast.makeText(sign_up_verify_email.this, "Invalid code. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Resend Code button click
        resendCode.setOnClickListener(v -> {
            if (user != null) {
                // Send the verification code again via your custom API (EmailJS)
                sendVerificationCodeAgain(user.getEmail());
            } else {
                Toast.makeText(sign_up_verify_email.this, "No user found. Please log in again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Function to resend verification code via your API (EmailJS)
    private void sendVerificationCodeAgain(String email) {
        // Generate a new 6-digit verification code
        String newVerificationCode = String.format("%06d", (int) (Math.random() * 1000000));

        // Send verification code via your API (EmailJS)
        Map<String, Object> emailData = new HashMap<>();
        emailData.put("service_id", "service_azuofda");
        emailData.put("template_id", "template_j570oig");
        emailData.put("user_id", "fiLYOS_ZMch-OAh02");

        Map<String, String> templateParams = new HashMap<>();
        templateParams.put("email", email);  // Email of the user
        templateParams.put("verification_code", newVerificationCode);  // New verification code to send
        emailData.put("template_params", templateParams);

        // Retrofit setup to send email via EmailJS
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.emailjs.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmailJSApi emailService = retrofit.create(EmailJSApi.class);

        emailService.sendEmail(emailData).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(sign_up_verify_email.this, "Verification code resent. Please check your inbox.", Toast.LENGTH_SHORT).show();

                    // Update the sentVerificationCode to the new code
                    sentVerificationCode = newVerificationCode;
                } else {
                    Toast.makeText(sign_up_verify_email.this, "Failed to resend email. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(sign_up_verify_email.this, "Error sending verification email: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
