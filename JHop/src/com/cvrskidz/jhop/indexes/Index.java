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

public class Index implements Indexable<Response>, Serializable{
    protected Map<String, Set<IndexEntry>> terms;
    protected Map<IndexEntry, Map<String, Integer>> pages;
    
    private IndexOptions options;
    
    public Index() {
        terms = new HashMap();
        pages = new HashMap();
        options = new IndexOptions();
    }
    
    @Override
    public Response update(Response state) {
        index(state);
        return state;
    }
    
    @Override
    public boolean contains(IndexEntry key) {
        return pages.containsKey(key);
    }
    
    @Override
    public Indexable<Response> index(Response res) {
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
    
    public List<IndexEntry> getPage(String page) {
        List<IndexEntry> results = new ArrayList();
        
        for(IndexEntry e: pages.keySet()) {
            if(e.getUrl().equals(page)) {
                results.clear();
                results.add(e);
                return results;
            }
            else if(e.getTitle().equals(page)) results.add(e);
        }
        
        return results;
    }
    
    public Set<IndexEntry> getPagesContaining(String term) {
        return terms.get(term);
    }
    
    public Integer getFequencyIn(String term, IndexEntry page) {
        return pages.get(page).get(term);
    }
    
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
    
    private void indexTerm(IndexEntry url, String term) {
        Set<IndexEntry> appearances = terms.get(term);
        
        //check if term has been encountered
        if(appearances == null) appearances = new HashSet();
        
        appearances.add(url);
        terms.put(term, appearances);
    }
    
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
}
