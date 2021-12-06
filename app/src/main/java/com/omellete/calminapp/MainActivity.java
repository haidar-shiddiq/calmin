package com.omellete.calminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FragmentManager fragmentManager;
    Button btnSkip;

    @Override
    protected void onStart () {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // redirect if user is not null
        if (firebaseUser != null) {
            startActivity(new Intent(MainActivity.this, LandingActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        btnSkip = findViewById(R.id.btn_skip);

        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataForOnBoarding());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container_main, paperOnboardingFragment);
        fragmentTransaction.commit();

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });


    }

    private ArrayList<PaperOnboardingPage> getDataForOnBoarding() {
        PaperOnboardingPage src1 = new PaperOnboardingPage("Selamat Datang","Swipe untuk memulai",
                Color.parseColor("#AADE87"),R.drawable.calmin,R.drawable.calmin_mini);
        PaperOnboardingPage src2 = new PaperOnboardingPage("Kembangkan Diri","Buat dirimu menjadi orang yang lebih tenang",
                Color.parseColor("#F57B51"),R.drawable.ic_self_improvement_big,R.drawable.ic_self_improvement_big);
        PaperOnboardingPage src3 = new PaperOnboardingPage("Zona Tenang","Nikmati ketenangan",
                Color.parseColor("#44D6E9"),R.drawable.ic_nature_people_big,R.drawable.ic_nature_people_big);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(src1);
        elements.add(src2);
        elements.add(src3);
        return elements;
    }
}