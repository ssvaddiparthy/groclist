package com.groclist.groclistcommons;

import com.groclist.groclistcommons.constants.ConversionConstants;
import com.groclist.groclistcommons.exceptions.InvalidQuantityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
public class UnitConverter {

    private ConversionConstants converter = new ConversionConstants();
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public Double convert(String srcUnit, String destUnit, Double srcVal) throws InvalidQuantityException {

        if (srcVal < 0){
            throw new InvalidQuantityException(String.format("Source value to convert cannot be less than 0 -- {}",
                    srcVal));
        }

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
}
