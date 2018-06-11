package com.example.refuel.torweather.Model;

public class LifeIndex {


    private int imageId;

    private String name;

    private String title;

    private String text;

    public LifeIndex(String name , String title , String text , int imageId){
        this.name = name;
        this.title = title;
        this.text = text;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
