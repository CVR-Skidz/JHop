package com.cvrskidz.jhop.db.indexutil;

import com.cvrskidz.jhop.exceptions.CommandException;
import com.cvrskidz.jhop.executables.indexutil.*;
import java.util.List;

public class DatabaseIndexOperationFactory extends IndexOperationFactory{
    public DatabaseIndexOperationFactory(List<String> argv, String call) {
        super(argv, call);
    }
    
    @Override 
    public IndexOperation produce() throws CommandException {
        switch(super.getOperationName()) {
            case IndexDropper.OPNAME:
                return new DatabaseIndexDropper(super.getArgs());
            case IndexReader.OPNAME:
                return new DatabaseIndexReader(super.getArgs());
            case IndexWriter.OPNAME:
                return new DatabaseIndexWriter(super.getArgs());
            case IndexInspector.OPNAME:
                return new DatabaseIndexInspector(super.getArgs());
            default:
                return null;
        }
    }
}
