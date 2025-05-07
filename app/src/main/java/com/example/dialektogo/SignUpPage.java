package com.example.dialektogo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.Calendar;

public class SignUpPage extends AppCompatActivity {

    EditText birthMonth, birthDay, birthYear, editEmail, editUsername;
    ImageButton backIcon;
    TextView alreadyHaveAccount;
    Button btnContinue;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_page);

        mAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        birthMonth = findViewById(R.id.birthMonth);
        birthDay = findViewById(R.id.birthDay);
        birthYear = findViewById(R.id.birthYear);
        btnContinue = findViewById(R.id.btnContinue);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        backIcon = findViewById(R.id.backIcon);

        // Common click listener for all 3 fields
        View.OnClickListener openDatePicker = v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpPage.this,
                    (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                        birthMonth.setText(String.format("%02d", selectedMonth + 1));
                        birthDay.setText(String.format("%02d", selectedDay));
                        birthYear.setText(String.valueOf(selectedYear));
                    }, year, month, day);

            datePickerDialog.show();
        };

        birthMonth.setFocusable(false);
        birthDay.setFocusable(false);
        birthYear.setFocusable(false);

        birthMonth.setOnClickListener(openDatePicker);
        birthDay.setOnClickListener(openDatePicker);
        birthYear.setOnClickListener(openDatePicker);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        alreadyHaveAccount.setOnClickListener(view -> {
            finish();
            Intent intent = new Intent(SignUpPage.this, LoginPage.class);
            startActivity(intent);
        });

        btnContinue.setOnClickListener(view -> {
            btnContinue.setEnabled(false); // Disable the button immediately

            String email = editEmail.getText().toString().trim();
            String username = editUsername.getText().toString().trim();
            String month = birthMonth.getText().toString().trim();
            String day = birthDay.getText().toString().trim();
            String year = birthYear.getText().toString().trim();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editEmail.setError("Enter a valid email");
                editEmail.requestFocus();
                btnContinue.setEnabled(true);
                return;
            }

            if (username.isEmpty()) {
                editUsername.setError("Username required");
                editUsername.requestFocus();
                btnContinue.setEnabled(true);
                return;
            }

            if (month.isEmpty() || day.isEmpty() || year.isEmpty()) {
                Toast.makeText(this, "Complete your birthday", Toast.LENGTH_SHORT).show();
                btnContinue.setEnabled(true);
                return;
            }

            // Generate a 6-digit code
            String verificationCode = String.format("%06d", (int) (Math.random() * 1000000));

            Map<String, Object> emailData = new HashMap<>();
            emailData.put("service_id", "service_azuofda");
            emailData.put("template_id", "template_j570oig");
            emailData.put("user_id", "fiLYOS_ZMch-OAh02");

            Map<String, String> templateParams = new HashMap<>();
            templateParams.put("to_name", username);
            templateParams.put("email", email);
            templateParams.put("time", new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date()));
            templateParams.put("message", "Please verify your email for DialektoGo.");
            templateParams.put("verification_code", verificationCode);
            emailData.put("template_params", templateParams);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.emailjs.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EmailJSApi emailService = retrofit.create(EmailJSApi.class);

            emailService.sendEmail(emailData).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(SignUpPage.this,
                                "Verification code sent to " + email,
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SignUpPage.this, sign_up_verify_email.class);
                        intent.putExtra("email", email);
                        intent.putExtra("username", username);
                        intent.putExtra("birthdate", month + "/" + day + "/" + year);
                        intent.putExtra("verificationCode", verificationCode);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("EmailJS Error", errorBody);
                            Toast.makeText(SignUpPage.this, "Error: " + response.code() + "\n" + errorBody, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        btnContinue.setEnabled(true); // Re-enable on failure
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("EmailJS Failure", t.getMessage(), t);
                    Toast.makeText(SignUpPage.this, "Email sending failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    btnContinue.setEnabled(true); // Re-enable on failure
                }
            });
        });
    }
}
