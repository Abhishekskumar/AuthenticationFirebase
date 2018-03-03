package com.smarproj.abhishek.smartmed;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    ProgressBar progressBar;
    EditText emailiduser,pwduser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        emailiduser = findViewById(R.id.emailidid);
        pwduser = findViewById(R.id.pwdid);
    }

    private void userlogin(){

        String email = emailiduser.getText().toString().trim();
        String password = pwduser.getText().toString().trim();

        if(email.isEmpty())
        {
            emailiduser.setError("Enter Email ID");
            emailiduser.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailiduser.setError("Please Enter a valid Email ID");
            emailiduser.requestFocus();
            return;
        }
        if(password.isEmpty()){
            pwduser.setError("Enter Password");
            pwduser.requestFocus();
            return;
        }

        if(password.length()<6){
            pwduser.setError("Minimum password length is 6 characters");
            pwduser.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()) {
                   Intent intent = new Intent(SigninActivity.this,MeddetailsActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button2:
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                break;

            case R.id.button:
                userlogin();
                break;
        }
    }
}
