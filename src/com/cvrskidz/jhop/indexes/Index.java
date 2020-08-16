package com.cvrskidz.jhop.indexes;

import com.cvrskidz.jhop.network.Response;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

public class Index implements Indexable<Response>, Serializable{
    protected Map<String, Set<String>> terms;
    protected Map<String, Map<String, Integer>> pages;
    
    public Index() {
        terms = new HashMap();
        pages = new HashMap();
    }
    
    @Override
    public Response update(Response state) {
        index(state);
        return state;
    }
    
    @Override
    public boolean contains(Response res) {
        return pages.containsKey(res.getUrl().getURLNoProtocol());
    }
    
    @Override
    public Indexable<Response> index(Response res) {
        StringTokenizer tokens = new StringTokenizer(res.getContents());
        String url = res.getUrl().getURLNoProtocol();
        
        while(tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            //update list of pages term appears in
            indexTerm(url, token);
            //update frequency of term in page
            indexPage(url, token);
        }   
        
        return this;
    }
    
    public Set<String> getPagesContaining(String term) {
        return terms.get(term);
    }
    
    public Integer getFequencyIn(String term, String page) {
        return pages.get(page).get(term);
    }
    
    private void indexPage(String url, String term) {
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
    
    private void indexTerm(String url, String term) {
        Set<String> appearances = terms.get(term);
        
        //check if term has been encountered
        if(appearances == null) appearances = new HashSet();
        
        appearances.add(url);
        terms.put(term, appearances);
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        
        for(Entry<String, Map<String, Integer>> e: pages.entrySet()) {
            out.append(e.getKey()).append(" terms: ");
            out.append(e.getValue().size()).append("\n");
        }
        
        return out.toString();
    }
}
