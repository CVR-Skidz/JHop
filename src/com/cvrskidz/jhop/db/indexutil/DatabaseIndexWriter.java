package com.cvrskidz.jhop.db.indexutil;

import com.cvrskidz.jhop.indexutil.IndexWriter;
import com.cvrskidz.jhop.db.IndexConnection;
import com.cvrskidz.jhop.db.IndexLog;
import com.cvrskidz.jhop.db.Page;
import com.cvrskidz.jhop.db.Term;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.indexes.Index;
import com.cvrskidz.jhop.indexes.IndexEntry;
import com.cvrskidz.jhop.indexes.IndexOptions;
import java.util.List;
import java.util.Map;

/**
 * A DatabaseIndexWriter extends a generic IndexWriter and writes a index into
 * a database. 
 * 
 * @author cvrskidz 18031335
 */
public class DatabaseIndexWriter extends IndexWriter{
    private IndexConnection db;
    
    /**
     * Create a DatabaseIndexWriter.
     * 
     * @param argv The arguments supplied to the --create command
     * @param db The connected database
     * @throws CommandException If there was an error creating this object.
     */
    public DatabaseIndexWriter(List<String> argv, IndexConnection db) throws CommandException {
        super(argv);
        this.db = db;
    }
    
    /**
     * Saves the specified index;
     * 
     * @param index The index to save.
     * @return The index saved.
     */
    @Override
    public Index exec(Index index) {
        IndexOptions opt = index.getOptions();
        opt.setName(indexName);
        IndexLog record = new IndexLog(indexName, opt.getAttribute(), opt.getValue());
        
        // add index to DIRECTORY
        if(DatabaseHelper.dbContainsIndex(db, indexName)) {
            setError(new Exception("Index of the same name already exists"));
        }
        else {
            //add
            DatabaseHelper.save(db, record);
            indexContents(index);
        }
        
        return index;
    }
    
    /**
     * Store the contents of the index in the database.
     * 
     * @param index The index to save.
     */
    private void indexContents(Index index) {
        // add pages to PAGE
        for(IndexEntry page : index.getPages().keySet()) {
            Page pageRecord = new Page(indexName, page.getUrl(), 
                page.getSource(), page.getTitle());
            DatabaseHelper.save(db, pageRecord);
            
            // add terms to TERM
            Map<String, Integer> pageTerms = index.getPages().get(page);
            for(String term : pageTerms.keySet()) {
                Term termRecord = new Term(indexName, term, page.getUrl(), pageTerms.get(term));
                DatabaseHelper.save(db, termRecord);
            }
        }
    } 
}
