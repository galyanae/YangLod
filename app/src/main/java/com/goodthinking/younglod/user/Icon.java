package com.goodthinking.younglod.user;

/**
 * Created by Owner on 23/07/2016.
 */
public class Icon {

    private int Image;
    private String name;
    private int id;

    public Icon(int id, int image, String name) {
        this.id = id;
        Image = image;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
