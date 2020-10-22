package com.cvrskidz.jhop.db.indexutil;

import com.cvrskidz.jhop.indexutil.IndexReader;
import com.cvrskidz.jhop.indexutil.IndexOperationFactory;
import com.cvrskidz.jhop.indexutil.IndexWriter;
import com.cvrskidz.jhop.indexutil.IndexDropper;
import com.cvrskidz.jhop.indexutil.IndexInspector;
import com.cvrskidz.jhop.indexutil.IndexOperation;
import com.cvrskidz.jhop.db.IndexConnection;
import com.cvrskidz.jhop.exceptions.CommandException;
import java.util.List;

/**
 * A DatabaseIndexOperationFactory extends a generic IndexOperationFactory to
 * create operations that interact with indexes stored in databases.
 * 
 * @author cvrskidz 18031335
 */
public class DatabaseIndexOperationFactory extends IndexOperationFactory{
    private IndexConnection db;
    
    /**
     * Create a DatabaseIndexOpertaionFactory.
     * 
     * @param argv The arguments supplied to the command
     * @param call The name of the command
     * @param db The connected database
     */
    public DatabaseIndexOperationFactory(List<String> argv, String call, IndexConnection db) {
        super(argv, call);
        this.db = db;
    }
    
    @Override 
    public IndexOperation produce() throws CommandException {
        switch(super.getOperationName()) {
            case IndexDropper.OPNAME:
                return new DatabaseIndexDropper(super.getArgs(), db);
            case IndexReader.OPNAME:
                return new DatabaseIndexReader(super.getArgs(), db);
            case IndexWriter.OPNAME:
                return new DatabaseIndexWriter(super.getArgs(), db);
            case IndexInspector.OPNAME:
                return new DatabaseIndexInspector(super.getArgs(), db);
            default:
                return null;
        }
    }
}
