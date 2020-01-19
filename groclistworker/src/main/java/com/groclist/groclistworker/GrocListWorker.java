package com.groclist.groclistworker;

import com.groclist.groclistworker.constants.HebbarsConstants;
import com.groclist.groclistworker.model.Recipe;
import com.groclist.groclistworker.repository.RecipeRepository;
import com.groclist.groclistworker.scrapper.HebbarsIngredientScrapper;
import com.groclist.groclistworker.scrapper.urlfetcher.HKURLsFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@EnableScheduling
public class GrocListWorker {

    @Autowired
    RecipeRepository recipeRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        SpringApplication.run(GrocListWorker.class, args);
    }

    @Scheduled(fixedDelay = 100000)
    private void recipeAdder(){
        HKURLsFetcher urlFetcher = new HKURLsFetcher();
        List<String> breakfastRecipes = urlFetcher.fetchHKURLS(HebbarsConstants.WEBSITE_URL+ "/" + HebbarsConstants.BF_RECIPES);

        HebbarsIngredientScrapper fetcher = new HebbarsIngredientScrapper();
        List<String> missedUrls = getMissedUrls(breakfastRecipes);
        if (missedUrls.isEmpty()){
            logger.info("All the fetched URLs have been processed and stored in the DB");
            return;
        }

        logger.info("{} urls to process", missedUrls.size());
        for (String temp: missedUrls) {
            logger.info("Fetching the ingredients from {}", temp);
            recipeRepository.save(fetcher.fetchIngredientsFromWebPage(temp));
        }
    }

    private List<String> getMissedUrls(List<String> onlineRecipeList){
        List<Recipe> storedRecipeList = recipeRepository.findAll();

        List<String> storedUrlList = new ArrayList<>();
        for (Recipe temp: storedRecipeList) {
            storedUrlList.add(temp.getUrl());
        }

        HashSet<String> storedUrlSet = new HashSet<>(storedUrlList);
        HashSet<String> newUrlSet = new HashSet<>(onlineRecipeList);
        newUrlSet.removeAll(storedUrlSet);
        return new ArrayList<>(newUrlSet);
    }
}
