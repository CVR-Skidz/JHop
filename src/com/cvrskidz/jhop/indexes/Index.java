package com.cvrskidz.jhop.indexes;

import com.cvrskidz.jhop.network.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * An Index of Response(s) mapped to IndexEntry(s) containing the information of
 * the request used to obtain each response. An Index maps the terms contained within 
 * web pages to its url. This allows each page to be searched by its contents.  
 * 
 * @author cvrskidz 18031335
 */
public class Index implements Indexable<Response, IndexEntry>, Serializable{
    private Map<String, Set<IndexEntry>> terms;           // map of terms to pages 
    private Map<IndexEntry, Map<String, Integer>> pages;  // map of pages to terms & frequency
    
    private IndexOptions options;   // options of this index
    
    /** 
     * Constructs an empty Index.
     */
    public Index() {
        terms = new HashMap();
        pages = new HashMap();
        options = new IndexOptions();
    }
    
    /**
     * Update this Index by indexing the supplied response.
     *
     * @return The indexed Response. 
     * @see com.cvrskidz.jhop.network.Response
     */
    @Override
    public Response update(Response state) {
        index(state);
        return state;
    }
    
    /**
     * Checks whether the supplied entry has been indexed.
     *
     * @param key An entry to query this Index with.
     * @return True if the given entry has been indexed, false otherwise.
     * @see IndexEntry
     */
    @Override
    public boolean contains(IndexEntry key) {
        return pages.containsKey(key);
    }
    
    /** 
     * Indexes the given response.
     *
     * @param res The Response to index.
     * @return A reference to this Index after indexing.
     * @see com.cvrskidz.jhop.network.Response 
     */
    @Override
    public Indexable<Response, IndexEntry> index(Response res) {
        StringTokenizer tokens = new StringTokenizer(res.getContents());
        IndexEntry url = new IndexEntry(res.getUrl());
        
        while(tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            //update list of pages term appears in
            indexTerm(url, token);
            //update frequency of term in page
            indexPage(url, token);
        }   
        
        return this;
    }
    
    /**
     * Retrieves all pages matching the given name indexed in this index.
     * If a URL is supplied instead of a title, if the URL has been indexed 
     * only that entry is returned so that the size of the result is 1. 
     * 
     * @param page The page name to search for.
     * @return A list of IndexEntry(s) that match the page name.
     * @see IndexEntry 
     */
    public List<IndexEntry> getPage(String page) {
        List<IndexEntry> results = new ArrayList();
        
        for(IndexEntry e: pages.keySet()) {
            // return a single result when page name is a specific url.
            if(e.getUrl().equals(page)) {           
                results.clear();
                results.add(e);
                return results;
            }
            else if(e.getTitle().equals(page)) results.add(e);
        }
        
        return results;
    }
    
    /**
     * Return a set of all pages containing the specified term.
     * 
     * @param term The term to search for
     * @return The set of all pages containing the specified term
     * @see IndexEntry
     */
    public Set<IndexEntry> getPagesContaining(String term) {
        // search for both capitalized and uncapitalized terms.
        char firstLetter = term.charAt(0);
        boolean capital = firstLetter <= 'Z';
        firstLetter -= capital ? 'A' - 'a' : 'a' - 'A';
        String alternate = firstLetter + term.substring(1);
        
        Set<IndexEntry> results = terms.get(term);      // results for original term
        Set<IndexEntry> altResults = terms.get(alternate);   // results for alternate term
        
        if(altResults != null) {
            if(results != null) results.addAll(altResults);
            else results = altResults;
        }
        
        return results;
    }
    
    /**
     * Return the amount of times a term appears in a page.
     *
     * @param term The term to search for.
     * @param page The page to search in.
     * @return Return the amount of times a term appears in a page.
     */
    public Integer getFequencyIn(String term, IndexEntry page) {
        return pages.get(page).get(term);
    }
    
    /**
     * Index the given url and term. This maps the supplied page to 
     * each term it contains, and the frequency which it occurs.
     *
     * @param url The page url
     * @param term The term to count
     */
    private void indexPage(IndexEntry url, String term) {
        Map<String, Integer> terms = pages.get(url);
        int frequency = 1;
        
        //check for indexed terms
        if(terms == null) {
            terms = new HashMap();
        }
        else {
            Integer indexedFrequency = terms.get(term);
            if(indexedFrequency != null) frequency += indexedFrequency;
        }
        
        terms.put(term, frequency);
        pages.put(url, terms);
    }
    
    /**
     * Index the given url and term. This maps the supplied term to 
     * each page it appears in.
     *
     * @param url The page url
     * @param term The term to map
     */
    private void indexTerm(IndexEntry url, String term) {
        Set<IndexEntry> appearances = terms.get(term);
        
        //check if term has been encountered
        if(appearances == null) appearances = new HashSet();
        
        appearances.add(url);
        terms.put(term, appearances);
    }
    
    /**
     * @return The options idetnifying this Index.
     * @see IndexOptions
     */
    public IndexOptions getOptions() {
        return options;
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        
        for(Entry<IndexEntry, Map<String, Integer>> e: pages.entrySet()) {
            out.append(e.getKey()).append(" terms: ");
            out.append(e.getValue().size()).append("\n");
        }
        
        return out.toString();
    }

    public Map<String, Set<IndexEntry>> getTerms() {
        return terms;
    }

    public Map<IndexEntry, Map<String, Integer>> getPages() {
        return pages;
    }

    public void setTerms(Map<String, Set<IndexEntry>> terms) {
        this.terms = terms;
    }

    public void setPages(Map<IndexEntry, Map<String, Integer>> pages) {
        this.pages = pages;
    }
}
