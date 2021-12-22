package com.omellete.calminapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FragmentManager fragmentManager;
    TextView btnSkip;

    @Override
    protected void onStart() {
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
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


    }

    private ArrayList<PaperOnboardingPage> getDataForOnBoarding() {
        PaperOnboardingPage src1 = new PaperOnboardingPage("Selamat Datang", "Swipe untuk memulai",
                Color.parseColor("#AADE87"), R.drawable.calmin, R.drawable.calmin_mini);
        PaperOnboardingPage src2 = new PaperOnboardingPage("Ruang Cakap", "Bagikan ceritamu atau tanggapi cerita orang lain",
                Color.parseColor("#FFC107"), R.drawable.ic_forum, R.drawable.ic_forum);
        PaperOnboardingPage src3 = new PaperOnboardingPage("Kembangkan Diri", "Tambah wawasanmu dengan artikel mengenai kesehatan mental",
                Color.parseColor("#F57B51"), R.drawable.ic_mental_health, R.drawable.ic_mental_health);
        PaperOnboardingPage src4 = new PaperOnboardingPage("Zona Tenang", "Nikmati musik yang menenangkan",
                Color.parseColor("#44D6E9"), R.drawable.ic_moon, R.drawable.ic_moon);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(src1);
        elements.add(src2);
        elements.add(src3);
        elements.add(src4);
        return elements;
    }
}