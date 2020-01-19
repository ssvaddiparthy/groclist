package com.groclist.groclistconverter.constants;

import java.util.HashMap;

public class ConversionConstants {

    public HashMap<String, HashMap<String, Double>> CONVERSION_FACTORS = new HashMap<>();

    public ConversionConstants(){
        // Input is in CUPS
        HashMap<String, Double> cupConverters = new HashMap<>();
        cupConverters.put("tsp", 48.0);
        cupConverters.put("tbsp", 16.00);
        cupConverters.put("gm", 136.00);
        cupConverters.put("pound", 4.80);
        CONVERSION_FACTORS.put("cup", cupConverters);

        // Input is in American TableSpoons
        HashMap<String, Double> tbspConverters = new HashMap<>();
        tbspConverters.put("tsp", 3.0);
        tbspConverters.put("pound", 0.03);
        tbspConverters.put("gm", 15.00);
        tbspConverters.put("cup", 0.0625);
        CONVERSION_FACTORS.put("tbsp", tbspConverters);

        // Input is in American Teaspoons
        HashMap<String, Double> tspConverters = new HashMap<>();
        tspConverters.put("tbsp", 0.33);
        tspConverters.put("pound", 0.010);
        tspConverters.put("gm", 5.00);
        tspConverters.put("cup", 0.020);
        CONVERSION_FACTORS.put("tsp", tspConverters);

        // Input is in Grams
        HashMap<String, Double> gmConverters = new HashMap<>();
        gmConverters.put("tbsp", 0.06);
        gmConverters.put("pound", 0.002);
        gmConverters.put("tsp", 0.23);
        gmConverters.put("cup", 0.0078125);
        CONVERSION_FACTORS.put("gm", gmConverters);

        // Input is in ml
        HashMap<String, Double> mlConverters = new HashMap<>();
        gmConverters.put("tbsp", 0.067628);
        gmConverters.put("pound", 0.002204623);
        gmConverters.put("tsp", 0.202884);
        gmConverters.put("cup", 0.00422675);
        CONVERSION_FACTORS.put("ml", gmConverters);
    }
}
