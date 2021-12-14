package com.omellete.calminapp.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omellete.calminapp.GroupSharingActivity;
import com.omellete.calminapp.R;

public class ProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    String name,uid;
    TextView tvName, tvUid,tvEmail;
    FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        uid=currentUser.getUid();
//        GroupSharingActivity group = new GroupSharingActivity();
//        name = group.showUserDname();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference();
        String userKey = uid;
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

        tvName = view.findViewById(R.id.profileName);
        tvUid = view.findViewById(R.id.profileUid);
        tvEmail = view.findViewById(R.id.profileEmail);
        tvUid.setText(uid);
        tvEmail.setText(currentUser.getEmail());

        return view;
    }
}