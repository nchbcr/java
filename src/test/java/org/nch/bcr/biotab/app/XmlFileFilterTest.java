package org.nch.bcr.biotab.app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.nch.bcr.biotab.clinical.app.BaseBiotaberApp;

/**
 * Author: Phillip Johnson
 * Created: 1/27/14.
 */
public class XmlFileFilterTest {

    AbstractBiotaber.XmlFileFilter filter;

    @Before
    public void setUp(){

    }

    @Test
    public void acceptClinical() throws Exception {

        filter = new AbstractBiotaber.XmlFileFilter(BaseBiotaberApp.XmlFileType.CLINICAL);

        //Regular Clinical XML
        File file = new File("nationwidechildrens.org_clinical.some-barcode.xml");
        assertTrue(filter.acceptTest(file));

        //OMF XML
        file = new File ("nationwidechildrens.org_omf.some-barcode.xml");
        assertFalse(filter.acceptTest(file));

        //SSF XML
        file = new File ("nationwidechildrens.org_ssf.some-barcode.xml");
        assertFalse(filter.acceptTest(file));

        //Biospecimen XML
        file = new File ("nationwidechildrens.org_biospecimen.some-barcode.xml");
        assertFalse(filter.acceptTest(file));

        //XML in the "invalid" folder
        file = new File ("directory/invalid/nationwidechildrens.org_clinical.some-barcode.xml");
        assertFalse(filter.acceptTest(file));

        //XML that had a warning attached
        file = new File ("nationwidechildrens.org_clinical.some-barcode.xml.HIGH");
        assertFalse(filter.acceptTest(file));
    }

    @Test
    public void acceptOmf() throws Exception {

        filter = new AbstractBiotaber.XmlFileFilter(BaseBiotaberApp.XmlFileType.OMF);

        //Regular Clinical XML
        File file = new File("nationwidechildrens.org_clinical.some-barcode.xml");
        assertFalse(filter.acceptTest(file));

        //OMF XML
        file = new File ("nationwidechildrens.org_omf.some-barcode.xml");
        assertTrue(filter.acceptTest(file));

        //SSF XML
        file = new File ("nationwidechildrens.org_ssf.some-barcode.xml");
        assertFalse(filter.acceptTest(file));

        //Biospecimen XML
        file = new File ("nationwidechildrens.org_biospecimen.some-barcode.xml");
        assertFalse(filter.acceptTest(file));

        //XML in the "invalid" folder
        file = new File ("directory/invalid/nationwidechildrens.org_clinical.some-barcode.xml");
        assertFalse(filter.acceptTest(file));

        //XML that had a warning attached
        file = new File ("nationwidechildrens.org_clinical.some-barcode.xml.HIGH");
        assertFalse(filter.acceptTest(file));
    }

    @Test
    public void acceptSsf() throws Exception {

        filter = new AbstractBiotaber.XmlFileFilter(BaseBiotaberApp.XmlFileType.SSF);

        //Regular Clinical XML
        File file = new File("nationwidechildrens.org_clinical.some-barcode.xml");
        assertFalse(filter.acceptTest(file));

        //OMF XML
        file = new File ("nationwidechildrens.org_omf.some-barcode.xml");
        assertFalse(filter.acceptTest(file));

        //SSF XML
        file = new File ("nationwidechildrens.org_ssf.some-barcode.xml");
        assertTrue(filter.acceptTest(file));

        //Biospecimen XML
        file = new File ("nationwidechildrens.org_biospecimen.some-barcode.xml");
        assertFalse(filter.acceptTest(file));

        //XML in the "invalid" folder
        file = new File ("directory/invalid/nationwidechildrens.org_clinical.some-barcode.xml");
        assertFalse(filter.acceptTest(file));

        //XML that had a warning attached
        file = new File ("nationwidechildrens.org_clinical.some-barcode.xml.HIGH");
        assertFalse(filter.acceptTest(file));
    }
   
}
