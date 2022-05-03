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
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private Button loginButton;
    private Button signUpButton;
    private String username;
    private String email;
    private String password;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginButton = findViewById(R.id.loginButtonR);
        signUpButton = findViewById(R.id.signUpButton);
        fAuth = FirebaseAuth.getInstance();
        setUp();
    }

    private void setUp(){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText usernameText = (EditText)findViewById(R.id.usernameText);
                EditText emailText = (EditText)findViewById(R.id.emailTextR);
                EditText passText = (EditText)findViewById(R.id.passwordTextR);
                username = usernameText.getText().toString();
                email = emailText.getText().toString();
                password = passText.getText().toString();

                if(TextUtils.isEmpty(username)){
                    usernameText.setError(getString(R.string.error_username));
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    emailText.setError(getString(R.string.error_email));
                    return;
                }
                if(password.length()<6){
                    passText.setError(getString(R.string.error_password));
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener() {
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            changeActivity(true);
                        } else {
                            System.out.println(task.getException());
                            System.out.println(email);
                            showAlert();
                        }
                    }
                });

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(false);
            }
        });
    }
    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(getString(R.string.error_auth));
        builder.setPositiveButton("Okay", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void changeActivity(Boolean election){
        if (election){
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }

    }
}