package com.groclist.groclistworker.dto;

import org.bson.types.ObjectId;

public class RecipeDTO {
    private String name;
    private String ingredients;
    private String url;
    private String title;

    public RecipeDTO(String name, String ingredients, String url, String title) {
        this.name = name;
        this.ingredients = ingredients;
        this.url = url;
        this.title = title;
    }

    public RecipeDTO() {
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
