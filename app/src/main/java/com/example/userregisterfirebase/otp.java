package com.example.userregisterfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class otp extends AppCompatActivity {
TextInputLayout number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
        Button login = (Button)findViewById(R.id.button2);
//        EditText cc = (EditText)findViewById(R.id.editTextPhone2);
         number = (TextInputLayout) findViewById(R.id.editTextPhone);
        login.setOnClickListener(v -> {
//            String cc1 = cc.getText().toString();
            String num1 =  number.getEditText().getText().toString();
            if(num1.length()!=10){
                Toasty.error(getApplicationContext(),"Invalid Number!!!",Toasty.LENGTH_SHORT).show();
                return ;
            }
            else {
                String fullnum = "+" + "91" + "" + num1;
                Intent intent = new Intent(getApplicationContext(), getOtp.class);
                intent.putExtra("full_no", fullnum);
                intent.putExtra("numb", num1);
                startActivity(intent);
                finish();
            }

        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    public void back(View v){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}