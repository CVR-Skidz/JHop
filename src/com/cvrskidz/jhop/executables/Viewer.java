package com.cvrskidz.jhop.executables;
import com.cvrskidz.jhop.indexes.Index;
import java.util.List;

/**
 * @author cvr-skidz bcc9954 18031335
 */
public class Viewer extends Operation{
    public static final String OPNAME = "--show";
    private final static int PRIORITY = 2;
    private String title;
    
    public Viewer(List<String> argv) {
        super(argv, OPNAME);
        priority = PRIORITY;
        title = argv.get(0);
    }
    
    @Override
    public Index exec(Index index) {
        //stub
        System.out.println("Page of " + title);
        return index;
    }
}
