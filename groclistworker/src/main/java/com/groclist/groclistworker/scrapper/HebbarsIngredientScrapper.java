package com.groclist.groclistworker.scrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groclist.groclistworker.constants.ConversionConstants;
import com.groclist.groclistworker.constants.HebbarsConstants;
import com.groclist.groclistworker.model.Recipe;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class HebbarsIngredientScrapper implements IngredientScrapper {

    private HashMap<String, HashMap<String, Double>> converter = new ConversionConstants().CONVERSION_FACTORS;
    private GetWebPage webPageGetter = new GetWebPage();
    private ObjectMapper objectMapper = new ObjectMapper();
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    public HebbarsIngredientScrapper() {
    }

    private void mergeIngredients(HashMap<String, HashMap<String, String>> ingredients, String name, HashMap<String,
            String> quantity) {

        Double finalVal = 0.00;
        HashMap<String, String> newUnits = new HashMap<>();
        if (quantity.get("unit").equals(ingredients.get(name).get("unit"))) {
            logger.info("The input and output units are the same {}", quantity.get("unit"));
            finalVal = Double.parseDouble(ingredients.get(name).get("amount")) + Double.parseDouble(quantity.get(
                    "amount"));
        } else {
            logger.info("Conversion is needed from {} to {}", quantity.get("unit"), ingredients.get(name).get("unit"));
            try{
                finalVal = Double.parseDouble(ingredients.get(name).get("amount"))
                        +
                        (converter.get(quantity.get("unit")).get(ingredients.get(name).get("unit"))
                                * Double.parseDouble(quantity.get("amount")));

            } catch (NullPointerException exception){
                logger.error("No conversion from {} to {} found.", quantity.get("unit"),
                        ingredients.get(name).get("unit"));
            }
        }
        newUnits.put("unit", ingredients.get(name).get("unit"));
        newUnits.put("amount", finalVal.toString());
    }

    private HashMap<String, HashMap<String, String>> scrapeIngredients(Elements elements) {
        HashMap<String, HashMap<String, String>> ingredients = new HashMap<>();
        for (Element ingredientDivision : elements) {
            Elements ul = ingredientDivision.getElementsByTag("ul");
            for (Element lis : ul) {
                for (Element li : lis.children()) {
                    String name = li.getElementsByClass(HebbarsConstants.INGREDIENT_NAME_CLASS_NAME).text();
                    String unit = li.getElementsByClass(HebbarsConstants.INGREDIENT_UNIT_CLASS_NAME).text();
                    String amount = li.getElementsByClass(HebbarsConstants.INGREDIENT_AMOUNT_CLASS_NAME).text();

                    HashMap<String, String> quantity = new HashMap<>();
                    quantity.put("unit", unit);
                    quantity.put("amount", amount);

                    switch (amount) {
                        case "¼":
                            quantity.put("amount", "0.25");
                            break;
                        case "¾":
                            quantity.put("amount", "0.75");
                            break;
                        case "½":
                            quantity.put("amount", "0.50");
                            break;
                        case "1½":
                            quantity.put("amount", "1.50");
                            break;
                        case "1¼":
                            quantity.put("amount", "1.25");
                            break;
                        case "2½":
                            quantity.put("amount", "2.50");
                            break;
                        case "3½":
                            quantity.put("amount", "3.50");
                            break;
                        case "1 ":
                            quantity.put("amount", "1.00");
                            break;
                        case "½ ":
                            quantity.put("amount", "0.5");
                            break;

                    }

                    // missing amount
                    if (amount.isEmpty()) {
                        if (name.contains("few")) {
                            quantity.put("amount", "7");
                            quantity.put("unit", "units");
                        }

                        if (unit.equals("pinch")) {
                            quantity.put("amount", "0.01");
                            quantity.put("unit", "tsp");
                        }

                        if (unit.isEmpty()) {
                            quantity.put("amount", "1");
                            quantity.put("unit", "tbsp");
                        }

                    }

                    if (unit.isEmpty()) {
                        quantity.put("unit", "units");
                    }

                    if (!ingredients.containsKey(name)) {
                        ingredients.put(name, quantity);
                    } else {
                        logger.info("Merging new and old quantities for {}}", name);
                        mergeIngredients(ingredients, name, quantity);
                    }
                }
            }
        }
        return ingredients;
    }

    public Recipe fetchIngredientsFromWebPage(String url) {

        Document webPage = webPageGetter.getWebPage(url);
        if (webPage == null) {
            return null;
        }
        Elements ingredientGroups = webPage.getElementsByClass(HebbarsConstants.INGREDIENT_GROUP_CLASS_NAME);

        HashMap<String, HashMap<String, String>> ingredients = scrapeIngredients(ingredientGroups);
        String recipeName = webPage.title().split(" \\| ")[0].replace(" ", "-")
                .replace("-recipe", "");

        try {
            Recipe result = new Recipe(recipeName, objectMapper.writeValueAsString(ingredients), url, recipeName);
            return result;
        } catch (JsonProcessingException exception) {
            logger.error("Unable to convert the following string into a Recipe object: \n {}", ingredients.toString());
            logger.error(exception.getMessage());
        }

        return null;
    }
}
