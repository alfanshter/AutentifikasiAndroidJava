package com.alfanshter.autentifikasi.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alfanshter.autentifikasi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText edtemail,edtpassword;
    Button btnlogin,btnrefresh;
    String email,password;
    String userid;
    TextView txtstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();

        edtemail = findViewById(R.id.edt_email);
        edtpassword = findViewById(R.id.edt_password);
        btnlogin = findViewById(R.id.btn_login);
        btnrefresh = findViewById(R.id.btn_refresh);
        txtstatus = findViewById(R.id.txt_status);

        updatestatus();
        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().getCurrentUser()
                        .reload()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                updatestatus();

                            }
                        });
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtemail.getText().toString().trim();
                password = edtpassword.getText().toString().trim();
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((task -> {
                    if (task.isSuccessful()){
                        //send verification email
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(RegisterActivity.this,"email terkirim",Toast.LENGTH_SHORT);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this,"gagal",Toast.LENGTH_SHORT);
                            }
                        });
                        //==========

                        userid = firebaseAuth.getCurrentUser().getUid();
                        Toast.makeText(RegisterActivity.this,"Sukses",Toast.LENGTH_SHORT);
                    }else {
                        Toast.makeText(RegisterActivity.this,"gagal",Toast.LENGTH_SHORT);
                    }
                }));

            }
        });


    }

    private void updatestatus (){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        txtstatus.setText(new StringBuilder("Status : ").append(String.valueOf(user.isEmailVerified())));

    }
}