package com.cvrskidz.jhop.parsers;

import java.util.Iterator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * An implementation of a HTMLParser that parses all nodes in a DOM containing 
 * links to other endpoints that satisfy the given query.
 * <p>
 * The query supplied to this parser consists of an attribute name and an attribute
 * value. All links inside nodes that contain references and who also contain the 
 * specified attribute and value are extracted.
 * 
 * @author cvrskidz 18031335
 */
public class HTMLReferenceParser extends HTMLParser<Elements>{
    private final String REF_KEY = "href";  //atttribute name specifiying link
    private Elements referenceNodes;        //nodes containing external links
    
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
            Elements surface = new Elements();              // topmost level of dom 
            surface.add(dom.body());                        // start from <body>...
            referenceNodes = getMatchingElements(surface);  // obtain elements that mathc the query
        
            Iterator<Element> it = referenceNodes.iterator();
            while(it.hasNext()) {
                Element e = it.next();
                String reference = e.attributes().get(REF_KEY);
                if(reference.isEmpty()) it.remove();        // remove nodes without links
            }
        }
        catch (NullPointerException e) {
            referenceNodes = new Elements();                //parsed with bad input, or not initalized
        }
        
        return this;
    }
    
    @Override
    public Elements output() {
        return referenceNodes;
    } 
    
    /**
     * Traverse the DOM from the specified node, returning all node branches
     * that start from a node matching this objects query. 
     * 
     * @param node
     * @return 
     */
    @Override
    protected Elements allChildrenOf(Element node){
        Elements elements = new Elements();

        for(Element e: node.children()){
            elements.add(e);                    //add each child
            elements.addAll(allChildrenOf(e));  //add each child of this child
        }
        
        return elements;
    }
}
