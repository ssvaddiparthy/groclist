package com.groclist.groclistworker.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Recipe {

    private String name;
    private String title;
    private String ingredients;
    private String url;

    public Recipe(){}

    public Recipe(String name, String ingredients, String url, String title) {
        this.name = name;
        this.ingredients = ingredients;
        this.url = url;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getUrl(){ return this.url; }

    public void setUrl(String url){ this.url = url; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
