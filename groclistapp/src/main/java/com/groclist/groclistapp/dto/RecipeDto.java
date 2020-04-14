package com.groclist.groclistapp.dto;

public class RecipeDto {
    private String name;
    private String ingredients;
    private String url;
    private String title;

    public RecipeDto(String name, String ingredients, String url, String title) {
        this.name = name;
        this.ingredients = ingredients;
        this.url = url;
        this.title = title;
    }

    public RecipeDto() {
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

    @Override
    public String toString() {
        return "RecipeDTO{" +
                "name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
