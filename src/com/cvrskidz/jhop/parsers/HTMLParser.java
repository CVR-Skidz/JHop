package com.cvrskidz.jhop.parsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A Parser implementation for HTML. How the HTML is parsed is the implementing
 * classes responsibility. This class defines common methods and members shared
 * across all HTML parsers, but does not define any parsing functionality.
 * 
 * @author cvrskidz 18013335
 * @param <E> The type of the parsed output.
 */
public abstract class HTMLParser<E> implements Parser<E>{
    public static int WIDTH = 0;               // Line width when parsing text
    
    protected Document dom;             // DOM of the HTML suuplied to this
    protected Set<String> tags;         // Tags to accept when parsing this DOM
    protected String attribute, value;  // The query to check when parsing this DOM
    
    // The realtive path of the configuration file specifiying applicable
    // tags to parse.
    public static final String PATH = "config/include";
    
    public HTMLParser(String html, String attribute, String value) {
        dom = Jsoup.parse(html);
        this.attribute = attribute;
        this.value = value;
        tags = includedTags();
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
     * The manner in which children are selected is determined by the underlying 
     * HTMLParser implementation.
     * 
     * @param node The parent of the children elements.
     * @return All elements who are children of the specified
     * node.
     */
    protected abstract Elements allChildrenOf(Element node);

    public void setDom(String html) {
        dom = Jsoup.parse(html);
    }
    
    /**
     * Reads the names of tags to include, and therefore also the names of tags
     * to exclude from parsing, from a configuration file stored on disk.
     * 
     * @return A set of tag names to accept when parsing HTML.
     */
    protected Set<String> includedTags() {
        try {
            Reader file = new FileReader(HTMLParser.PATH);
            BufferedReader bufferedFile = new BufferedReader(file);

            Set<String> tags = new HashSet();
            String tagBuffer;

            while((tagBuffer = bufferedFile.readLine()) != null) {
                tags.add(tagBuffer);
            }

            bufferedFile.close();
            return tags;
        }
        catch(IOException e) {
            System.out.println("Error reading " + e.getMessage());
            return null;
        }
    }
}
