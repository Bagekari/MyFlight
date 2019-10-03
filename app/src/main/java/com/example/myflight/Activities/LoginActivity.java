package com.example.myflight.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myflight.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    Button btnLogin;
    TextView tvReg, tvInfo;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private int counter = 5;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.loginBtn);
        tvReg = findViewById(R.id.textViewLogin);
        tvInfo = findViewById(R.id.textViewWarning);
        TextView forgotPassword = findViewById(R.id.tvForgotPassword);



        String message = getString(R.string.attempt_string);
        message += 5;
        tvInfo.setText(message);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, NavigationDrawerActivity.class));
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Logging In. Please wait...");
                progressDialog.show();

                String user_email = email.getText().toString();
                String user_password = password.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            checkEmailVerification();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            counter--;
                            String message = getString(R.string.attempt_string);
                            message += counter;
                            tvInfo.setText(message);
                            if (counter == 0)
                                btnLogin.setEnabled(false);
                        }
                    }
                });
            }
        });

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, PasswordActivity.class));
            }
        });

    }

    private void checkEmailVerification() {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        boolean emailFlag = Objects.requireNonNull(firebaseUser).isEmailVerified();

        startActivity(new Intent(LoginActivity.this, NavigationDrawerActivity.class));

//        if (emailFlag) {
//            finish();
//            startActivity(new Intent(LoginActivity.this, NavigationDrawerActivity.class));
//        } else {
//            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
//            firebaseAuth.signOut();
//        }
    }
}
