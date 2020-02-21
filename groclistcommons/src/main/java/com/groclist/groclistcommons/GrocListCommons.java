package com.groclist.groclistcommons;

import com.groclist.groclistcommons.constants.ConversionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class GrocListCommons {

    private ConversionConstants converter = new ConversionConstants();
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private Double convert(String srcUnit, String destUnit, Double srcVal){
        HashMap<String, HashMap<String, Double>> conversionConstants = converter.CONVERSION_FACTORS;
        Double conversionFactor = 1.00;
        try{
            conversionFactor = conversionConstants.get(srcUnit).get(destUnit);
        } catch (NullPointerException exception){
            logger.info("Failed to convert {} to {}", srcUnit, destUnit);
        }

        if (conversionFactor == null){
            conversionFactor = 1.00;
        }
        return srcVal * conversionFactor;
    }

    public void mergeIngredient(HashMap<String, HashMap<String, String>> ingredients, String name, HashMap<String, String> quantity) {
        HashMap<String, String> newUnits = new HashMap<>();
        Double srcValue = Double.parseDouble(quantity.get("amount"));

        if (!quantity.get("unit").equals(ingredients.get(name).get("unit"))) {
            logger.info("Conversion is needed from {} to {} for ingredient {}", quantity.get("unit"), ingredients.get(name).get("unit"), name);
            srcValue = convert(quantity.get("unit"), ingredients.get(name).get("unit"), srcValue);
        }

        Double finalVal = srcValue + Double.parseDouble(ingredients.get(name).get("amount"));
        newUnits.put("unit", ingredients.get(name).get("unit"));
        newUnits.put("amount", finalVal.toString());
        ingredients.put(name, newUnits);
    }

    public static void main(String[] args) {

    }
}
