package com.incapp.onlinelearning;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    TextView textViewForgotPassword;
    Button buttonLogin;
    ProgressBar progressBar;
    LinearLayout linearLayoutRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        textViewForgotPassword = (TextView) findViewById(R.id.textView_forgot_password);
        buttonLogin = (Button) findViewById(R.id.button_login);
        progressBar = findViewById(R.id.progressBar);
        linearLayoutRegister = (LinearLayout) findViewById(R.id.linearLayout_register);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (email.isEmpty()) {
                    editTextEmail.setError("Required!");
                    editTextEmail.requestFocus();
                } else if (!email.matches(Patterns.EMAIL_ADDRESS.pattern())) {
                    editTextEmail.setError("Invalid Email!");
                    editTextEmail.requestFocus();
                } else if (password.isEmpty()) {
                    editTextPassword.setError("Required!");
                    editTextPassword.requestFocus();
                } else if (password.length() < 6) {
                    editTextPassword.setError("Password too short!");
                    editTextPassword.requestFocus();
                } else {
                    buttonLogin.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    FirebaseAuth.getInstance()
                            .signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    buttonLogin.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);

                                    if (authResult.getUser().isEmailVerified()) {
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        authResult.getUser().sendEmailVerification();

                                        Toast.makeText(LoginActivity.this,
                                                "Verify email first.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    buttonLogin.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(LoginActivity.this,
                                            e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();

                if (email.isEmpty()) {
                    editTextEmail.setError("Required");
                    editTextEmail.requestFocus();
                } else if (!email.matches(Patterns.EMAIL_ADDRESS.pattern())) {
                    editTextEmail.setError("Invalid Email!");
                    editTextEmail.requestFocus();
                } else {
                    FirebaseAuth.getInstance()
                            .sendPasswordResetEmail(email)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(LoginActivity.this,
                                            "Password reset mail Sent.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this,
                                            e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        linearLayoutRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

