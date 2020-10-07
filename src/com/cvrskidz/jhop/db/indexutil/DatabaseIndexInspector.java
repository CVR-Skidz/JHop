package com.cvrskidz.jhop.db.indexutil;

import com.cvrskidz.jhop.db.IndexConnection;
import com.cvrskidz.jhop.db.IndexLog;
import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.indexutil.IndexInspector;
import com.cvrskidz.jhop.indexes.Index;
import java.util.List;

public class DatabaseIndexInspector extends IndexInspector {
    private IndexConnection db;
    private List<IndexLog> indexes;
    
    public DatabaseIndexInspector(List<String> argv) throws CommandException {
        super(argv);
        db = new IndexConnection();
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
    
    public List<IndexLog> getResults() {
        return indexes;
    }
}
