package com.groclist.groclistworker.webhandler.scrapper.interfaces;

import com.groclist.groclistworker.model.Recipe;

public interface IngredientScrapper {

    public Recipe fetchIngredientsFromWebPage(String url);

}
