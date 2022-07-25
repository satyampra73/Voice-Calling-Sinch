package com.db.voicecallingsinch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail,etName,etPassword;
    private Button btnRegister;
    private ProgressBar progressBar;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etEmail=findViewById(R.id.etEmail);
        etName=findViewById(R.id.etName);
        etPassword=findViewById(R.id.etPassword);
        btnRegister =findViewById(R.id.btnRegister);
        progressBar=findViewById(R.id.progressBar);
        auth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference().child("users");


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register();
            }
        });
    }

    private void register() {
        String name=etName.getText().toString().trim();
         String email=etEmail.getText().toString().trim();
         String password=etPassword.getText().toString().trim();
        if (name.isEmpty()&&email.isEmpty()&&password.isEmpty()){
            Toast.makeText(RegisterActivity.this,"Please Enter Credentials",Toast.LENGTH_SHORT).show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser=auth.getCurrentUser();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("name", name);
                        hashMap.put("email", email);
                        hashMap.put("id", firebaseUser.getUid());
                        hashMap.put("password", password);

                        reference.child(firebaseUser.getUid()).setValue(hashMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(RegisterActivity.this,"User Created Successfully",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(RegisterActivity.this, "User Registration Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });
        }
    }
}