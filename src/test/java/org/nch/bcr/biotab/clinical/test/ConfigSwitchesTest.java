package org.nch.bcr.biotab.clinical.test;

import java.text.MessageFormat;

import org.nch.bcr.biotab.app.BioTabFileDTO;
import org.nch.bcr.biotab.clinical.test.driver.TestableClinicalBiotaber;
import org.nch.bcr.biotab.domain.table.Cell;
import org.nch.bcr.biotab.domain.table.HeaderCell;
import org.nch.bcr.biotab.domain.table.Row;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * System tests on the XSDs themselves. These are testing rules/conventions that we have on the structure of the XSDs. 
 *
 * <PRE>
 *  Execution order of commonly used annotations of TestNG
 *  
 *  @BeforeTest -- The annotated method will be run before any test method belonging to the classes inside the <test> tag is run. 
 *  @BeforeClass -- The annotated method will be run before the first test method in the current class is invoked. 
 *  @BeforeMethod --  The annotated method will be run before *each* test method. 
 *  @Test 
 *  @AfterMethod --  The annotated method will be run after *each( test method. 
 *  @AfterClass -- The annotated method will be run after all the test methods in the current class have been run. 
 *  @AfterTest --  The annotated method will be run after all the test methods belonging to the classes inside the <test> tag have run. 
 *  
 * </PRE>
 * 
 * @author John Deardurff
 *
 */
@Test(enabled=true, groups={"BioTabs"})
public class ConfigSwitchesTest extends BaseTest {
	
	
	
	@Test(enabled=true, dataProvider="dataProviderMethod", groups={"BioTabs"})
	public void testAppendDisplayOrderToHeader(String mode, String xsdMajorVersion, String xsdMinorVersion, String xsdPatchNumber, String dslBuildNumber) throws Exception {
		
		// Tell the test in which study the sample/mocked xml is located. This can be a : separated 
		// list of disease codes. 
		String diseaseCode = "brca";
		
		// Make sure that all of the relevant properties have been initialized for this specific test. 
		// For these tests we have only one property file for all of the tests, as opposed to a property 
		// file for each test. So, we are basically setting many of the properties programmatically. 
		System.getProperties().setProperty("clinical.biotab.write.empty.file", "false");
		System.getProperties().setProperty("clinical.biotab.include.empty.columns", "true");
		System.getProperties().setProperty("clinical.biotab.sort.by.display.order", "true");
		System.getProperties().setProperty("clinical.biotab.set.procurment.status.as.cell.value", "true");
		System.getProperties().setProperty("clinical.biotab.barcode.always.first.column", "true");
		System.getProperties().setProperty("clinical.biotab.append.display.order.to.header", "true");
		System.getProperties().setProperty("clinical.xml.input.path", System.getProperties().getProperty("clinical.xml.input.path") + "\\test_switches\\append.display.order.to.header");
		
		TestableClinicalBiotaber biotaber = new TestableClinicalBiotaber(System.getProperties(), diseaseCode.toUpperCase());
		biotaber.run();
		
		// Make sure the is a displayOrder for each and every header cell. This test will fail on the first header cell 
		// that is finds that does not pass this test. 
		for (BioTabFileDTO bioTabFileDTO : biotaber.getBioTabFileSet()) {
			for (HeaderCell headerCell : bioTabFileDTO.getHeader()) {
				String cellValue = bioTabFileDTO.getHeader().getCellValue(biotaber.getJobDTO(), headerCell);
				// Now we make sure that the cell value is of the format ALPHA:NUMBER, which is enough to test
				// that there is a display order postfixed to the preferred name. 
				String preferredName = null;
				String displayOrder = null;
				if (cellValue != null) {
					String[] parts = cellValue.split(":");
					if (parts.length == 2) {
						if (!parts[0].trim().equals("")) {
							preferredName = parts[0];
						}
						if (!parts[1].trim().equals("")) {
							displayOrder = parts[1];
						}
					}
				}
				Assert.assertNotNull( preferredName, MessageFormat.format("-- HeaderCell does NOT have a postfixed displayOrder when it should for [Study={0}], [Section={1}]. -- ", diseaseCode, bioTabFileDTO.getSectionToFileMapNode().getSectionId() ));
				Assert.assertNotNull( displayOrder, MessageFormat.format("-- HeaderCell does NOT have a postfixed displayOrder when it should for [Study={0}], [Section={1}]. -- ", diseaseCode, bioTabFileDTO.getSectionToFileMapNode().getSectionId() ));
			}
			
		}
		
		// Now, make sure that if we turn this property/switch off then the display 
		// order value will no longer be prefixed to the header cell. 
		System.getProperties().setProperty("clinical.biotab.append.display.order.to.header", "false");
		
		biotaber = new TestableClinicalBiotaber(System.getProperties(), diseaseCode.toUpperCase());
		biotaber.run();
		
		// Make sure the is a displayOrder for each and every header cell. This test will fail on the first header cell 
		// that is finds that does not pass this test. 
		for (BioTabFileDTO bioTabFileDTO : biotaber.getBioTabFileSet()) {
			for (HeaderCell headerCell : bioTabFileDTO.getHeader()) {
				String cellValue = bioTabFileDTO.getHeader().getCellValue(biotaber.getJobDTO(), headerCell);
				Assert.assertFalse(cellValue.contains(":"), MessageFormat.format("-- HeaderCell has a postfixed displayOrder when it should NOT for [Study={0}], [Section={1}], -- ", diseaseCode, bioTabFileDTO.getSectionToFileMapNode().getSectionId() ));
			}
		}
	
	}
	
	@Test(enabled=true, dataProvider="dataProviderMethod", groups={"BioTabs"})
	public void testBarcodeAlwaysFirstColumn(String mode, String xsdMajorVersion, String xsdMinorVersion, String xsdPatchNumber, String dslBuildNumber) throws Exception {
		// Make sure that for every generated biotab, the display name of the first header cell contains 
		// the work 'barcode' and also make sure that every cell of the first column is not the empty string. 
		// Tell the test in which study the sample/mocked xml is located. This can be a : separated 
		// list of disease codes. 
		String diseaseCode = "brca";
		
		// Make sure that all of the relevant properties have been initialized for this specific test. 
		// For these tests we have only one property file for all of the tests, as opposed to a property 
		// file for each test. So, we are basically setting many of the properties programmatically. 
		System.getProperties().setProperty("clinical.biotab.write.empty.file", "false");
		System.getProperties().setProperty("clinical.biotab.include.empty.columns", "true");
		System.getProperties().setProperty("clinical.biotab.sort.by.display.order", "true");
		System.getProperties().setProperty("clinical.biotab.set.procurment.status.as.cell.value", "true");
		System.getProperties().setProperty("clinical.biotab.barcode.always.first.column", "true");
		System.getProperties().setProperty("clinical.biotab.append.display.order.to.header", "true");
		System.getProperties().setProperty("clinical.xml.input.path", System.getProperties().getProperty("clinical.xml.input.path") + "\\test_switches\\barcode.always.first.column");
		
		TestableClinicalBiotaber biotaber = new TestableClinicalBiotaber(System.getProperties(), diseaseCode.toUpperCase());
		biotaber.run();
		
		// Make sure the first header cell of each and every table is the barcode.  
		for (BioTabFileDTO bioTabFileDTO : biotaber.getBioTabFileSet()) {
			HeaderCell headerCell = bioTabFileDTO.getHeader().getHeaderCellAt(0);
			Assert.assertTrue(bioTabFileDTO.getHeader().getCellValue(biotaber.getJobDTO(), headerCell).contains("barcode"), 
					MessageFormat.format("-- The first header cell is not for the barcode when it should for [Study={0}], [Section={1}]. -- ", diseaseCode, bioTabFileDTO.getSectionToFileMapNode().getSectionId() ));
		}
		// Make sure that each cell of the first column of each and every table has data. 
		for (BioTabFileDTO bioTabFileDTO : biotaber.getBioTabFileSet()) {
			for (Row<Cell> row : bioTabFileDTO.getTable()) {
				Assert.assertNotEquals("", row.getCellAt(bioTabFileDTO.getHeader().getHeaderCellAt(0)).getValue().trim(), 
					MessageFormat.format("-- All barcode cells must have data for [Study={0}], [Section={1}]. -- ", diseaseCode, bioTabFileDTO.getSectionToFileMapNode().getSectionId() ));
			}
		}
	}
	
	@Test(enabled=false, dataProvider="dataProviderMethod", groups={"BioTabs"})
	public void testIncludeEmptyColumns(String mode, String xsdMajorVersion, String xsdMinorVersion, String xsdPatchNumber, String dslBuildNumber) throws Exception {
		// Make sure that for every generated biotab, the display name of the first header cell contains 
		// the work 'barcode' and also make sure that every cell of the first column is not the empty string. 
		// Tell the test in which study the sample/mocked xml is located. This can be a : separated 
		// list of disease codes. 
		String diseaseCode = "brca";
		
		// Make sure that all of the relevant properties have been initialized for this specific test. 
		// For these tests we have only one property file for all of the tests, as opposed to a property 
		// file for each test. So, we are basically setting many of the properties programmatically. 
		System.getProperties().setProperty("clinical.biotab.write.empty.file", "false");
		System.getProperties().setProperty("clinical.biotab.include.empty.columns", "false");
		System.getProperties().setProperty("clinical.biotab.sort.by.display.order", "true");
		System.getProperties().setProperty("clinical.biotab.set.procurment.status.as.cell.value", "true");
		System.getProperties().setProperty("clinical.biotab.barcode.always.first.column", "true");
		System.getProperties().setProperty("clinical.biotab.append.display.order.to.header", "true");
		System.getProperties().setProperty("clinical.xml.input.path", System.getProperties().getProperty("clinical.xml.input.path") + "\\test_switches\\include.empty.columns");
		
		TestableClinicalBiotaber biotaber = new TestableClinicalBiotaber(System.getProperties(), diseaseCode.toUpperCase());
		biotaber.run();
		
		for (BioTabFileDTO bioTabFileDTO : biotaber.getBioTabFileSet()) {
			HeaderCell headerCell = bioTabFileDTO.getHeader().getHeaderCellAt(0);
			Assert.assertFalse(bioTabFileDTO.getTable().isColumnEmpty(headerCell), "There can NOT be a column that is totally empty.");
		}
	}
	
	@Test(enabled=false, dataProvider="dataProviderMethod", groups={"BioTabs"})
	public void testSetProcurmentStatusAsCellValue(String mode, String xsdMajorVersion, String xsdMinorVersion, String xsdPatchNumber, String dslBuildNumber) throws Exception {
		// For this test we have mocked up an XML file such that the vital_status element in the patient section 
		// has a procurment status of NOT_COMPLETED. 
		String diseaseCode = "brca";
		// Make sure that all of the relevant properties have been initialized for this specific test. 
		// For these tests we have only one property file for all of the tests, as opposed to a property 
		// file for each test. So, we are basically setting many of the properties programmatically. 
		System.getProperties().setProperty("clinical.biotab.write.empty.file", "false");
		System.getProperties().setProperty("clinical.biotab.include.empty.columns", "true");
		System.getProperties().setProperty("clinical.biotab.sort.by.display.order", "true");
		System.getProperties().setProperty("clinical.biotab.set.procurment.status.as.cell.value", "true");
		System.getProperties().setProperty("clinical.biotab.barcode.always.first.column", "true");
		System.getProperties().setProperty("clinical.biotab.append.display.order.to.header", "true");
		System.getProperties().setProperty("clinical.xml.input.path", System.getProperties().getProperty("clinical.xml.input.path") + "\\test_switches\\set.procurment.status.as.cell.value");
		
		// We will locate the vital_status column via xpath. First we get the HeaderCell via xpath and then we can get
		// all of the vital_status cell via this instance of HeaderCell. 
		//String xpath = "//*[local-name()='tcga_bcr' and namespace-uri()='http://tcga.nci/bcr/xml/clinical" + diseaseCode + "/2.7']/*[local-name()='patient' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/" + diseaseCode + "/2.7']/*[local-name()='vital_status' and namespace-uri()='http://tcga.nci/bcr/xml/clinical/shared/2.7]";
	
		TestableClinicalBiotaber biotaber = new TestableClinicalBiotaber(System.getProperties(), diseaseCode.toUpperCase());
		biotaber.run();
		
		BioTabFileDTO bioTabFileDTO = biotaber.getBioTabFileDTO(diseaseCode, "patient");
		// Find the vital_status header. 
		for (HeaderCell headerCell : bioTabFileDTO.getHeader()) {
			// Locate the vital_status HeaderCell via the elementName, since we know that there is only one of them. If there were 
			// two XML element whose name is the same, then we would need to get these via the xpath. 
			if ("vital_status".equals(headerCell.getElementName())) {
				for (Row row : bioTabFileDTO.getTable()) {
					Assert.assertTrue(row.getCellAt(headerCell).getValue().contains("]"), "The vital_status element does not contain the procurment status value as it should.");
					Assert.assertTrue(row.getCellAt(headerCell).getValue().contains("["), "The vital status element does not contain the procurment status value as it should.");
				}
			}
		}
		
		System.getProperties().setProperty("clinical.biotab.set.procurment.status.as.cell.value", "false");
		biotaber = new TestableClinicalBiotaber(System.getProperties(), diseaseCode.toUpperCase());
		biotaber.run();
		
		bioTabFileDTO = biotaber.getBioTabFileDTO(diseaseCode, "patient");
		// Find the vital_status header. 
		for (HeaderCell headerCell : bioTabFileDTO.getHeader()) {
			// Locate the vital_status HeaderCell via the elementName, since we know that there is only one of them. If there were 
			// two XML element whose name is the same, then we would need to get these via the xpath. 
			if ("vital_status".equals(headerCell.getElementName())) {
				for (Row row : bioTabFileDTO.getTable()) {
					Assert.assertFalse(row.getCellAt(headerCell).getValue().contains("]"), "The vital_status element contains the procurment status value but it should NOT.");
					Assert.assertFalse(row.getCellAt(headerCell).getValue().contains("["), "The vital_status element contains the procurment status value but it should NOT.");
				}
			}
		}
	}
	
	@Test(enabled=false, dataProvider="dataProviderMethod", groups={"BioTabs"})
	public void testSortByDisplayOrder(String mode, String xsdMajorVersion, String xsdMinorVersion, String xsdPatchNumber, String dslBuildNumber) throws Exception {
		// For this test we have mocked up an XML file such that the vital_status element in the patient section 
		// has a procurment status of NOT_COMPLETED. 
		String diseaseCode = "brca";
		// Make sure that all of the relevant properties have been initialized for this specific test. 
		// For these tests we have only one property file for all of the tests, as opposed to a property 
		// file for each test. So, we are basically setting many of the properties programmatically. 
		System.getProperties().setProperty("clinical.biotab.write.empty.file", "false");
		System.getProperties().setProperty("clinical.biotab.include.empty.columns", "true");
		System.getProperties().setProperty("clinical.biotab.sort.by.display.order", "true");
		System.getProperties().setProperty("clinical.biotab.set.procurment.status.as.cell.value", "true");
		System.getProperties().setProperty("clinical.biotab.barcode.always.first.column", "true");
		System.getProperties().setProperty("clinical.biotab.append.display.order.to.header", "true");
		System.getProperties().setProperty("clinical.xml.input.path", System.getProperties().getProperty("clinical.xml.input.path") + "\\test_switches\\set.procurment.status.as.cell.value");
		
		TestableClinicalBiotaber biotaber = new TestableClinicalBiotaber(System.getProperties(), diseaseCode.toUpperCase());
		biotaber.run();
		
		HeaderCell ageAtDiagnosisHeaderCell = null;
		int ageAtDiagnosisHeaderCellIndex = 0;
		HeaderCell genderHeaderCell = null;
		int genderHeaderCellIndex = 0;
		
		BioTabFileDTO bioTabFileDTO = biotaber.getBioTabFileDTO(diseaseCode, "patient");
		// Find the vital_status header. 
		for (HeaderCell headerCell : bioTabFileDTO.getHeader()) {
			// Locate the vital_status HeaderCell via the elementName, since we know that there is only one of them. If there were 
			// two XML element whose name is the same, then we would need to get these via the xpath. 
			if ("age_at_diagnosis".equals(headerCell.getElementName())) {
				ageAtDiagnosisHeaderCell = headerCell;
			}
			if ("gender".equals(headerCell.getElementName())) {
				genderHeaderCell = headerCell;
			}
			if (ageAtDiagnosisHeaderCell == null) {
				ageAtDiagnosisHeaderCellIndex++;
			}
			if (genderHeaderCell == null) {
				genderHeaderCellIndex++;
			}
		}
		Assert.assertNotNull(ageAtDiagnosisHeaderCellIndex, "age_at_diagnosis HeaderCell could not be found.");
		Assert.assertNotNull(genderHeaderCell, "gender HeaderCell could not be found.");
		Assert.assertTrue(genderHeaderCellIndex < ageAtDiagnosisHeaderCellIndex, "gender HeaderCell is NOT sorted before age_at_diagnosis HeaderCell.");
		
		// Now turn off the sort.by.display.order switch so that the headers will be sorted alpha-numerically. 
		System.getProperties().setProperty("clinical.biotab.sort.by.display.order", "false");
		
		biotaber = new TestableClinicalBiotaber(System.getProperties(), diseaseCode.toUpperCase());
		biotaber.run();
		
		ageAtDiagnosisHeaderCell = null;
		ageAtDiagnosisHeaderCellIndex = 0;
		genderHeaderCell = null;
		genderHeaderCellIndex = 0;
		
		bioTabFileDTO = biotaber.getBioTabFileDTO(diseaseCode, "patient");
		// Find the vital_status header. 
		for (HeaderCell headerCell : bioTabFileDTO.getHeader()) {
			// Locate the vital_status HeaderCell via the elementName, since we know that there is only one of them. If there were 
			// two XML element whose name is the same, then we would need to get these via the xpath. 
			if ("age_at_diagnosis".equals(headerCell.getElementName())) {
				ageAtDiagnosisHeaderCell = headerCell;
			}
			if ("gender".equals(headerCell.getElementName())) {
				genderHeaderCell = headerCell;
			}
			if (ageAtDiagnosisHeaderCell == null) {
				ageAtDiagnosisHeaderCellIndex++;
			}
			if (genderHeaderCell == null) {
				genderHeaderCellIndex++;
			}
		}
		
		Assert.assertNotNull(ageAtDiagnosisHeaderCellIndex, "age_at_diagnosis HeaderCell could not be found.");
		Assert.assertNotNull(genderHeaderCell, "gender HeaderCell could not be found.");
		Assert.assertTrue(ageAtDiagnosisHeaderCellIndex < genderHeaderCellIndex, "age_at_diagnosis HeaderCell is NOT sorted before gender HeaderCell.");
	}
	
	@Test(enabled=false, dataProvider="dataProviderMethod", groups={"BioTabs"})
	public void testWriteEmptyFile(String mode, String xsdMajorVersion, String xsdMinorVersion, String xsdPatchNumber, String dslBuildNumber) {
		// TODO: it might not be practical to implement a unit test for this. 
	}
	
}
