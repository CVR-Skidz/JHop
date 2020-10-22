package com.cvrskidz.jhop.db.indexutil;

import com.cvrskidz.jhop.indexutil.IndexReader;
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
    
    /**
     * Create a DatabaseIndexReader.
     * 
     * @param argv The arguments supplied to the --set command
     * @param db The connected database
     * @throws CommandException If there was an error creating this object.
     */
    public DatabaseIndexReader(List<String> argv, IndexConnection db) throws CommandException {
        super(argv);
        this.db = db;
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
        
        //populate index
        try {
            fillIndex(index);
            return index;
        }
        catch (IOException e){
            setError(e, "Found invalid page in database. ");
            return new Index();
        }
    }
    
    /**
     * populate the given index with its mapped results in the database.
     * 
     * @param index The index to populate.
     * @throws IOException If there was an error populating the index.
     */
    private void fillIndex(Index index) throws IOException{
        String termsQuery = "FROM Term WHERE index_name='" + indexName + "'";
        List<Term> terms = DatabaseHelper.execute(db, termsQuery);
        Map<String, Set<IndexEntry>> indexTerms = new HashMap();  // term -> page
        Map<IndexEntry, Map<String, Integer>> indexPages = new HashMap(); // page -> (term -> frequency)
        
        //load index contents
        for(Term t : terms) {
            String term = t.getTerm();
            String pageURL = t.getPage();
            
            //get page source
            IndexEntry entry = getPage(pageURL);
            //add term and add it's page
            Set<IndexEntry> termPages = indexTerms.get(t.getTerm()); 
            if(termPages == null) {
                termPages = new HashSet();
                termPages.add(entry);
                indexTerms.put(term, termPages);
            }
            else termPages.add(entry);
            
            //add each term and frequency to page
            Map<String, Integer> pageTerms = indexPages.get(entry);
            int frequency = 1;
            
            if(pageTerms == null) pageTerms = new HashMap(); //add
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
    
    /**
     * Create and IndexEntry for the queried page.
     * 
     * @param pageURL The URL of the page
     * @return The new IndexEntry
     * @throws IOException If the page could not be connected to
     */
    private IndexEntry getPage(String pageURL) throws IOException {
            String pageQuery = "FROM Page WHERE url='" + pageURL + "' AND "
                    + "index_name='" + indexName + "'";
            Page page = (Page) DatabaseHelper.execute(db, pageQuery).get(0);
            HopConnection con = new HopConnection(page.getSrc());
            return new IndexEntry(con);
    }
}
