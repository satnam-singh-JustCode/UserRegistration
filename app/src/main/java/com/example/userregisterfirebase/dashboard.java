package com.example.userregisterfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

public class dashboard extends AppCompatActivity {
    FirebaseAuth mauth,mauth1;
    Button signout,varify;
    TextView textView;
    public void onStart() {
        super.onStart();
        mauth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mauth.getCurrentUser();
        if(currentUser == null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        mauth1 = FirebaseAuth.getInstance();
        signout = (Button)findViewById(R.id.signout);
//        varify = (Button)findViewById(R.id.varify);
//        textView =(TextView)findViewById(R.id.textView3);
//        String email = mauth1.getCurrentUser().getEmail();
//        textView.setText("Please Varify "+email);
//        if(!mauth1.getCurrentUser().isEmailVerified()){
//            textView.setVisibility(View.VISIBLE);
//            varify.setVisibility(View.VISIBLE);
//        }
//        if(mauth1.getCurrentUser().isEmailVerified()){
//            textView.setVisibility(View.GONE);
//            varify.setVisibility(View.GONE);
//        }
//        varify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mauth1.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            Toasty.success(getApplicationContext(),"Email send successfully...",Toasty.LENGTH_SHORT).show();
//                            textView.setVisibility(View.GONE);
//                            varify.setVisibility(View.GONE);
//                        }
//                        else{
//                            Toasty.error(getApplicationContext(),"Something went wrong!!!",Toasty.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mauth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }
}