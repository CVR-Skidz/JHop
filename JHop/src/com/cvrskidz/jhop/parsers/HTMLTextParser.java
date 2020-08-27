package com.cvrskidz.jhop.parsers;

import java.util.Iterator;
import java.util.Set;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * An implementation of a HTMLParser that parses all nodes in a DOM containing 
 * text that satisfy the given query, in a manner that only returns information
 * intended to be read (displayed as content on a page).
 * <p>
 * The query supplied to this parser consists of an attribute name and an attribute
 * value. All text inside nodes that contain the specified attribute and value, 
 * or are children of a node that does, are extracted.
 * <p>
 * Nodes may satisfy the conditions to be parsed (contain names specified in 
 * JHops tag list, or satisfy the query) but are not included. This happens if a 
 * parent node is a direct child of the first instance matching the query but is
 * not included in JHop's taglist. This is intended behavior to avoid blob data
 * or other text not intended to be read as content.
 * 
 * @author bcc9954 18031335 cvr-skidz
 */
public class HTMLTextParser extends HTMLParser<String>{
    // character to seperate paragraphs, lists, etc. (an empty line)
    private static final String SEPERATOR = "\n\n";     
    private Elements textNodes; 
    
    public HTMLTextParser(String html, String attribute, String value) {
        super(html, attribute, value);
    }
    
    @Override 
    public Parser<String> parse() {
        try {
            Elements surface = new Elements();          // topmost level of DOM
            surface.add(dom.body());                    // start at <body>...
            textNodes = getMatchingElements(surface);   // obtain matching nodes
            Set<String> tags = includedTags();          // read persistent taglist
            
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
        
        // concatenate all text with given seperator
        Iterator<Element> it = textNodes.iterator();
        while(it.hasNext()) {
            Element current = it.next();
            out.append(current.text());
            if(it.hasNext()) out.append(SEPERATOR);
        }
        
        return out.toString();
    }
    
    /**
     * Traverses this objects DOM from the given node and matches all node branches
     * that start with a node included in JHop's taglist.
     * <p>
     * For example given the query "class" and "content" the tag "first" and "third"
     * will be returned, but not "second".
     * <pre>{@code
     *      <div class="content">
     *          <p id="first">...</p>
     *          <div id="pictures">
     *              <p id="second">...</p>
     *          </div>
     *          <ul>
     *              <li id="third">...</li>
     *          </ul>
     *      </div>
     * }</pre>
     * @param node The node to start from
     * @return 
     */
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
