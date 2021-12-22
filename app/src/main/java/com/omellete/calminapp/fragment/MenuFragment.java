package com.omellete.calminapp.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omellete.calminapp.CalmCornerActivity;
import com.omellete.calminapp.GroupSharingActivity;
import com.omellete.calminapp.ProfileActivity;
import com.omellete.calminapp.R;
import com.omellete.calminapp.SelfImproveActivity;
import com.omellete.calminapp.SupportActivity;

public class MenuFragment extends Fragment {

    CardView fabChat, fabCalm, fabSelf;
    ImageButton imgProfile, imgSupport;
    TextView tvName;
    String name;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        fabChat = view.findViewById(R.id.fabChat);
        fabCalm = view.findViewById(R.id.fabCalm);
        fabSelf = view.findViewById(R.id.fabSelf);
        imgProfile = view.findViewById(R.id.imgProfile);
        imgSupport = view.findViewById(R.id.imgSupport);
        tvName = view.findViewById(R.id.tvName);

        fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), GroupSharingActivity.class));
            }
        });
        fabCalm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CalmCornerActivity.class));
            }
        });
        fabSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SelfImproveActivity.class));
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });
        imgSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SupportActivity.class));
            }
        });

        getName();


        return view;
    }

    public void getName() {
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String key = currentUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference();
        String userKey = key;
        ref.child("Users").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("username").getValue(String.class);
                Log.d(TAG, "Nameeee: " + name);
                tvName.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}