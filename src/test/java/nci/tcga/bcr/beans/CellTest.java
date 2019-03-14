package nci.tcga.bcr.beans;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Phillip Johnson
 * Created: 4/7/2014.
 */
public class CellTest {

    @Test
    public void emptyCellIsInvalid(){
        Cell c = new Cell();
        assertFalse(c.isValid());
    }

    @Test
    public void cdeNotRequired() {
        Cell c = new Cell();
        c.setElementValue("123");
        c.setProcurementStatus("Complete");
        c.setIsRestricted(true);
        c.setElementName("name");
        c.setDisplayOrder("1");
        c.setCde("859375");
        c.setSchemaVersion("3");
        c.setPreferredName("sdfhjds");
        assertTrue(c.isValid());

        c = new Cell();
        c.setElementValue("123");
        c.setProcurementStatus("Complete");
        c.setIsRestricted(true);
        c.setElementName("name");
        c.setDisplayOrder("1");
        c.setCde(null);
        c.setSchemaVersion("3");
        c.setPreferredName("sdfhjds");
        assertTrue(c.isValid());
    }

}
