package com.cvrskidz.jhop.db.indexutil;

import com.cvrskidz.jhop.executables.indexutil.IndexDropper;
import com.cvrskidz.jhop.db.*;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.indexes.Index;
import java.util.List;

public class DatabaseIndexDropper extends IndexDropper {
    private IndexConnection db;
    
    public DatabaseIndexDropper(List<String> argv) throws CommandException {
        super(argv);
        db = new IndexConnection();
    }
    
    @Override 
    public Index exec(Index index) {
        if(DatabaseHelper.dbContainsIndex(db, indexName)) { //exists ?
            deleteTerms(db);    //delete terms referencing index
            deletePages(db);    //delete pages referencing index
            deleteIndex(db);    //delete directory entry for index
            
            return new Index();
        }
        else {
            return index; //if index is not in database return it
        }
    }
    
    private void deleteTerms(IndexConnection db) {
        StringBuilder hql = new StringBuilder("DELETE FROM Term ");
        hql.append("WHERE index_name='").append(indexName).append("'");
        
        DatabaseHelper.executeModification(db, hql.toString());
    }
    
    private void deletePages(IndexConnection db) {
        StringBuilder hql = new StringBuilder("DELETE FROM Page ");
        hql.append("WHERE index_name='").append(indexName).append("'");
        
        DatabaseHelper.executeModification(db, hql.toString());
    }
    
    private void deleteIndex(IndexConnection db) {
        StringBuilder hql = new StringBuilder("DELETE FROM IndexLog ");
        hql.append("WHERE index_name='").append(indexName).append("'");
        
        DatabaseHelper.executeModification(db, hql.toString());
    }
    
}
