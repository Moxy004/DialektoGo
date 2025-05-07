package com.example.dialektogo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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

    String sentVerificationCode;
    String email;
    String username;
    String birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_verify_email);

        // Get the passed values from the previous activity
        email = getIntent().getStringExtra("email");
        sentVerificationCode = getIntent().getStringExtra("verificationCode");
        username = getIntent().getStringExtra("username");
        birthdate = getIntent().getStringExtra("birthdate");

        // Initialize UI components
        btnContinue = findViewById(R.id.btnContinue);
        resendCode = findViewById(R.id.resendCode);
        codeInput = findViewById(R.id.codeInput);

        // Handle Continue button click
        // Handle Continue button click
        btnContinue.setOnClickListener(v -> {
            btnContinue.setEnabled(false);  // Disable immediately to prevent multiple taps

            String enteredCode = codeInput.getText().toString().trim();

            if (enteredCode.isEmpty()) {
                Toast.makeText(sign_up_verify_email.this, "Please enter the verification code.", Toast.LENGTH_SHORT).show();
                btnContinue.setEnabled(true);  // Re-enable on failure
                return;
            }

            if (enteredCode.equals(sentVerificationCode)) {
                // Code is correct, proceed
                Intent intent = new Intent(sign_up_verify_email.this, sign_up_password.class);
                intent.putExtra("email", email);
                intent.putExtra("username", username);
                intent.putExtra("birthdate", birthdate);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(sign_up_verify_email.this, "Invalid code. Please try again.", Toast.LENGTH_SHORT).show();
                btnContinue.setEnabled(true);  // Re-enable on failure
            }
        });

// Handle Resend Code click
        resendCode.setOnClickListener(v -> {
            resendCode.setEnabled(false); // Disable immediately to avoid spamming
            if (email != null && !email.isEmpty()) {
                sendVerificationCodeAgain(email);
            } else {
                Toast.makeText(sign_up_verify_email.this, "Email is missing. Please restart the registration process.", Toast.LENGTH_SHORT).show();
                resendCode.setEnabled(true); // Re-enable if something goes wrong
            }
        });
    }

    private void sendVerificationCodeAgain(String email) {
        String newVerificationCode = String.format("%06d", (int) (Math.random() * 1000000));

        Map<String, Object> emailData = new HashMap<>();
        emailData.put("service_id", "service_azuofda");
        emailData.put("template_id", "template_j570oig");
        emailData.put("user_id", "fiLYOS_ZMch-OAh02");

        Map<String, String> templateParams = new HashMap<>();
        templateParams.put("email", email);
        templateParams.put("verification_code", newVerificationCode);
        emailData.put("template_params", templateParams);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.emailjs.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmailJSApi emailService = retrofit.create(EmailJSApi.class);

        emailService.sendEmail(emailData).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                resendCode.setEnabled(true); // Re-enable after response

                if (response.isSuccessful()) {
                    Toast.makeText(sign_up_verify_email.this, "Verification code resent. Please check your inbox.", Toast.LENGTH_SHORT).show();
                    sentVerificationCode = newVerificationCode;
                } else {
                    Toast.makeText(sign_up_verify_email.this, "Failed to resend email. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resendCode.setEnabled(true); // Re-enable on failure
                Toast.makeText(sign_up_verify_email.this, "Error sending verification email: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
