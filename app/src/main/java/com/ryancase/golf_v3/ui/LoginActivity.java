package com.ryancase.golf_v3.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ryancase.golf_v3.R;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton, signUpButton, signInButton;
    private EditText passwordText, emailText;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //Logged in
                    loadMainActivity();
                    Log.d("Already logged in", "");
                } else {
                    //Not logged in
                }
            }
        };

        passwordText = (EditText) findViewById(R.id.passwordText);
        emailText = (EditText) findViewById(R.id.emailText);

        signInButton = (Button) findViewById(R.id.signInButton);
        if (signInButton != null) {
            signInButton.setVisibility(View.VISIBLE);
        }
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(emailText.getText().toString(), passwordText.getText().toString());
            }
        });

        signUpButton = (Button) findViewById(R.id.signUpButton);
        if (signUpButton != null) {
            signUpButton.setVisibility(View.VISIBLE);
        }
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(emailText.getText().toString(), passwordText.getText().toString());
            }
        });
    }

    private void login(String email, String password) {
        if (!validateForm()) {
            Log.d("Invalid form", "");
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("", "signInUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w("", "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Login Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void signUp(String email, String password) {
        if (!validateForm()) {
            Log.d("Invalid form", "");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w("", "createWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Login Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void loadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailText.setError("Required.");
            valid = false;
        } else {
            emailText.setError(null);
        }

        String password = passwordText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordText.setError("Required.");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }


}
