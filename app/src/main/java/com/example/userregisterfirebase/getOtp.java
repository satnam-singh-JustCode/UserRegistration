package com.example.userregisterfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class getOtp extends AppCompatActivity {
    FirebaseAuth mAuth3;
    public static String otp_id;
    public static int counter =2;
    TextInputLayout entrotp;
    TextView number;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_otp);
        mAuth3 = FirebaseAuth.getInstance();
        entrotp = (TextInputLayout) findViewById(R.id.eNtErOtP);
        Button varify = (Button)findViewById(R.id.varify);
        Button getotp = (Button)findViewById(R.id.GETOTP);
        number = (TextView)findViewById(R.id.textView4);
        String full_num = getIntent().getStringExtra("full_no");
        String numberonly = getIntent().getStringExtra("numb");
        number.setText(numberonly);
        mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) { // THIS METHOD HELPS TO STORE THE CREDENTIALS IN "phoneAuthCredential" OBJECT AND HELPS US TO VARIFY USER AUTOMATICALLY.
                signInwithCrendential(phoneAuthCredential,numberonly);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
//                note.setText(e.toString());
//                note.setTextColor(Color.RED);
//                note.setVisibility(View.VISIBLE);
                Toast.makeText(getOtp.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                otp_id=s;
            }
        };
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthOptions options;
                options = PhoneAuthOptions.newBuilder(mAuth3)
                        .setPhoneNumber(full_num)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(getOtp.this)
                        .setCallbacks(mcallbacks) // SETTING CALLBACKS
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });
        varify.setOnClickListener(v -> {
            String otp = entrotp.getEditText().getText().toString();
            if(!otp.isEmpty() || !(otp.length()==6)){
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otp_id,otp); // varifyiing otp
                signInwithCrendential(credential,numberonly);
            }
        });
    }
    public void signInwithCrendential(PhoneAuthCredential phoneAuthCredential,String numberonly){
        mAuth3 .signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toasty.success(getApplicationContext(),"Varified "+numberonly,Toasty.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), dashboard.class));
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), ".....", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
    counter--;
        if(counter==1)
            Toasty.info(this, "Press again to Exit", Toasty.LENGTH_SHORT).show();
        if(counter==0)
        {
            startActivity(new Intent(this,otp.class));
            finish();
            //super.onBackPressed();
        } // super call the original predefined onBackPressed() function , which is used to shift to previous scrren.

    }

    public void back(View v){
        startActivity(new Intent(this,otp.class));
        finish();
    }
}