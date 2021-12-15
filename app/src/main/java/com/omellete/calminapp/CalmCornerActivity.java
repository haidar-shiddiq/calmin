package com.omellete.calminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.omellete.calminapp.adapter.MusicAdapter;
import com.omellete.calminapp.model.Music;

import java.util.ArrayList;

public class CalmCornerActivity extends AppCompatActivity {

    private ArrayList<Music> arrayList;
    private MusicAdapter adapter;
    private ListView songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calm_corner);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#2196F3"));
        actionBar.setBackgroundDrawable(colorDrawable);
        setTitle("Zona Tenang");

        songList = findViewById(R.id.songList);
        arrayList = new ArrayList<>();
        arrayList.add(new Music("Raining","Mixkit",R.raw.mixkit_rain,R.drawable.bg_rain,R.drawable.ic_forum));
        arrayList.add(new Music("Forest Ambience","Mixkit",R.raw.mixkit_forest,R.drawable.bg_forest,R.drawable.ic_moon));

        adapter = new MusicAdapter(this, R.layout.item_music, arrayList);
        songList.setAdapter(adapter);
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
        adapter.stopPlayer();
        startActivity(new Intent(CalmCornerActivity.this, LandingActivity.class));
    }
}