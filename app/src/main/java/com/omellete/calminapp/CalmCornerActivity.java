package com.omellete.calminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
        arrayList.add(new Music("Drunk","Keshi",R.raw.keshi_drunk));
        arrayList.add(new Music("Baka Mitai","Chris Nostalgic",R.raw.baka_mitai));

        adapter = new MusicAdapter(this, R.layout.item_music, arrayList);
        songList.setAdapter(adapter);
    }
}