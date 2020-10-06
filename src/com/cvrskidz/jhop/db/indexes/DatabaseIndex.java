package com.cvrskidz.jhop.db.indexes;

import com.cvrskidz.jhop.db.IndexConnection;
import com.cvrskidz.jhop.indexes.Index;

public class DatabaseIndex extends Index{
    private IndexConnection db;
    
    public DatabaseIndex() {
        db = new IndexConnection();
    }
   
}
