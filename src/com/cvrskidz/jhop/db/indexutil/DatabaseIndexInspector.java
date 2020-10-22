package com.cvrskidz.jhop.db.indexutil;

import com.cvrskidz.jhop.db.IndexConnection;
import com.cvrskidz.jhop.db.IndexLog;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.indexutil.IndexInspector;
import com.cvrskidz.jhop.indexes.Index;
import java.util.List;

/**
 * A DatabaseIndexInspector extends a generic IndexInspector to list the 
 * details of an index stored in a database. The results of this operation
 * are not printed to a stream, and should be obtained after execution.
 * 
 * @author cvrskidz 18031335.
 */
public class DatabaseIndexInspector extends IndexInspector {
    private IndexConnection db;
    private List<IndexLog> indexes; // The indexes to describe
    
    /**
     * Create a DatabaseIndexInspector.
     * 
     * @param argv The arguments supplied to the --describe command
     * @param db The connected database
     * @throws CommandException If there was an error creating this object.
     */
    public DatabaseIndexInspector(List<String> argv, IndexConnection db) throws CommandException {
        super(argv);
        this.db = db;
    }
    
    @Override
    public Index exec(Index index) {
        StringBuilder hql = new StringBuilder("FROM IndexLog");
        
        // list target index only if specified
        if(indexName != null && !indexName.isEmpty()) {
            hql.append(" WHERE index_name='").append(indexName).append("'");
        }
        
        indexes = DatabaseHelper.execute(db, hql.toString());
        return index;
    }
    
    /**
     * @return The log entries for the databases queried.
     */
    public List<IndexLog> getResults() {
        return indexes;
    }
}
