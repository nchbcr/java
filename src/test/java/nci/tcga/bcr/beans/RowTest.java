package nci.tcga.bcr.beans;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Alyssa Janning
 * Created: 6/13/2014.
 */
public class RowTest {

    @Test
    public void emptyRowIsInvalid(){
        Row r = new Row();
        assertFalse(r.isValid());
    }
    
    @Test
    public void filledRowIsValid(){
    	Row r = new Row();
    	r.add(new Cell());
    	
    	assertTrue(r.isValid());
    }
}