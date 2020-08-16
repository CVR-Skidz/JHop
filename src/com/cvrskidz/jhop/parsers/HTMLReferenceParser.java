package com.cvrskidz.jhop.parsers;

import java.util.Iterator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLReferenceParser extends HTMLParser<Elements>{
    private final String REF_KEY = "href";
    private Elements referenceNodes; 
    
    public HTMLReferenceParser(String html, String attribute, String value) {
        super(html, attribute, value);
    }
    
    
    /**
     * Traverse the DOM tree from the root and return any elements 
     * matching this objects query that contain URLs to crawl.
     * 
     * @return All elements matching this objects query and contain valid "href"
     * attributes.
     */
    @Override 
    public Parser<Elements> parse() {
        try {
            Elements surface = dom.body().children();
            referenceNodes = getMatchingElements(surface);
        
            Iterator<Element> it = referenceNodes.iterator();
            while(it.hasNext()) {
                Element e = it.next();
                String reference = e.attributes().get(REF_KEY);
                if(reference.isEmpty()) it.remove();
            }
        }
        catch (NullPointerException e) {
            //parsed with bad input, or not initalized
            referenceNodes = new Elements();
        }
        
        return this;
    }
    
    @Override
    public Elements output() {
        return referenceNodes;
    } 
}
