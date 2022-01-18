package com.example.userregisterfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.collection.LLRBNode;

import es.dmoral.toasty.Toasty;

public class login extends AppCompatActivity {
    FirebaseAuth mauth;
    TextInputLayout email,password1;
    Button signin;
    ProgressBar probar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email= (TextInputLayout) findViewById(R.id.email);
        password1=(TextInputLayout) findViewById(R.id.password1);
        signin=(Button)findViewById(R.id.register);
        probar=(ProgressBar)findViewById(R.id.progressBar2);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                probar.setVisibility(View.VISIBLE);
                mauth = FirebaseAuth.getInstance();
                String femail = email.getEditText().getText().toString();
                String fpass = password1.getEditText().getText().toString();
                mauth.signInWithEmailAndPassword(femail,fpass).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            probar.setVisibility(View.INVISIBLE);
                            email.getEditText().setText("");
                            password1.getEditText().setText("");
                            startActivity(new Intent(login.this,dashboard.class));
                            finish();
                        }
                        else{
                            Toasty.error(login.this, "invalid credentials", Toasty.LENGTH_SHORT).show();
                            probar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }

    public void switch_register(View view){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}