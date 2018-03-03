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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    EditText emailiduser,pwduser;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

       emailiduser = findViewById(R.id.emailids);
       pwduser = findViewById(R.id.pwds);
       progressBar = (ProgressBar)findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.button).setOnClickListener(this);
    }
        private void registeruser(){
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
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"User registered Successfully",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(LoginActivity.this,SigninActivity.class);
                        startActivity(in);
                    }
                    else if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(),"User Already Registered!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

       }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.button:
              registeruser();
                break;



        }
    }
}
