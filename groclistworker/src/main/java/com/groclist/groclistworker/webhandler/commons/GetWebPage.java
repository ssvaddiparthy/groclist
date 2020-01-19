package com.groclist.groclistworker.webhandler.commons;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GetWebPage {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private UrlValidator urlValidator = new UrlValidator();

    public GetWebPage(){}

    public Document getWebPage(String url){

        if (!urlValidator.isValid(url)){
            return null;
        }

        try{
            Document doc = Jsoup.connect(url).get();
            logger.debug("Successfully fetched {}", url);
            return doc;
        } catch (IOException exception){
            logger.error("Cannot connect to URL {}", url);
        }

        return null;
    }

}
