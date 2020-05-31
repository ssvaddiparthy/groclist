package com.groclist.groclistcommons;

import com.groclist.groclistcommons.exceptions.InvalidQuantityException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UnitConverterTest {

    private UnitConverter converter = new UnitConverter();
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Test
    void convert() {
        try{
            assertEquals(96.00, converter.convert("cup", "tsp", 2.00));
            assertEquals(converter.convert("cup", "tbsp", 2.00), 32.00);
            assertEquals(converter.convert("cup", "gm", 2.00), 272.00);
            assertEquals(converter.convert("cup", "pound", 2.00), 9.6);
        } catch(InvalidQuantityException exception){
            logger.error("the source values cannot be negative to convert");
        }
    }

    @Test
    void convertFailure() {
        assertThrows(InvalidQuantityException.class, () -> {
            converter.convert("cup", "tsp", -2.00);
        });
    }
}