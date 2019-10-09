package com.incapp.onlinelearning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    Button buttonRegister;
    ProgressBar progressBar;
    LinearLayout linearLayoutLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        editTextName = (EditText) findViewById(R.id.editText_name);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.editText_confirm_password);
        buttonRegister = (Button) findViewById(R.id.button_register);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        linearLayoutLogin = (LinearLayout) findViewById(R.id.linearLayout_login);

        linearLayoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (name.isEmpty()) {
                    editTextName.setError("Required!");
                    editTextName.requestFocus();
                } else if (email.isEmpty()) {
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
                } else if (!password.equals(confirmPassword)) {
                    editTextConfirmPassword.setError("Password too short ");
                    editTextConfirmPassword.requestFocus();
                } else {
                    buttonRegister.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    buttonRegister.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);

                                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            .build();

                                    authResult.getUser().updateProfile(request);

                                    authResult.getUser().sendEmailVerification();

                                    Toast.makeText(RegisterActivity.this, "Verify email and login.", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    buttonRegister.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(RegisterActivity.this,
                                            e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
