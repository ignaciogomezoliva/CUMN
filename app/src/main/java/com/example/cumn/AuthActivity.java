package com.example.cumn;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity  {
    private Button loginButton;
    private Button singUpButton;
    private String email;
    private String password;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        loginButton = findViewById(R.id.loginButton);
        singUpButton = findViewById(R.id.signUpButtonA);
        fAuth = FirebaseAuth.getInstance();


        //Setup
        setUp();
    }
    private void setUp(){

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailText = (EditText)findViewById(R.id.emailText);
                EditText passText = (EditText)findViewById(R.id.passwordText);
                email = emailText.getText().toString();
                password = passText.getText().toString();
                if(TextUtils.isEmpty(email)){
                    emailText.setError("Email is Required.");
                    return;
                }
                if(password.length()<6){
                    passText.setError("Password length must be 6 or more.");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener() {
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            changeActivity();
                        } else {
                            System.out.println(task.getException());
                            showAlert();
                        }
                    }
                });

            }
        });

        singUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
    private void showAlert(){
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setTitle("Error");
       builder.setMessage("Error 45111");
       builder.setPositiveButton("Okay", null);
       AlertDialog dialog = builder.create();
       dialog.show();
    }

    private void changeActivity(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}