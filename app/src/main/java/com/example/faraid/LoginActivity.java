package com.example.faraid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faraid.Type.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;

    private ProgressDialog loadingBar;
    private EditText mEmail, mPassword;
    private Button btnLogin;
    private TextView tvRegister,tvForgotPass;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);


        session = new Session(this);
        if (session.loggedIn()){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mEmail = findViewById(R.id.editTextEmail);
        mPassword = findViewById(R.id.editTextPassword);
        tvRegister = findViewById(R.id.register);
        tvForgotPass = findViewById(R.id.forgot);
        btnLogin = findViewById(R.id.cirRegisterButton);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToRegisterActivity();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserToLogin();
            }
        });
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToForgotPassword();
            }
        });
    }

    private void AllowUserToLogin() {
        String email = mEmail.getText().toString();
        String password  = mPassword.getText().toString();
        mAuth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(email)){
            mEmail.setError("Username tidak boleh kosong");
            mEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            mPassword.setError("Password tidak boleh kosong");
            mPassword.requestFocus();
        }
        else if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Data tidak boleh kosong",Toast.LENGTH_SHORT).show();
        }
        else {
            final ProgressDialog loadingBar = new ProgressDialog(this);
            loadingBar.setTitle("Logged In");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Gagal Login",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    } else {
                        loadingBar.dismiss();
                        session.setLoggedin(true);
                        SendToMainActivity();
                    }
                }
            });
        }
    }

    /*Buat Halaman Forgot Password */
    private void SendToForgotPassword() {
//        Intent ForgotPass = new Intent(LoginActivity.this,ForgotPasswordActivity);
//        startActivity(ForgotPass);
    }

    private void SendToRegisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void SendToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    public void onLoginClick(View View) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}


