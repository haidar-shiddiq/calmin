package com.omellete.calminapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omellete.calminapp.R;
import com.omellete.calminapp.model.Music;

import java.util.ArrayList;

public class MusicAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Music> arrayList;
    private MediaPlayer mediaPlayer;
    private Boolean flag = true;
    String song,singer;
    MusicAdapter adapter;
    Dialog dialog;
    Handler mHandler;

    public MusicAdapter(Context context, int layout, ArrayList<Music> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
        this.adapter = this;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public void stopPlayer() {
        if (!flag) {
            mediaPlayer.stop();
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtName, txtSinger;
        ImageView ivPlay, ivStop, imgBg, ivMusic;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(layout, null);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtSinger = (TextView) convertView.findViewById(R.id.txtSinger);
            viewHolder.ivPlay = (ImageView) convertView.findViewById(R.id.ivPlay);
            viewHolder.ivStop = (ImageView) convertView.findViewById(R.id.ivStop);
            viewHolder.imgBg = (ImageView) convertView.findViewById(R.id.cardBackground);
            viewHolder.ivMusic = (ImageView) convertView.findViewById(R.id.ivMusic);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Music music = arrayList.get(position);
        dialog = new Dialog(convertView.getContext());
        mHandler = new Handler();

        viewHolder.txtName.setText(music.getName());
        viewHolder.txtSinger.setText(music.getSinger());
        Glide.with(viewHolder.imgBg.getContext())
                .load(music.getCardBackground())
                .into(viewHolder.imgBg);
        Glide.with(viewHolder.ivMusic.getContext())
                .load(music.getMusicLogo())
                .into(viewHolder.ivMusic);

        // play music
        viewHolder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    mediaPlayer = MediaPlayer.create(context, music.getSong());
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                    song = music.getName();
                    singer = music.getSinger();
                    alertPlaying(song,singer);
                    flag = false;
                } else {
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(context, music.getSong());
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                    song = music.getName();
                    singer = music.getSinger();
                    alertPlaying(song,singer);
                    flag = false;
                }
            }
        });

        // stop
        viewHolder.ivStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    flag = true;
                }
                viewHolder.ivPlay.setImageResource(R.drawable.ic_play);
            }
        });

        return convertView;
    }

    public void alertPlaying(String song, String singer) {
        dialog.setContentView(R.layout.alert_playing);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvSong = dialog.findViewById(R.id.tvSong);
        TextView tvSinger = dialog.findViewById(R.id.tvSingere);
        tvSong.setText(song);
        tvSinger.setText(singer);
        dialog.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },10000L);
    }
}
