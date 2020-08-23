package com.cvrskidz.jhop.parsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
        try {
            Elements surface = new Elements();
            surface.add(dom.body());
            textNodes = getMatchingElements(surface);
                
            Set<String> tags = includedTags();
            
            Iterator<Element> it = textNodes.iterator();
            while(it.hasNext()) {
                Element e = it.next();
                if(!e.hasText()) it.remove();
            }
        }
        catch (NullPointerException e) {
            //parsed with bad input, or not initialized
            textNodes = new Elements();
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
    
    @Override
    protected Elements allChildrenOf(Element node){
        Elements elements = new Elements();
        for(Element e: node.children()){
            if(tags != null && tags.contains(e.tagName())) {
                elements.add(e);
                elements.addAll(allChildrenOf(e));
            }
        }
        
        return elements;
    }
}
