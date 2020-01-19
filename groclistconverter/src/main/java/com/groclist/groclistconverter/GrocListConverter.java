package com.groclist.groclistconverter;

import com.groclist.groclistconverter.constants.ConversionConstants;

import java.util.HashMap;

public class GrocListConverter {

    private ConversionConstants converter = new ConversionConstants();

    public Double convert(String srcUnit, String srcVal, String destUnit){
        HashMap<String, HashMap<String, Double>> conversionConstants = converter.CONVERSION_FACTORS;
        Double conversionFactor = conversionConstants.get(srcUnit).get(destUnit);
        return Double.parseDouble(srcVal) * conversionFactor;
    }

    public static void main(String[] args) {

    }
}
