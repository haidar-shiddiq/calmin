package com.omellete.calminapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.omellete.calminapp.LandingActivity;
import com.omellete.calminapp.R;
import com.omellete.calminapp.ResetPasswordActivity;

public class SupportFragment extends Fragment {

    Button btn_sendEmail,btn_reset,btn_masukan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        btn_sendEmail = view.findViewById(R.id.sendEmail);
        btn_masukan = view.findViewById(R.id.button_support_masukan);
        btn_reset = view.findViewById(R.id.button_support_reset);

        btn_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + "Permintaan Support Calm.in"+ "&body=" + "" + "&to=" + "cs.calmin@gmail.com");
                mailIntent.setData(data);
                startActivity(Intent.createChooser(mailIntent, "Send mail..."));
            }
        });

        btn_masukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + "Masukan untuk Calm.in"+ "&body=" + "" + "&to=" + "cs.calmin@gmail.com");
                mailIntent.setData(data);
                startActivity(Intent.createChooser(mailIntent, "Send mail..."));
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ResetPasswordActivity.class));
            }
        });

        return view;
    }

}