package com.cvrskidz.jhop.db.indexutil;

import com.cvrskidz.jhop.executables.indexutil.IndexReader;
import com.cvrskidz.jhop.db.*;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.indexes.Index;
import com.cvrskidz.jhop.indexes.IndexEntry;
import com.cvrskidz.jhop.network.HopConnection;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DatabaseIndexReader extends IndexReader {
    private IndexConnection db;
    
    public DatabaseIndexReader(List<String> argv) throws CommandException{
        super(argv);
        db = new IndexConnection();
    }
    
    @Override
    public Index exec(Index index) {
        if(!DatabaseHelper.dbContainsIndex(db, indexName)) {
            setError(new Exception("Could not find index: " + indexName));
            return index;
        }
        
        //attain index log
        String indexQuery = "FROM IndexLog WHERE index_name='" + indexName + "'";
        IndexLog result = (IndexLog) (DatabaseHelper.execute(db, indexQuery).get(0));
        // set options etc.
        index = new Index();
        index.getOptions().setName(indexName);
        index.getOptions().setQuery(result.getTag(), result.getAttribute());
        
        
        try {
            fillIndex(index);
            return index;
        }
        catch (IOException e){
            setError(e, "Found invalid page in database. ");
            return new Index();
        }
    }
    
    private void fillIndex(Index index) throws IOException{
        String termsQuery = "FROM Term WHERE index_name='" + indexName + "'";
        List<Term> terms = DatabaseHelper.execute(db, termsQuery);

        // term -> page
        Map<String, Set<IndexEntry>> indexTerms = new HashMap();
        // page -> (term -> frequency)
        Map<IndexEntry, Map<String, Integer>> indexPages = new HashMap();
        
        //load index contents
        for(Term t : terms) {
            String term = t.getTerm();
            String pageURL = t.getPage();
            
            //get page source
            String pageQuery = "FROM Page WHERE url='" + pageURL + "' AND "
                    + "index_name='" + indexName + "'";
            Page page = (Page) DatabaseHelper.execute(db, pageQuery).get(0);
            HopConnection con = new HopConnection(page.getSrc());
            IndexEntry entry = new IndexEntry(con);
            
            //add term and add it's page
            Set<IndexEntry> termPages = indexTerms.get(t.getTerm()); 
            if( termPages == null) {
                termPages = new HashSet();
                termPages.add(entry);
                indexTerms.put(term, termPages);
            }
            else termPages.add(entry);
            
            //add each term and frequency to page
            Map<String, Integer> pageTerms = indexPages.get(entry);
            int frequency = 1;
            
            if(pageTerms == null) { //add
                pageTerms = new HashMap();
            }
            else {  //update
                Integer indexedFrequency = pageTerms.get(term);
                if(indexedFrequency != null) frequency += indexedFrequency;
            }
            
            pageTerms.put(term, frequency);
            indexPages.put(entry, pageTerms);
        }
        
        index.setTerms(indexTerms);
        index.setPages(indexPages);
    }
}
