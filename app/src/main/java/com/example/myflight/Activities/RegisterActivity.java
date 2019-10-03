package com.example.myflight.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myflight.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, emailId, password;
    private Button btnReg;
    private TextView tvSignIn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private CircleImageView circleImageView;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    String uName, uPasswd, uEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    // Upload data to the database
                    progressDialog.setMessage("Registering User. Please wait...");
                    progressDialog.show();

                    final String user_email = emailId.getText().toString().trim();
                    final String user_password = password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()) {
                                // sendEmailVerification();
                                sendUserData(user_email,user_password);
                                firebaseAuth.signOut();
                                Toast.makeText(RegisterActivity.this, "Registration Successful, Upload Complete!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                circleImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupUIViews() {
        name = findViewById(R.id.regName);
        password = findViewById((R.id.regPassword));
        emailId = findViewById(R.id.regMail);
        btnReg = findViewById(R.id.regBtn);
        tvSignIn = findViewById(R.id.textViewReg);
        circleImageView = findViewById(R.id.regUserPhoto);
    }

    private boolean validate() {
        boolean result = false;

        uName = name.getText().toString().trim();
        uPasswd = password.getText().toString();
        uEmail = emailId.getText().toString();

        if (uName.isEmpty() || uPasswd.isEmpty() || uEmail.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }

//    private void sendEmailVerification() {
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        if (firebaseUser != null) {
//            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()) {
//                        sendUserData();
//                        Toast.makeText(RegisterActivity.this, "Registration Successful, Verification mail sent!", Toast.LENGTH_SHORT).show();
//                        firebaseAuth.signOut();
//                        finish();
//                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                    } else {
//                        Toast.makeText(RegisterActivity.this, "Verification mail not sent!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }

    private void sendUserData(String email, String pass) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Map<String,String> data = new HashMap<>();
        data.put("email",email);
        data.put("pass",pass);
        DatabaseReference myRef = firebaseDatabase.getReference().child(firebaseAuth.getUid());
        myRef.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(RegisterActivity.this, "Done", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
