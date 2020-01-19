package com.groclist.groclistworker.scrapper;

import com.groclist.groclistworker.model.Recipe;

public interface IngredientScrapper {

    public Recipe fetchIngredientsFromWebPage(String url);

}
