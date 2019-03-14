package nci.tcga.bcr.beans;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Alyssa Janning
 * Created: 6/13/2014.
 */
public class ColumnTest {

    @Test
    public void emptyColumnIsInvalid(){
        Column cl = new Column();
        assertFalse(cl.isValid());
    }
    
    @Test
    public void filledColumnIsValid(){
    	Column cl = new Column();
        Cell cell = new Cell();
        cell.setCde("1234");
        cell.setDisplayOrder("999");
        cell.setElementName("element");
        cell.setElementValue("value");
        cell.setIsRestricted(false);
        cell.setPreferredName("element_preferred");
        cell.setProcurementStatus("Completed");
        cell.setSchemaVersion("2.6");
        cl.add(cell);
    	assertTrue(cl.isValid());
    }
}
