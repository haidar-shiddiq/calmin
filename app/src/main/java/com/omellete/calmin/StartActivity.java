package com.omellete.calmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.omellete.calmin.databinding.ActivityRegisterBinding;
import com.omellete.calmin.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    ActivityStartBinding binding;

    @Override
    protected void onStart () {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // redirect if user is not null
        if (firebaseUser != null) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonStartLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,LoginActivity.class));
            }
        });
        binding.buttonStartRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            }
        });
    }
}