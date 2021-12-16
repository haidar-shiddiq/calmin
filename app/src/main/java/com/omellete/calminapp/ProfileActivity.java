package com.omellete.calminapp;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
    Dialog dialog, dialogSuccess;
    String oldEmail, newEmail, password;

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
                    binding.calminProfile.setVisibility(View.VISIBLE);
                } else if (verticalOffset == 0) {
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

        dialog = new Dialog(this);
        dialogSuccess = new Dialog(this);
        binding.buttonGanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeEmailDialog();
            }
        });

    }

    public void changeEmailDialog() {
        dialog.setContentView(R.layout.alert_email_change);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText oldEmailEdit, newEmailEdit, passwordEdit;
        oldEmailEdit = dialog.findViewById(R.id.editTextTextEmailAddress);
        newEmailEdit = dialog.findViewById(R.id.editTextTextNewEmailAddress);
        passwordEdit = dialog.findViewById(R.id.editTextTextPassword);
        Button ganti = dialog.findViewById(R.id.btnGanti);
        ImageButton close = dialog.findViewById(R.id.btnCLose);
        dialog.show();

        ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = passwordEdit.getText().toString();
                oldEmail = oldEmailEdit.getText().toString();
                newEmail = newEmailEdit.getText().toString();
                Log.d(TAG, "apakah masuk" + password + oldEmail + newEmail);
                if (TextUtils.isEmpty(oldEmail) || TextUtils.isEmpty(password) || TextUtils.isEmpty(newEmail)) {
                    Toast.makeText(ProfileActivity.this, "Mohon isi semua Field", Toast.LENGTH_SHORT).show();
                } else {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(oldEmail, password);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d(TAG, "User re-authenticated.");
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    user.updateEmail(newEmail)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User email address updated.");
                                                        dialog.dismiss();
                                                        successChangeEmail();
                                                    }
                                                }
                                            });
                                }
                            });
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void successChangeEmail() {
        dialogSuccess.setContentView(R.layout.alert_success_reset);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView success, successDialog;
        success = dialogSuccess.findViewById(R.id.tvSuccessReset);
        successDialog = dialogSuccess.findViewById(R.id.tvSuccessDialog);
        success.setText("BERHASIL");
        successDialog.setText("Email kamu berhasil diganti");
        dialogSuccess.show();
        ImageButton close = dialogSuccess.findViewById(R.id.btnCLoseR);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSuccess.dismiss();
                finish();
                startActivity(getIntent());
            }
        });
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