package com.example.faraid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import com.example.faraid.Type.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private ProgressDialog loadingBar;
    private EditText mUsername, mEmail, mPassword, mNophone;
    private Button btnRegister;

    private String lvl;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private String mNamaUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        loadingBar = new ProgressDialog(this);

        mUsername = findViewById(R.id.editTextName);
        mEmail = findViewById(R.id.editTextEmail);
        mNophone = findViewById(R.id.editTextMobile);
        mPassword = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.cirRegisterButton);

        SendUserToLogin();
    }

    private void SendUserToLogin() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = mUsername.getText().toString();
                final String email = mEmail.getText().toString();
                final String password   = mPassword.getText().toString();
                final String nophone = mNophone.getText().toString();
                final String lvl = "1";
                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(nophone)){
                    Toast.makeText(RegisterActivity.this,"Data tidak boleh kosong...",Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(username)){
                    mUsername.setError("Nama tidak boleh kosong");
                    mUsername.requestFocus();

                }else  if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email tidak boleh kosong");
                    mEmail.requestFocus();

                }else if (TextUtils.isEmpty(nophone)){
                    mNophone.setError("Masukan No Ponsel");
                    mNophone.requestFocus();

                }else if (TextUtils.isEmpty(password)){
                    mPassword.setError("Masukan Password");
                    mPassword.requestFocus();

                }else {
                    loadingBar.setTitle("Creating An Account");
                    loadingBar.setMessage("Please wait...");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                String message= task.getException().toString();
                                Toast.makeText(RegisterActivity.this,"Error :"+message,Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else {
                                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                    mNamaUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    loadingBar.dismiss();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                } else {
                                    loadingBar.dismiss();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                }
                                RootRef = FirebaseDatabase.getInstance().getReference("Users").child(mNamaUser);
                                User user= new User(username, email, password,nophone, lvl);
                                newUserLvl();
                                RootRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this,"Berhasil daftar",Toast.LENGTH_SHORT).show();
                                        SendToLogin();

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
    private void newUserLvl() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        final DatabaseReference UserId = databaseReference.child(mAuth.getCurrentUser().getUid());
        UserId.child("lvl").setValue(1);

    }

    private void SendToLogin() {
        Intent logInten = new Intent(RegisterActivity.this, LoginActivity.class);
        logInten.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logInten);


}

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

        public void onLoginClick(View view){
            startActivity(new Intent(this,LoginActivity.class));
            overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);


    }
}
