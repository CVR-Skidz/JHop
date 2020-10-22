package com.cvrskidz.jhop.db.indexutil;

import com.cvrskidz.jhop.indexutil.IndexDropper;
import com.cvrskidz.jhop.db.*;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.indexes.Index;
import java.util.List;

/**
 * A DatabaseIndexDropper extends a generic IndexDropper to delete a persistent 
 * index from a database rather than a file.
 * 
 * @author cvrskidz 18031335
 */
public class DatabaseIndexDropper extends IndexDropper {
    private final IndexConnection db;
    
    /**
     * Create a DatabaseIndexDropper.
     * 
     * @param argv The arguments supplied to the --drop command.
     * @param db The database connection.
     * @throws CommandException If there was an error creating this object.
     */
    public DatabaseIndexDropper(List<String> argv, IndexConnection db) throws CommandException {
        super(argv);
        this.db = db;
    }
    
    
    @Override 
    public Index exec(Index index) {
        if(DatabaseHelper.dbContainsIndex(db, indexName)) { //exists ?
            deleteTerms(db);    //delete terms referencing index
            deletePages(db);    //delete pages referencing index
            deleteIndex(db);    //delete directory entry for index
            db.reloadSession();
            return new Index();
        }
        else return index; //if index is not in database return it
    }
    
    /**
     * Delete the terms indexed by the target index.
     * @param db  The connected database
     */
    private void deleteTerms(IndexConnection db) {
        StringBuilder hql = new StringBuilder("DELETE FROM Term ");
        hql.append("WHERE index_name='").append(indexName).append("'");
        
        DatabaseHelper.executeModification(db, hql.toString());
    }
    
    /**
     * Delete the pages indexed by the target index.
     * @param db  The connected database
     */
    private void deletePages(IndexConnection db) {
        StringBuilder hql = new StringBuilder("DELETE FROM Page ");
        hql.append("WHERE index_name='").append(indexName).append("'");
        
        DatabaseHelper.executeModification(db, hql.toString());
    }
    
    /**
     * Delete the target index.
     * @param db  The connected database
     */
    private void deleteIndex(IndexConnection db) {
        StringBuilder hql = new StringBuilder("DELETE FROM IndexLog ");
        hql.append("WHERE index_name='").append(indexName).append("'");
        
        DatabaseHelper.executeModification(db, hql.toString());
    }
    
}
