package com.groclist.groclistworker.webhandler.urlfetcher;

import com.groclist.groclistworker.constants.HebbarsConstants;
import com.groclist.groclistworker.webhandler.commons.GetWebPage;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HKURLsFetcher {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private GetWebPage webPageGetter = new GetWebPage();

    public HKURLsFetcher(){}

    private Integer getTotalPages(Document webPage){
        return Integer.parseInt(webPage.getElementsByClass(HebbarsConstants.NAV_LAST_PAGE_CLASS_NAME).text());
    }

    private List<String> getSubLinks(Document webPage){
        List<String> fetchedURLs = new ArrayList<>();

        for (Element ele: webPage.getElementsByTag("h3")) {
            Elements mainContent = ele.getElementsByTag("a");
            for (Element e: mainContent) {
                fetchedURLs.add(e.attr("href"));
            }
        }
        return fetchedURLs;
    }

    public List<String> fetchHKURLS(String baseUrl){
        Document landingPage = webPageGetter.getWebPage(baseUrl);

        if (landingPage == null){
            logger.info("Unable to fetch {}", baseUrl);
            return null;
        }

        Integer totalPages = getTotalPages(landingPage);
        logger.info("Obtained a total of {} pages", totalPages);

        List<String> fetchedURLs = getSubLinks(webPageGetter.getWebPage(baseUrl));
        for (int i = 2; i <= totalPages; i++) {
            String subPage = baseUrl + "/page/"+ i;
            Document subPageDoc = webPageGetter.getWebPage(subPage);
            fetchedURLs.addAll(getSubLinks(subPageDoc));
        }
        return fetchedURLs;
    }

}
