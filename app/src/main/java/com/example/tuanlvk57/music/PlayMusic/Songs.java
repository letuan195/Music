package com.example.tuanlvk57.music.PlayMusic;

/**
 * Created by tuanlv.k57 on 27/01/2015.
 */
public class Songs {
    private String Title;
    private String Artist;
    private long Duration;
    private String Album;
    private String Lyric;


    public Songs(String name, String artist){
        Title = name;
        Artist = artist;
    }
    public Songs(){

    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }
}
