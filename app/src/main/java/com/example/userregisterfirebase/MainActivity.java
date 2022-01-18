package com.example.userregisterfirebase;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
FirebaseAuth mauth,mauth1;
FirebaseDatabase db;
TextInputLayout email,password1,password2,name;
GoogleSignInClient msigninclient;
Button register,varifyMail;
ProgressBar progressBar;
public class userdetails{
    public String userName;
    public String userEmail;
    public userdetails(){ } // we have to define a default constructor (compulsary)
    public userdetails(String nameo,String emailo){
        this.userName=nameo;
        this.userEmail=emailo;
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth1 = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        email= findViewById(R.id.email);
        password1=findViewById(R.id.password1);
        password2= findViewById(R.id.password2);
        ImageView google = findViewById(R.id.imageView2);
        name = findViewById(R.id.name);
        register=findViewById(R.id.register);
        progressBar=findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                mauth = FirebaseAuth.getInstance();
                String remail=email.getEditText().getText().toString();
                String rpassword=password1.getEditText().getText().toString();
                String rrepassword=password2.getEditText().getText().toString();
                String namef = name.getEditText().getText().toString();
                if(remail.isEmpty()){
                    Toasty.warning(getApplicationContext(),"Email Required!!",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
//                if(!remail.contains("@gmail.com")){
//                    Toasty.warning(getApplicationContext(),"Invalid id!!",Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.INVISIBLE);
//                    return;
//                }
                if(rpassword.isEmpty()){
                    Toasty.warning(getApplicationContext(),"Password Required!!",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(rpassword.length()<7){
                    Toasty.warning(getApplicationContext(),"Password must have at least 7 characters",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(rpassword.length()>8){
                    Toasty.warning(getApplicationContext(),"Password must have at most 8 characters",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(!rrepassword.equals(rpassword)) {
                    Toasty.warning(getApplicationContext(),"Both password must be same!",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                else{
                    mauth.createUserWithEmailAndPassword(remail,rpassword).addOnCompleteListener(MainActivity.this, task -> {
                        if(task.isSuccessful()){
                                userdetails user = new userdetails(namef, remail);
                                db.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> taskotherEntity) {
                                        if (taskotherEntity.isSuccessful()) {
                                            Toasty.success(MainActivity.this, "Successfully Registered...", Toasty.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            email.getEditText().setText("");
                                            password1.getEditText().setText("");
                                            startActivity(new Intent(MainActivity.this, login.class));
                                            finish();
                                        } else {
                                            Toast.makeText(MainActivity.this, "Registration Failed...name", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Registration Failed...", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }

            }
        });
//        mAuth = FirebaseAuth.getInstance();
//        signinbtn = (SignInButton) findViewById(R.id.a);
//        progressBar = (ProgressBar)findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.INVISIBLE);
        //-------------------------------------------------------------------------------------------------------
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build(); // CREATING THE REQUEST
//        msigninclient = GoogleSignIn.getClient(this, gso); // PASSING THE REQUEST TO THE GOOGLE.DE
        //--------------------------------------------------------------------------------------------------
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                resultLauncher.launch(new Intent(msigninclient.getSignInIntent()));
            }
        });
    }
    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) ;
            Intent intent = result.getData();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                progressBar.setVisibility(View.INVISIBLE);
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, "error :- " + e, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    });
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mauth1.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(getApplicationContext(),dashboard.class));
                    finish(); }
                else
                    Toast.makeText(MainActivity.this, "fail..", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void switch_login(View v){
        startActivity(new Intent(this,login.class));
        finish();
    }
    public void otp(View v){
        startActivity(new Intent(this,otp.class));
        finish();
    }
}