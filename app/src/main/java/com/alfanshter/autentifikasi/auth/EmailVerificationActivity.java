package com.alfanshter.autentifikasi.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alfanshter.autentifikasi.MainActivity;
import com.alfanshter.autentifikasi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText edtemail,edtpassword;
    Button btnlogin,btnregister;
    String email,password;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        firebaseAuth = FirebaseAuth.getInstance();

        edtemail = findViewById(R.id.edt_email);
        edtpassword = findViewById(R.id.edt_password);
        btnlogin = findViewById(R.id.btn_login);
        btnregister = findViewById(R.id.btn_register);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtemail.getText().toString().trim();
                String password = edtpassword.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent myIntent = new Intent(EmailVerificationActivity.this, MainActivity.class);
                            startActivity(myIntent);
                        }else {
                            Toast.makeText(EmailVerificationActivity.this,"gagal",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(EmailVerificationActivity.this, RegisterActivity.class);
                startActivity(myIntent);
            }
        });
    }
}