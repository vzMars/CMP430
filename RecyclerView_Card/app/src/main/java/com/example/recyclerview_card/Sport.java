package com.example.recyclerview_card;

public class Sport {

    private String title;
    private String info;
    private int imageId;

    Sport(String title, String info){
        this.title = title;
        this.info = info;
    }

    Sport(String title, String info, int imageId){
        this.title = title;
        this.info = info;
        this.imageId = imageId;
    }

    public String getTitle(){
        return title;
    }

    public String getInfo(){
        return info;
    }

    public int getImageId(){
        return imageId;
    }

}
