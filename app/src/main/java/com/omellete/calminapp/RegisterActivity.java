package com.omellete.calminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.omellete.calminapp.databinding.ActivityRegisterBinding;
import com.omellete.calminapp.model.User;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        auth = FirebaseAuth.getInstance();

        binding.textLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Mohon Tunggu Sebentar...");
                pd.show();

                String username = binding.username.getEditText().getText().toString();
                String email = binding.email.getEditText().getText().toString();
                String password = binding.password.getEditText().getText().toString();
                String confirmPw = binding.confirmPassword.getEditText().getText().toString();

                if (TextUtils.isEmpty(username)||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)
                        ||TextUtils.isEmpty(confirmPw)){
                    Toast.makeText(RegisterActivity.this, "Mohon isi semua field", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }else if(password.length()<6){
                    Toast.makeText(RegisterActivity.this, "Password minimal terdiri dari 6 karakter", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }else if(!password.equals(confirmPw)){
                    Toast.makeText(RegisterActivity.this, "Konfirmasi password dengan benar", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }else{
                    register(username,email,password);
                }
            }
        });
    }

    private void register(String username, String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();
                            User u = new User();
                            u.setName(username);
                            u.setuid(userid);
                            u.setEmail(email);
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username.toLowerCase());
                            hashMap.put("email", email);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, GroupSharingActivity.class);
                                        intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent. FLAG_ACTIVITY_NEW_TASK) ;
                                        startActivity(intent);
                                    }
                                }
                            });
                        }else{
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, "Maaf, anda tidak bisa register dengan username atau email ini", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}