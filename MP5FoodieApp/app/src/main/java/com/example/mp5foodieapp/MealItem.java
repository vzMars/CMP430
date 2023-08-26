package com.example.mp5foodieapp;

public class MealItem {

    private String title;
    private String description;
    private String ingredients;
    private String calories;
    private String link;
    private int imageId;

    MealItem(String title, String description, String ingredients, String calories, String link){
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.calories = calories;
        this.link = link;
    }

    MealItem(String title, String description, String ingredients, String calories, String link, int imageId){
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.calories = calories;
        this.link = link;
        this.imageId = imageId;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getIngredients(){
        return ingredients;
    }

    public String getCalories(){
        return calories;
    }

    public String getLink(){
        return link;
    }

    public int getImageId(){
        return imageId;
    }

}
