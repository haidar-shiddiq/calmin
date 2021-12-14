package com.omellete.calminapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.omellete.calminapp.CalmCornerActivity;
import com.omellete.calminapp.GroupSharingActivity;
import com.omellete.calminapp.ProfileActivity;
import com.omellete.calminapp.R;
import com.omellete.calminapp.SelfImproveActivity;
import com.omellete.calminapp.SupportActivity;

public class MenuFragment extends Fragment {

    FloatingActionButton fabChat,fabCalm,fabSelf,fabProfile,fabSupport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        fabChat = view.findViewById(R.id.fabChat);
        fabCalm = view.findViewById(R.id.fabCalm);
        fabSelf = view.findViewById(R.id.fabSelf);
        fabProfile = view.findViewById(R.id.fabProfile);
        fabSupport = view.findViewById(R.id.fabSupport);
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
        fabProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });
        fabSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SupportActivity.class));
            }
        });

        return view;
    }
}