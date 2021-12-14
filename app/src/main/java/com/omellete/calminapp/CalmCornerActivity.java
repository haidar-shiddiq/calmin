package com.omellete.calminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        songList = findViewById(R.id.songList);
        arrayList = new ArrayList<>();
        arrayList.add(new Music("Raining","Mixkit",R.raw.mixkit_rain,R.drawable.bg_rain,R.drawable.ic_forum));
        arrayList.add(new Music("Forest Ambience","Mixkit",R.raw.mixkit_forest,R.drawable.bg_forest,R.drawable.ic_nature_people));

        adapter = new MusicAdapter(this, R.layout.item_music, arrayList);
        songList.setAdapter(adapter);
    }
}