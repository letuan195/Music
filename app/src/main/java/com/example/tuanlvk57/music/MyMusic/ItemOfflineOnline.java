package com.example.tuanlvk57.music.MyMusic;

/**
 * Created by tuanlv.k57 on 31/01/2015.
 */
public class ItemOfflineOnline {
    private int Image;
    private String Type;
    private int Number;

    public ItemOfflineOnline(int image, String type){
        this.Image = image;
        this.Type = type;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }
}
