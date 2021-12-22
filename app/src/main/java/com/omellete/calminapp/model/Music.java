package com.omellete.calminapp.model;

public class Music {
    private String name;
    private String singer;
    private int song;
    private int cardBackground;
    private int musicLogo;

    public Music(String name, String singer, int song, int cardBackground, int musicLogo) {
        this.name = name;
        this.singer = singer;
        this.song = song;
        this.cardBackground = cardBackground;
        this.musicLogo = musicLogo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getSong() {
        return song;
    }

    public void setSong(int song) {
        this.song = song;
    }

    public int getCardBackground() {
        return cardBackground;
    }

    public void setCardBackground(int cardBackground) {
        this.cardBackground = cardBackground;
    }

    public int getMusicLogo() {
        return musicLogo;
    }

    public void setMusicLogo(int musicLogo) {
        this.musicLogo = musicLogo;
    }
}