package com.omellete.calminapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omellete.calminapp.databinding.ActivityProfileBinding;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    String name, uid;
    FirebaseDatabase firebaseDatabase;
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle("Profil");

            (binding.appBar).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    int min_height = ViewCompat.getMinimumHeight(binding.collapsingToolbar) * 2;
                    float scale = (float) (min_height + verticalOffset) / min_height;
                    binding.profileCard.setScaleX(scale >= 0 ? scale : 0);
                    binding.profileCard.setScaleY(scale >= 0 ? scale : 0);
                    if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                        // If collapsed, then do this
                        binding.calminProfile.setVisibility(View.VISIBLE);
                    } else if (verticalOffset == 0) {
                        // If expanded, then do this
                        binding.calminProfile.setVisibility(View.GONE);
                    }
                }
            });

        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.white));

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        uid = currentUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference();
        String userKey = uid;
        ref.child("Users").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("username").getValue(String.class);
                Log.d(TAG, "Nameeee: " + name);
                binding.profileName.setText(name);
                binding.tvNamee.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        binding.profileUid.setText(uid);
        binding.profileEmail.setText(currentUser.getEmail());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this, LandingActivity.class));
    }
}