package com.cvrskidz.jhop.db.indexutil;

import com.cvrskidz.jhop.executables.indexutil.IndexWriter;
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

public class DatabaseIndexWriter extends IndexWriter{
    private IndexConnection db;
    
    public DatabaseIndexWriter(List<String> argv) throws CommandException{
        super(argv);
        db = new IndexConnection();
    }
    
    @Override
    public Index exec(Index index) {
        IndexOptions opt = index.getOptions();
        opt.setName(indexName);
        IndexLog record = new IndexLog(indexName, opt.getAttribute(), opt.getValue());
        
        // add index to DIRECTORY
        if(DatabaseHelper.dbContainsIndex(db, indexName)) {
            //update
            DatabaseHelper.update(db, record);
        }
        else {
            //add
            DatabaseHelper.save(db, record);
        }
        
        indexContents(index);
        return index;
    }
    
    private void indexContents(Index index) {
        // add pages to PAGE
        for(IndexEntry page : index.getPages().keySet()) {
            if(!DatabaseHelper.dbContainsPage(db, page.getUrl(), indexName)) {
                //only add new pages (no need to update pre-existing ones)
                Page pageRecord = new Page(indexName, page.getUrl(), 
                    page.getSource(), page.getTitle());
                DatabaseHelper.save(db, pageRecord);
            }
            
            // add terms to TERM
            Map<String, Integer> pageTerms = index.getPages().get(page);
            for(String term : pageTerms.keySet()) {
                Term termRecord = new Term(indexName, term, page.getUrl(), pageTerms.get(term));

                if(DatabaseHelper.dbContainsTerm(db, term, page.getUrl())) {
                    //update
                    DatabaseHelper.update(db, termRecord);
                }
                else {
                    //add
                    DatabaseHelper.save(db, termRecord);
                }
            }
        }
    } 
}
