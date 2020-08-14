package com.cvrskidz.jhop.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class HTMLParser<E> implements Parser<E>{
    protected Document dom;
    protected String attribute, value;
    
    public HTMLParser(String html, String attribute, String value) {
        dom = Jsoup.parse(html);
        this.attribute = attribute;
        this.value = value;
    }
    
    /**
     * Traverse the DOM tree from the given points and return any elements 
     * matching this objects query.
     * 
     * @param nodes The elements to traverse the tree from.
     * @return All elements matching this objects query
     * attributes.
     */
    protected Elements getMatchingElements(Elements nodes) {
        Elements matches = new Elements();
        
        for(Element e: nodes) {
            String query = e.attributes().get(attribute);  
            if(query.equals(value)) {
                Elements branch = allChildrenOf(e);
                matches.addAll(branch);
            }
            else {
                Elements childBranches = getMatchingElements(e.children());
                matches.addAll(childBranches);
            }
        }
        
        return matches;
    }
    
    /**
     * Returns all elements who are children of the specified
     * node. 
     * <p>
     * The children returned are not only direct children of the parent node, but 
     * any element beneath the parent node within the node tree.
     * 
     * @param node The parent of the children elements.
     * @return All elements who are children of the specified
     * node.
     */
    protected Elements allChildrenOf(Element node) {
        Elements elements = new Elements();

        for(Element e: node.children()){
            elements.add(e);
            elements.addAll(allChildrenOf(e));
        }
        
        return elements;
    }

    public void setDom(String html) {
        dom = Jsoup.parse(html);
    }
}
