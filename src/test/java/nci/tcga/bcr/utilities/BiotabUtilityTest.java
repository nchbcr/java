package nci.tcga.bcr.utilities;

import nci.tcga.bcr.errors.BiotabInitializationException;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

/**
 * Author: Phillip Johnson
 * Created: 4/14/2014.
 */
public class BiotabUtilityTest {

    @Test
    public void canAccessXsdFromJar() throws Exception {

        InputStream is = BiotabUtility.streamResourceFromClasspath("schemaorg_apache_xmlbeans", "TCGA_BCR.Administration.xsd");

        if(is==null){
            Assert.fail();
        }
    }

    @Test(expected = BiotabInitializationException.class)
    public void ExceptionThrowsWhenXsdNotPresent() throws Exception {
        BiotabUtility.streamResourceFromClasspath("schemaorg_apache_xmlbeans","some_fake_xsd.xsd");
    }
}
