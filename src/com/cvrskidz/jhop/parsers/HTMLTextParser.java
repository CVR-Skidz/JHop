package com.cvrskidz.jhop.parsers;

import java.util.Iterator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLTextParser extends HTMLParser<String>{
    private static final String SEPERATOR = "\n\n";
    private Elements textNodes; 
    
    public HTMLTextParser(String html, String attribute, String value) {
        super(html, attribute, value);
    }
    
    @Override 
    public Parser<String> parse() {
        Elements surface = dom.body().children();
        textNodes = getMatchingElements(surface);
        
        Iterator<Element> it = textNodes.iterator();
        while(it.hasNext()) {
            Element e = it.next();
            if(!e.hasText()) it.remove();
        }
        
        return this;
    }
    
    @Override
    public String output() {
        StringBuilder out = new StringBuilder();

        Iterator<Element> it = textNodes.iterator();
        while(it.hasNext()) {
            Element current = it.next();
            out.append(current.text());
            if(it.hasNext()) out.append(SEPERATOR);
        }
        
        return out.toString();
    } 
}
