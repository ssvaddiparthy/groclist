package com.groclist.groclistcommons;

import com.groclist.groclistcommons.constants.ConversionConstants;
import com.groclist.groclistcommons.exceptions.InvalidQuantityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class GrocListCommons {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private UnitConverter converter;

    public void mergeIngredient(HashMap<String, HashMap<String, String>> ingredients, String name, HashMap<String, String> quantity) {
        HashMap<String, String> newUnits = new HashMap<>();
        Double srcValue = Double.parseDouble(quantity.get("amount"));

        if (!quantity.get("unit").equals(ingredients.get(name).get("unit"))) {
            logger.info("Conversion is needed from {} to {} for ingredient {}", quantity.get("unit"), ingredients.get(name).get("unit"), name);
            try{
                srcValue = converter.convert(quantity.get("unit"), ingredients.get(name).get("unit"), srcValue);
            } catch (InvalidQuantityException exception){
                logger.error("{} -- cannot have a negative quantity to convert", srcValue);
            }
        }

        Double finalVal = srcValue + Double.parseDouble(ingredients.get(name).get("amount"));
        newUnits.put("unit", ingredients.get(name).get("unit"));
        newUnits.put("amount", finalVal.toString());
        ingredients.put(name, newUnits);
    }

    public static void main(String[] args) {

    }
}
