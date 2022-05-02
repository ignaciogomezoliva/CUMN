package com.example.cumn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private Button loginButton;
    private Button signUpButton;
    private String username;
    private String email;
    private String password;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        loginButton = findViewById(R.id.loginButtonR);
        signUpButton = findViewById(R.id.signUpButton);
    }

    private void setUp(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Login", 0).apply();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText usernameText = (EditText)findViewById(R.id.usernameText);
                EditText emailText = (EditText)findViewById(R.id.emailTextR);
                EditText passText = (EditText)findViewById(R.id.passwordTextR);
                username = usernameText.getText().toString();
                email = emailText.getText().toString();
                password = passText.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener() {
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                changeActivity(true);
                                editor.putInt("Login", 1).apply();
                            } else {
                                System.out.println(task.getException());
                                System.out.println(email);
                                showAlert();
                            }
                        }
                    });
                }
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
        builder.setMessage("Error 45111");
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