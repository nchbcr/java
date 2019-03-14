package nci.tcga.bcr.beans;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Author: Alyssa Janning
 * Created: 4/7/2014.
 */
public class TableTest {

    @Test
    public void emptyTableIsInvalid(){
        Table t = new Table();
        assertFalse(t.isValid());
    }
    
    @Test
    public void filledTableIsValid(){
    	Table t = new Table();
    	Column c = new Column();
        Cell cell = new Cell();
        cell.setCde("1234");
        cell.setDisplayOrder("999");
        cell.setElementName("element");
        cell.setElementValue("value");
        cell.setIsRestricted(false);
        cell.setPreferredName("element_preferred");
        cell.setProcurementStatus("Completed");
        cell.setSchemaVersion("2.6");
    	c.add(cell);
    	t.addColumn(c);
    	
    	assertTrue(t.isValid());
    }
    
    
    @Test
    public void columnsDifferentNumberCells(){
        Table t = new Table();
        Column c1 = new Column();
        Column c2 = new Column();
        c1.add(new Cell());
        c2.add(new Cell());
        c2.add(new Cell());
        t.addColumn(c1);
        t.addColumn(c2);
        assertFalse(t.isValid());
    }
    
    @Test
    public void columnsSameNumberCells(){
        Table t = new Table();
        Column c1 = new Column();
        Column c2 = new Column();
        Cell cell = new Cell();
        cell.setCde("1234");
        cell.setDisplayOrder("999");
        cell.setElementName("element");
        cell.setElementValue("value");
        cell.setIsRestricted(false);
        cell.setPreferredName("element_preferred");
        cell.setProcurementStatus("Completed");
        cell.setSchemaVersion("2.6");
        c1.add(cell);
        c2.add(cell);
        t.addColumn(c1);
        t.addColumn(c2);
        assertTrue(t.isValid());
    }
    
    @Test
    public void columnsHaveCells(){
    	Table t = new Table();
    	Column c = new Column();
        Cell cell = new Cell();
        cell.setCde("1234");
        cell.setDisplayOrder("999");
        cell.setElementName("element");
        cell.setElementValue("value");
        cell.setIsRestricted(false);
        cell.setPreferredName("element_preferred");
        cell.setProcurementStatus("Completed");
        cell.setSchemaVersion("2.6");
    	c.add(cell);
    	t.addColumn(c);
    	assertTrue(t.isValid());
    }
    
    @Test
    public void columnsDoNotHaveCells(){
    	Table t = new Table();
    	Column c = new Column();
    	t.addColumn(c);
    	assertFalse(t.isValid());
    }

}
