package com.db.voicecallingsinch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
   private EditText etEmail,etPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView txtBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        progressBar=findViewById(R.id.progressBar);
        mAuth=FirebaseAuth.getInstance();
        txtBtnRegister=findViewById(R.id.txtBtnRegister);

        txtBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        String email=etEmail.getText().toString().trim();
        String password=etPassword.getText().toString().trim();
        if (email.isEmpty()&&password.isEmpty()){
            Toast.makeText(LoginActivity.this,"Please enter credentials",Toast.LENGTH_SHORT);
        }
        else {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(LoginActivity.this,"UnSuccessful Login",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}