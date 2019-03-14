/**
 * 
 */
package nci.tcga.bcr.process;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import nci.tcga.bcr.errors.BiotabInitializationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.sun.xml.xsom.XSAttributeDecl;
import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSContentType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSTerm;
import com.sun.xml.xsom.parser.XSOMParser;

import nci.tcga.bcr.beans.Column;
import nci.tcga.bcr.beans.Row;
import nci.tcga.bcr.beans.StringDate;
import nci.tcga.bcr.beans.Table;
import nci.tcga.bcr.beans.Cell;
import nci.tcga.bcr.errors.DateFatalException;
import nci.tcga.bcr.utilities.BiotabUtility;
import nci.tcga.bcr.utilities.CustomComparator;
import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.XPathContext;

/**
 * @author axk058
 * 
 */
public class BiotabProcess {

	private List<String> exclusions = null;
	private List<String> inclusions = null;
	private List<String> inclusionsNested = null;
	private List<String> exclusionsNested = null;
    private String xmlFile = null;
	private String day = "01";
	private String month = "01";
	private Document doc;
	private Document docSchema;
	private boolean hasCdeRow = false;
	private boolean append = false;
	private int counter = 100;
	private XPathContext context = null;
	private String section = null;
	private boolean isCntl = false;
	private boolean isAux = false;
	private Logger logger = null;
	private List<String> auxDiseases = null;
	private String outFiles = null;
	private XSSchemaSet schemaSet = null;
	private Builder parser = new Builder();
	private HashMap<String, String> nsToSchemaLocation = new HashMap<String, String>();
	private String target = null;
	private boolean isDiffSection = true;
	private List<Row> schemaRows = null;
	private File biotabFile = null;
	private File biotabDir = null;
	private String diseaseCode = null;
	private List<String> diseaseCodes = null;
	private String section0 = null;
	private int fileCount = 0;
	private List<String> sections = null;
	private List<String> topStackFolders = null;
	private Set<File> topFiles = new HashSet<File>();
	private boolean hasDiseaseCodes = false;
	private boolean includeDisplayOrder = false;
	private List<File> files = new ArrayList<File>();
	private List<String> fileTypeList = Arrays.asList("biospecimen","auxiliary","control");

    public BiotabProcess(List<String> topStackFolders) {
		this.topStackFolders = topStackFolders;
	}

	/**
	 * Main method for parsing the elements and construct the file layouts.
	 * 
	 * @param table     Table to begin processing on
	 * @return table object
	 */
	private Table startProcessing(Table table) {
		try {
			// Builder parser = new Builder();
			FileInputStream xml = new FileInputStream(new File(xmlFile));
			doc = parser.build(xml);

			if (context != null && context.lookup("admin") != null) {
				determineDiseaseCode();
				biotabDir = new File(outFiles + diseaseCode.toUpperCase());
			}

			// No need to parse the same schema again until next section. Note:
			// This is necessary
			// because of the auxiliary and control schemas which are different.
			// And there might
			// be more in the future.
			if (isDiffSection) {
				schemaRows = new ArrayList<Row>();

				context = new XPathContext();
				context.addNamespace("xs", "http://www.w3.org/2001/XMLSchema");
				context.addNamespace("xsi",
						"http://www.w3.org/2001/XMLSchema-instance");

				String schemaLocation = determineSchemaLocation();
                String schemeaFileName = schemaLocation.substring(schemaLocation.lastIndexOf('/') + 1);
                InputStream xsdStream = BiotabUtility.streamResourceFromClasspath("schemaorg_apache_xmlbeans",schemeaFileName);
                docSchema = parser.build(xsdStream);
				target = determineTargetNamespace();

				if (!nsToSchemaLocation.containsKey(target)) {
					nsToSchemaLocation.put(target, schemaLocation);
				}

				List<String> targetNamespaces;

                //Get a fresh stream for the target namespace parser
                xsdStream = BiotabUtility.streamResourceFromClasspath("schemaorg_apache_xmlbeans",schemeaFileName);
                targetNamespaces = getNamespaceList(xsdStream);

				context = BiotabUtility.generateXPathContext(context,
                        targetNamespaces);

				section0 = section;

				if (diseaseCode == null) {
					determineDiseaseCode();
					biotabDir = new File(outFiles + diseaseCode.toUpperCase()
							+ BiotabUtility.FILE_SEPARATOR);
				}

				if (isCntl) {
					section = "aliquot";
				} else if (isAux) {
					if (auxDiseases.contains(diseaseCode)) {
						section0 = "auxiliary";
						section = "patient";
					}
				}

                //logger.debug("table print pre getXSD \n" + table.printTable());
				table = getXsdElements(table);

                //logger.debug("table print pre addInclusion \n" + table.printTable());
				table = addInclusionElements(table);

                //logger.debug("table print pre CDE \n" + table.printTable());
				if (hasCdeRow) {
					table = createSecondRow(table);
				}

				Row row1 = new Row();
				Row row2 = new Row();

				for (int x = 0; x < table.numberOfColumns(); x++) {
					row1.add(table.get(x).get(0));
					row2.add(table.get(x).get(1));
				}

				schemaRows.add(row1);
				schemaRows.add(row2);

				isDiffSection = false;
			}

			if (table.numberOfColumns() == 0) {
				table.addRow(schemaRows.get(0));
				table.addRow(schemaRows.get(1));
			}

			// logger.debug("Number of threads running: " +
			// Thread.activeCount());
			
			//logger.debug("table print pre process\n" + table.printTable());
			
			table = processXml(table);
			
			logger.debug("table print post\n" + table.printTable());
			
			// Do not generate empty files
			if (table.numberOfRows() > 2) {
                //BCRI-3138: Process all tables, not just those with >3 rows
                table = processParentElements(table);
				
				logger.debug("table print post2\n" + table.printTable());

				List<Column> columns = table.asColumns();
				Collections.sort(columns, new CustomComparator());

                if(isAux){
                    columns = trimEmptyColumns(columns);
                } else {
                    columns = removeExcludedColumns(columns);
                }

                String filePrefix = (String)BiotabUtility.getPropValueFromProperties(BiotabUtility.prop,"outfile.prefix");

				if (biotabFile == null) 
				{
					if (isAux) {
						if (auxDiseases.contains(diseaseCode)) 
							biotabFile = new File(biotabDir + "/" + filePrefix + "auxiliary_" + diseaseCode + ".txt");
					}
					else if (isCntl) 
						biotabFile = new File(biotabDir + "/" + filePrefix + "control_cntl.txt");
					else 
						biotabFile = new File(biotabDir + "/" + filePrefix + "biospecimen_" + section0 + "_" + diseaseCode + ".txt");
				}

				if (!biotabDir.exists()) {
					biotabDir.mkdirs();
				}

				boolean isMerge = false;

				if ( biotabFile != null )
				{
					if (biotabFile.exists()) {
						isMerge = true;
	                    //Don't trim if this is the first file processing.
	                    //It is possible that the first file has no data if it is out of sync
	                    //with current XSD
	                    columns = trimEmptyColumns(columns);
					}					
	
					PrintWriter outBio = new PrintWriter(new FileWriter(biotabFile, append));
					writeTableToExternal(outBio, columns, isMerge);
					outBio.close();
				}
			}

		} catch (IOException | ParsingException | BiotabInitializationException e) {
			logger.error("May not have written completely For Biotab: "+biotabFile);
			logger.fatal(e.getMessage());
		} finally {
			biotabFile = null;
			diseaseCode = null;
			table = new Table();
		}
		
		return table;
	}

	/**
	 * Main method to be called for processing.
	 * 
	 * This method is responsible for initialization the log file, define the
	 * processing sections for the files, read properties files, and starts
	 * walking through the directory structure looking for XML files.
	 */
	@SuppressWarnings("unchecked")
	public void process(String[] args) {
		Date start = new Date();
		long startTime = start.getTime();
		
		BiotabUtility.init(args);

		logger = BiotabUtility.initLogger(BiotabProcess.class.getName(),
				BiotabUtility.prop_loc);

		logger.info("Process begins.....");

		boolean useDataWarehouse = (Boolean) BiotabUtility.getPropValueFromProperties(BiotabUtility.prop, "data_warehouse.query");
		boolean isAppDetTop = true;

		if (topStackFolders == null) {
			if (useDataWarehouse) {
				this.topStackFolders = getTopStackFilenamesFromDB();
				if (this.topStackFolders.size() > 0) {
					for (String name : this.topStackFolders) {
						topFiles.add(new File(name));
					}
				}
			}

			isAppDetTop = false;
		}

		// The second row of the files with the CDE of the elements
		String propKey = "cderow.appears";
		hasCdeRow = (Boolean) BiotabUtility.getPropValueFromProperties(
				BiotabUtility.prop, propKey);

		// Whether or not the files should append to themselves, and they should
		// except
		// for special occasions
		propKey = "outfile.append";
		append = (Boolean) BiotabUtility.getPropValueFromProperties(
				BiotabUtility.prop, propKey);
		
		propKey = "display.display_order";
		includeDisplayOrder = (Boolean) BiotabUtility.getPropValueFromProperties(
				BiotabUtility.prop, propKey);

		// XML files location
		propKey = "xmlfiles.in";
		String inFiles = (String) BiotabUtility.getPropValueFromProperties(
				BiotabUtility.prop, propKey);

		// Output location needed to be updated
		propKey = "biotabfiles.out";
		outFiles = (String) BiotabUtility.getPropValueFromProperties(
				BiotabUtility.prop, propKey);

		// Disease code
		propKey = "disease.codes";
		diseaseCodes = (List<String>) BiotabUtility.getPropValueFromProperties(
				BiotabUtility.prop, propKey);

		if (diseaseCodes != null && diseaseCodes.size() > 0) {
			hasDiseaseCodes = true;
		}
		else {
			hasDiseaseCodes = false;
		}

		// Properties prop =
		// BiotabUtility.readPropetiesFile("../tcga_bcr_biotab/src/main/resources/biotab.properties");
		propKey = "biotab.properties";
		String biotabProp = (String) BiotabUtility.getPropValueFromProperties(
				BiotabUtility.prop, propKey); 
		Properties prop = BiotabUtility.readPropetiesFile(biotabProp);

		// List of disease code which contain auxiliary files
		propKey = "auxiliary.disease_codes";
		auxDiseases = (List<String>) BiotabUtility.getPropValueFromProperties(
				prop, propKey);

		// Biotab files
		propKey = "sections";
		sections = (List<String>) BiotabUtility.getPropValueFromProperties(
				prop, propKey);
		// List<String> sections = {"biospecimen_cqcf"};

		String prevSection = null;
		String fileType = null;

		if (topFiles.isEmpty()) 
		{
			File[] listFiles = new File(inFiles).listFiles();
			loopThruDirs(listFiles);
		}
		
		for (String section1 : sections) 
		{
			Table table = new Table();
			this.section = section1;

			if (prevSection == null || !prevSection.equals(section)) 
			{
				if (section.equals("cntl")) {
					isCntl = true;
					fileType = "control";
				} else if (section.equals("auxiliary")) {
					isAux = true;
					fileType = "auxiliary";
				} else
					fileType = "biospecimen";

				propKey = section + ".exclusions";
				exclusions = (List<String>) BiotabUtility
						.getPropValueFromProperties(prop, propKey);

				propKey = section + ".inclusions";
				inclusions = (List<String>) BiotabUtility
						.getPropValueFromProperties(prop, propKey);
				
				propKey = section + ".inclusions.nested";
				inclusionsNested = (List<String>) BiotabUtility
						.getPropValueFromProperties(prop, propKey);
				
				propKey = section + ".exclusions.nested";
				exclusionsNested = (List<String>) BiotabUtility
						.getPropValueFromProperties(prop, propKey);

                propKey = section + ".inclusions.disease";
                List<String> inclusionDiseaseList = (List<String>) BiotabUtility
                        .getPropValueFromProperties(prop, propKey);

				prevSection = section;
			}

			walkThroughFiles(files, table, isAppDetTop, fileType);

			isCntl = false;
			isAux = false;
			isDiffSection = true;
		}

		logger.info("Process completes!!!");
		Date end = new Date();
		long endTime = end.getTime();
		logger.info(BiotabUtility.determineProcessedTime(startTime, endTime));
		logger.info(fileCount + " files have been processed.");
	}

	// get all bio, aux and control files from sub directories
	private void loopThruDirs(File[] listFiles) 
	{
		for (File file : listFiles)
		{
			if( file.isDirectory() )
			{
				File[] xmlSubFolderFiles = (new File(file.getAbsolutePath())).listFiles();
				loopThruDirs(xmlSubFolderFiles);
			}
			else if( file.getName().endsWith("xml") && fileTypeList.stream().anyMatch( p -> file.getName().contains(p)) )
			{
				if( hasDiseaseCodes ) 
				{
					if ( diseaseCodes.stream().anyMatch( p -> file.getAbsolutePath().contains(p)) )
						files.add(file);
				}
				else
					files.add(file);
			}
		}
	}

	/**
	 * This method walks through the directories looking for XML files.
	 * 
	 * @param files
	 *            List of files in the current working directory.
	 */
	private Table walkThroughFiles(List<File> files, Table table, boolean isAppDetTop, String fileType) 
	{
		String name;
		if (files != null) 
		{
			for (File file : files) {
				if ( topStackFolders == null || topStackFolders.contains(file.getParentFile().getName()) ) 
				{
					name = file.getName().trim();

					if ( name.endsWith("xml") && name.contains(fileType) ) 
					{
						if (isAppDetTop) {
							topFiles.add(file);
						}

						if (section.equals(sections.get(0))) {
							fileCount++;
						}

						xmlFile = file.getAbsolutePath();

						// This chunk of code is for logging the disease
						// code, XML file name,
						// and biotab filename for debug purposes.
						int numOfSep = BiotabUtility
								.countFileSeparator(xmlFile);
						int index = -1;
						for (int a = 0; a < numOfSep - 1; a++) {
							index = xmlFile.indexOf(
									BiotabUtility.FILE_SEPARATOR,
									index + 1);
						}

						String diseaseDir = xmlFile
								.substring(
										index + 1,
										xmlFile.lastIndexOf(BiotabUtility.FILE_SEPARATOR));
						logger.debug("Processing " + diseaseDir
								+ BiotabUtility.FILE_SEPARATOR + name
								+ " for " + section + ".....");

						// This is where the real processing starts
						table = startProcessing(table);
						
					}
				}
			}
		} 
		else {
			for (File file : topFiles) {
				name = file.getName().trim();
				
				if (section.equals(sections.get(0))) {
					fileCount++;
				}

				xmlFile = file.getAbsolutePath();

				// This chunk of code is for logging the disease code, XML file
				// name,
				// and biotab filename for debug purposes.
				int numOfSep = BiotabUtility.countFileSeparator(xmlFile);
				int index = -1;
				for (int a = 0; a < numOfSep - 1; a++) {
					index = xmlFile.indexOf(BiotabUtility.FILE_SEPARATOR,
							index + 1);
				}

				String diseaseDir = xmlFile.substring(index + 1,
						xmlFile.lastIndexOf(BiotabUtility.FILE_SEPARATOR));
				logger.debug("Processing " + diseaseDir
						+ BiotabUtility.FILE_SEPARATOR + name + " for "
						+ section + ".....");

				// This is where the real processing starts
				if(name.contains(fileType))
					table = startProcessing(table);
			}
		}

		return table;
	}

	/**
	 * This method parses the schema and look for all the element names in
	 * interest for the specific processing section of the files along with
	 * their CDEs if available.
	 * 
	 * @param table
	 */
	private Table getXsdElements(Table table) {
		Column column = new Column();

		Iterator<XSElementDecl> itre = schemaSet.iterateElementDecls();

		while (itre.hasNext()) {
			Cell cell = new Cell();
			XSElementDecl xse = (XSElementDecl) itre.next();
			String elementName = xse.getName();
			boolean processed = true;

			// This is for dealing with multiple schemas being used within a XML
			// file.
			// e.g. cqcf in bio
			String targetTemp = xse.getTargetNamespace();
			if (!targetTemp.equalsIgnoreCase(target)) {
				String schemaLocation = nsToSchemaLocation.get(targetTemp);
                String schemeaFileName = schemaLocation.substring(schemaLocation.lastIndexOf('/') + 1);
				try {
                    InputStream xsdStream = BiotabUtility.streamResourceFromClasspath("schemaorg_apache_xmlbeans",schemeaFileName);
					docSchema = parser.build(xsdStream);
				} catch (ParsingException | BiotabInitializationException | IOException e) {
					logger.fatal(e.getMessage());
				}

				target = targetTemp;
			}

			// Determine whether the elements are related to any elements in the
			// exclusion list except when we are working
			// on auxiliary files.
			if (!isAux) {

                Nodes nodes = docSchema.query("//xs:element[@*='" + elementName + "']/ancestor-or-self::xs:element/@name", context);
				for (int i = 0; i < nodes.size(); i++) {
					String name = nodes.get(i).getValue();
					if (exclusions.contains(name)) {
						processed = false;
						break;
					}
				}
			}

			// Process ONLY if the elements have nothing to do with any of the
			// elements in the exclusion list.
			if (processed) {

				if (elementName.equalsIgnoreCase(section)) {

					XSComplexType xscomp = xse.getType().asComplexType();

					if (!exclusions.contains(elementName) && !elementName.equalsIgnoreCase(section)) {
					//if (!exclusions.contains(elementName)) {
						Collection<? extends XSAttributeUse> attributeUses = xscomp.getAttributeUses();
						Iterator<? extends XSAttributeUse> iter = attributeUses.iterator();

						while (iter.hasNext()) {
							XSAttributeDecl attributeDecl = iter.next().getDecl();

							if (attributeDecl.getName().equals("cde")) {
								if (attributeDecl.getDefaultValue() != null) {
									cell.setCde(attributeDecl.getDefaultValue().value);
								} else {
									cell.setCde("");
								}
							}
						}

						cell.setElementName(elementName);
						cell.setElementValue(elementName);
						if (elementName.indexOf("bcr_") > -1
								&& elementName.endsWith("_barcode")) {
							cell.setDisplayOrder(Integer.toString(counter));
							counter++;
						}

						column.add(cell);
						table.addColumn(column);

					}

					if (xscomp != null) {
						XSContentType xscont = xscomp.getContentType();
						XSParticle particle = xscont.asParticle();
						table = getElementsRecursively(particle, table);
					}
				}
			}
		}

		return table;
	}

	/**
	 * This method acts as a recursive helper method for getXsdElements so it
	 * could get to the lowest level of the branch.
	 * 
	 * @param xsp
	 *            Certain point on the tree.
	 */
	private Table getElementsRecursively(XSParticle xsp, Table table) {
		if (xsp != null) {
			XSTerm term = xsp.getTerm();

			if (term.isElementDecl()) {
				boolean processed = true;
				String elementName = term.asElementDecl().getName();


				// This is for dealing with multiple schemas being used within a
				// XML file.
				// e.g. cqcf in bio
				String targetTemp = term.asElementDecl().getTargetNamespace();
				if (!targetTemp.equalsIgnoreCase(target)) {
					String schemaLocation = nsToSchemaLocation.get(targetTemp);
                    String schemeaFileName = schemaLocation.substring(schemaLocation.lastIndexOf('/') + 1);
					try {
                        InputStream xsdStream = BiotabUtility.streamResourceFromClasspath("schemaorg_apache_xmlbeans",schemeaFileName);
						docSchema = parser.build(xsdStream);
					} catch (ParsingException | BiotabInitializationException | IOException e) {
						logger.fatal(e.getMessage());
					}

					target = targetTemp;
				}

				// Determine whether the elements are related to any elements in
				// the exclusion list except when we are working
				// on auxiliary files.
				if (!isAux) {
					Nodes nodes = docSchema.query("//xs:element[@*='"
							+ elementName
							+ "']/ancestor-or-self::xs:element/@name", context);

					for (int i = 0; i < nodes.size(); i++) {
						String name = nodes.get(i).getValue();
						if (exclusions.contains(name)) {
							processed = false;
							break;
						}
					}
				}

				// Process ONLY if the elements have nothing to do with any of
				// the elements in the exclusion list.
				if (processed) {
					Cell cell = new Cell();
					Column column = new Column();

					XSComplexType xscmp = (term.asElementDecl()).getType()
							.asComplexType();

					if (xscmp != null) {
						XSContentType xscont = xscmp.getContentType();
						XSParticle particle = xscont.asParticle();
						table = getElementsRecursively(particle, table);
					}

					cell.setElementName(elementName);

					if (!exclusions.contains(elementName)) {
						
						if (xscmp != null) {
							Collection<? extends XSAttributeUse> attributeUses = xscmp
									.getAttributeUses();
							Iterator<? extends XSAttributeUse> iter = attributeUses
									.iterator();

							while (iter.hasNext()) {
								XSAttributeDecl attributeDecl = iter.next()
										.getDecl();
								if (attributeDecl.getName().equals("cde")) {
									if (attributeDecl.getDefaultValue() != null) {
										cell.setCde(attributeDecl
												.getDefaultValue().value);
									} else {
										cell.setCde("");
									}
								}
							}

							cell.setElementValue(elementName);
							if (elementName.contains("bcr_")
									&& (elementName.endsWith("_barcode") || elementName
											.endsWith("_uuid"))) {
								cell.setDisplayOrder(Integer.toString(counter));
								counter++;
							}
							
							column.add(cell);
							table.addColumn(column);


						}
					}
				}
			} else if (term.isModelGroup()) {
				XSModelGroup model = term.asModelGroup();
				XSParticle[] parr = model.getChildren();

				for (XSParticle partemp : parr) {
					table = getElementsRecursively(partemp, table);
				}
			}
		}

		return table;
	}

	/**
	 * Starting method for processing the XML tree.
	 * 
	 * @return Table object with all the necessary data.
	 */
	private Table processXml(Table table) {
		Node root = (Node) (doc.getRootElement());
		Nodes nodes = doc.query("//*[local-name()='" + section + "']");

		// Check if the section appears in the XML at all
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i) != null) {
				return listChildren(root, 0, table);
			}
		}

		return table;
	}

	private Table listChildren(Node current, int depth, Table table) {
		
		if (current instanceof Element) {
			Element temp = (Element) current;

			// If the current element is not one level from the root... except
			// for auxiliary files.
			// The parent of the Patient element is the root.
            if (!(temp.getParent() instanceof Document)
					|| section.equals("patient")) {
				if (inclusions.contains(temp.getQualifiedName())
						&& !temp.getLocalName().startsWith("date_of_")
						&& !temp.getLocalName().startsWith("days_to_")) {
					
					Cell cell = makeValidCell(temp);

					// Add cell into table
					Row row = new Row();
					int index = 0;
					for (int k = 0; k < table.numberOfColumns(); k++) {
						if (table.get(k).get(0).getElementName()
								.equals(cell.getElementName())) {
							index = k;
							break;
						}
					}
					row = row.addCellToIndex(cell, index);

					table.addRow(row);

				} else if (section.equals("patient")
						|| ((Element) (temp.getParent())).getLocalName()
								.equals(section)) {
					
					String name = temp.getLocalName();
					String value = temp.getValue();

					if (name.startsWith("day_of") && !value.equals("")) {
						day = value;
					} else if (name.startsWith("month_of") && !value.equals("")) {
						month = value;
					} else if (name.startsWith("year_of")) {
						Cell cell = new Cell();
						String procurement = temp
								.getAttributeValue("procurement_status");
						cell.setCde(temp.getAttributeValue("cde"));
						cell.setPreferredName(temp
								.getAttributeValue("preferred_name"));
						cell.setDisplayOrder(temp
								.getAttributeValue("display_order"));
						cell.setProcurementStatus(procurement);

						String dateName = "date" + name.substring(4);

						// Creating date of value
						if (section.equals("aliquot")
								|| section.equals("portion")) {
							StringDate sd = null;

							try {
								sd = BiotabUtility.createStringDate(value, month, day, name);
							} catch (DateFatalException e) {
								logger.fatal("Unable to create a date string for "
										+ dateName + " for file " + xmlFile);
							}

							value = null;

							if (sd != null && sd.getDate() != null) {
								value = sd.getDate();
							} else {
								logger.warn(dateName
                                        + " for file " + xmlFile
										+ " does not have a valid date value.");
							}
						}

						cell.setElementName(dateName);

						if (value != null && !value.equals("")) {
							cell.setElementValue(value);
						} else {
							if (procurement != null && !procurement.equals("") && !procurement.equals("Completed")) {
								cell.setElementValue("[" + procurement + "]");
							} else {
								// If both the value and procurement_status are
								// blank, not much we
								// can do about that
								cell.setElementValue("[Not Available]");
							}
						}

						table.insertCell(cell);

					} else {
						
						//We don't want to add nested cells that should be excluded
                        //Typically these are empty parent elements
						if(!exclusionsNested.contains(name)){
							Cell cell = makeValidCell(temp);
							table.insertCell(cell);
						}
					}
				}
				
				//Am I at a parent element that contains nested child I want?
				if(section.equals(temp.getLocalName())){
					for(String childName : inclusionsNested){
						boolean childExists = checkChildExists(temp,childName);
						//If the child doesn't exist, insert a place holder for not available
						//This is because nested elements are often not present
						if(!childExists){
							Cell na = new Cell();
							na.setElementName(childName);
                            //Unfortunately, the elements aren't always present in the XML so we have no
                            //way of knowing if the element is "supposed" to be there or not. Therefore, we go with
                            //Not Available to be safe.
							na.setElementValue("[Not Available]");
							table.insertCell(na);
						}
						
						//Note: If the child does exist, we will pick it up on the next round of listChildren recursion
					}
				}
				
				if(inclusionsNested.contains(temp.getLocalName())){
					Cell cell = makeValidCell(temp);
					table.insertCell(cell);
				}
			}
		}

		int numOfChildren = current.getChildCount();

		// Obtain all the children of the current node
		for (int i = 0; i < numOfChildren; i++) {
			table = listChildren(current.getChild(i), ++depth, table);
		}

		return table;
	}

	private boolean checkChildExists(Node node, String childName){
		
		if(node instanceof Element){
			Element current = (Element) node;
			if(current.getLocalName().equals(childName)){
				return true;
			}
		}
		
		for(int i = 0; i < node.getChildCount(); i++){
			//Keep going until name is found or there are no more child nodes
			if(checkChildExists(node.getChild(i),childName)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * This method is for creating the second row which contains the CDE of the
	 * element (if available).
	 * 
	 * Since most of the elements in the biospecimen data do not have a CDE
	 * assigned, the second row can be eliminated by changing true to false
	 * under cderow.appears in config.properties.
	 */
	private Table createSecondRow(Table table) {
		Row row = new Row();
		for (Cell cell : table.getRowByIndex(0)) {
			Cell cellNew = new Cell();
			cellNew.setElementName(cell.getElementName());

			if (cell.getCde() != null && !cell.getCde().equals("")) {
				cellNew.setCde(cell.getCde());
				cellNew.setElementValue(cell.getCde());
			} else {
				if (cell.getProcurementStatus() != null
						&& !cell.getProcurementStatus().equals("")) {
					cellNew.setCde(cell.getProcurementStatus());
					cellNew.setElementValue(cell.getProcurementStatus());
				} else {
					cellNew.setCde("");
					cellNew.setElementValue("");
				}
			}
			row.add(cellNew);
		}
		table.addRow(row);

		return table;
	}

	/**
	 * This method is for adding extra elements to the table. Elements include
	 * parent elements like bcr_sample_barcode or bcr_patient_barcode and
	 * date_of elements which are absent from the schema.
	 */
	private Table addInclusionElements(Table table) {
		//Nested inclusions must be added first
        for(String child : inclusionsNested){
			Cell cell = new Cell();
			String elementName = child;
			
			//There is a possibility that we already grabbed this item when walking the XSD
			//However, if the first file didn't have the element, then we need to add it here
			boolean itemAlreadyAdded = false;
			for(Column column : table.asColumns()){
				if(column.numberOfCells()>0){
					if(column.get(0).getElementValue().equals(child)){
						itemAlreadyAdded = true;
						break;
					}
				}
			}
			
			if(!itemAlreadyAdded){
				cell.setElementName(elementName);
				cell.setElementValue(elementName);
	
				Nodes nodes = doc.query("//" + child + "/@cde", context);
	
				if (nodes.size() > 0) {
					cell.setCde(nodes.get(0).getValue());
				}

				table.insertCell(cell);
			}
			
		}

        for (String qualifiedName : inclusions) {
            Cell cell = new Cell();
            String elementName = qualifiedName;

            if (qualifiedName.indexOf(":") > -1) {
                elementName = qualifiedName.substring(qualifiedName
                        .indexOf(":") + 1);
            }
            cell.setElementName(elementName);
            cell.setElementValue(elementName);

            Nodes nodes = doc.query("//" + qualifiedName + "/@cde", context);

            if (nodes.size() > 0) {
                cell.setCde(nodes.get(0).getValue());
            }

            // Making sure that the barcode and uuid elements are displayed
            // first
            if (elementName.indexOf("bcr_") > -1
                    && (elementName.endsWith("_barcode") || elementName
                    .endsWith("_uuid"))) {
                cell.setDisplayOrder(Integer.toString(counter - 50));
                counter++;
            }

            table.insertCell(cell);
        }

		return table;
	}

    /**
     * Convenience method for getting a cell with all of its parameters set correctly.
     * @param element
     * @return
     */
	private Cell makeValidCell(Element element){
		String value = element.getValue();
		String name = element.getLocalName();
		Cell cell = new Cell();
		String procurement = element
				.getAttributeValue("procurement_status");
		cell.setCde(element.getAttributeValue("cde"));
		cell.setElementName(name);
		cell.setPreferredName(element
				.getAttributeValue("preferred_name"));
		cell.setDisplayOrder(element
                .getAttributeValue("display_order"));
		cell.setProcurementStatus(procurement);

		if (value != null && !value.equals("")) {
			cell.setElementValue(value);
		} else {
			if (procurement != null && !procurement.equals("") && !procurement.equals("Completed")) {
				cell.setElementValue("[" + procurement + "]");
			} else {
				// If both the value and procurement_status are
				// blank, not much we
				// can do about that
				cell.setElementValue("[Not Available]");
			}
		}
		
		return cell;
	}

	/**
	 * This method is for determining the disease code to be used for
	 * categorizing the XML files and generating output directories and files.
	 */
	private void determineDiseaseCode() {
		diseaseCode = doc.query("//admin:disease_code", context).get(0)
				.getValue().toLowerCase();
	}

	/**
	 * This method is for determining the location of the schema so the XML file
	 * will always match up with the correct version of the schema.
	 * 
	 * @return location of the schema.
	 */
	private String determineSchemaLocation() {
		String schemaLocation = ((Attribute) (doc.query(
				"//@xsi:schemaLocation", context).get(0))).getValue();
		schemaLocation = schemaLocation
				.substring(schemaLocation.indexOf(" ") + 1);
		return schemaLocation;
	}

	private String determineTargetNamespace() {
		String targetNamespace = ((Attribute) (docSchema.query(
				"//xs:schema/@targetNamespace", context).get(0))).getValue();
		return targetNamespace;
	}

	/**
	 * This method is responsible for filling in parent values into children
	 * rows.
	 */
	private Table processParentElements(Table table) {
		// Scan for parent elements and fill in values for their children elements
		int numOfColumns = table.numberOfColumns();
		int numOfRows = table.numberOfRows();

		String value;
		int actualNumOfRows = 0;

		for (int a = 1; a < numOfRows; a++) {
			if(table.get(0).numberOfCells() > a) {
				if (table.get(0).get(a).getElementName() == null) {
					for (int b = 1; b < numOfColumns; b++) {
						if(table.get(b).numberOfCells() > a) {
							if (table.get(b).get(a).getElementName() != null) {
								value = table.get(b).get(a).getElementValue();
								actualNumOfRows = table.get(b).numberOfCells();
								
								for (int c = a + 1; c < actualNumOfRows; c++) {
									if (table.get(b).get(c).getElementName() == null) {
										table.get(b).get(c).setElementName(table.get(b).get(0).getElementName());
										table.get(b).get(c).setElementValue(value);
									} else {
										// Obtain new parent value
										value = table.get(b).get(c).getElementValue();
									}
								}
								// Since the numberOfRows method determines the maximum
								// number of rows
								// a table has; the column in interest might not have as
								// many rows. The
								// following is necessary to avoid the application from
								// throwing NullPointerException
								// and to fill up those rows with no apparent cells with
								// actual cells and values.
								if (numOfRows != actualNumOfRows) {
									for (int d = actualNumOfRows; d < numOfRows; d++) {
										Cell cell = new Cell();
										cell.setElementName(table.get(b).get(d - 1).getElementName());
										cell.setElementValue(table.get(b).get(d - 1).getElementValue());
										table.get(b).addCellByIndex(cell, d);
										table.get(b).setCell(cell, d);
									}
								}
							}
						}
					}
				}
			}
		}

        //FIX for BCRI-1252. Old XML files may be missing elements
        //later added to the new XSD. This can cause an incomplete
        //number of cells to show up in the final biotab.
        numOfColumns = table.numberOfColumns();
        for(int col = 0; col < numOfColumns; col++){
            //If we are missing an element in the XML then we won't have
            //the same number of cells as we do for the other elements.
            //The number of cells should equal the number of rows for this
            //weird pre-processed Table.
            if(table.get(col).numberOfCells() < numOfRows
                    //If the element is excluded, we don't care about it
                    && ! exclusions.contains(table.get(col).get(0).getElementName())
                    && ! exclusionsNested.contains(table.get(col).get(0).getElementName())){

                int difference = numOfRows - table.get(col).numberOfCells();
                for (int i = 0; i < difference; i++) {
                    //Add a placeholder cell because this element does not exist for this XML file
                    Cell emptyCell = new Cell(table.get(col).get(0)); //Clone the cell..
                    emptyCell.setProcurementStatus(null); //...and blank it out
                    emptyCell.setElementValue(null);
                    table.get(col).add(emptyCell);
                }
            }
        }

        //FIX for BCRI-1629.
        //This code will give us the expected "pattern" that every cell in
        //the table should have. This pattern is determined by looking at
        //the elements at this specific biotab level. That is to say, we don't
        //look at inclusions and exclusions to determine the pattern.
        boolean[] dataPresent;
        //Find out how many possible cells we have
        dataPresent = new boolean[table.get(0).numberOfCells()];
        for(Column c : table){
            for (int i = 0; i < dataPresent.length; i++) {
                //We only care about the elements that aren't included or excluded
                if (!exclusions.contains(c.get(0).getElementName())  //Ignore exclusions
                        && !exclusionsNested.contains(c.get(0).getElementName())    //Ignore exclusions
                        && !inclusions.contains(c.get(i).getElementName())  //Ignore inclusions
                        && !inclusions.contains("bio:" + c.get(i).getElementName())  //Ignore inclusions
                        && !inclusions.contains("shared:" + c.get(i).getElementName())  //Ignore inclusions
                        && c.get(i).getElementValue() != null) {
                    //We should mark this cell as containing data
                    dataPresent[i] = true;
                }
            }
        }

        //Now let's fix those other columns to make sure that they match
        for(int col = 0; col < table.numberOfColumns(); col++){
            for (int i = 0; i < table.get(col).numberOfCells(); i++) {
                Cell c = table.get(col).get(i);
                //If the value is null but it SHOULD have data according to our pattern
                //then we need to manually set the cell to not available
                if(c.getElementValue() == null && dataPresent[i]
                        //If the element is excluded, we don't care about it
                        && ! exclusions.contains(table.get(col).get(0).getElementName())
                        && ! exclusionsNested.contains(table.get(col).get(0).getElementName())){
                    Cell emptyCell = new Cell(table.get(col).get(0)); //Clone the top level cell..
                    emptyCell.setProcurementStatus("Not Available"); //...and blank it out
                    emptyCell.setElementValue("[Not Available]");
                    table.get(col).setCell(emptyCell, i);
                }
            }
        }

		Table tableTemp = new Table();

        //Delete parent elements
		for (int e = 0; e < numOfRows; e++) {
			if(table.get(0).numberOfCells() > e) {
				if (table.get(0).get(e).getElementName() != null) {
					tableTemp.addRow(table.getRowByIndex(e));
				}
			}
		}
		
		table = tableTemp;


		
		return table;
	}

	/**
	 * This method is responsible for writing the files.
	 * 
	 * @param columns
	 *            table converted into list of columns
	 * @param out
	 *            BufferedWrite object
	 */
	private void writeTableToExternal(PrintWriter out, List<Column> columns, boolean isMerge) {		
		Table table = new Table(columns);
            int numOfRows = table.numberOfRows();
            int count = 0;
            int startRow = (isMerge) ? 2 : 0;

            for (int i = startRow; i < numOfRows; i++) {
                Row row = table.getRowByIndex(i);
                String value;
                int rowSize = row.size();

                for (int j = 0; j < rowSize; j++) {
                    value = "";
                    if (row.getCellAtIndex(j).getElementValue() != null) {
                        value = row.getCellAtIndex(j).getElementValue();
                        if(includeDisplayOrder && i == 0 && row.getCellAtIndex(j).getDisplayOrder() != null
                                && !row.getCellAtIndex(j).getDisplayOrder().equals("")) {

                            value += (":" + row.getCellAtIndex(j).getDisplayOrder());
                        }
                    }

                    //Avoid trailing tabs BCRI-867
                    if(j != rowSize - 1){
                        out.write(value + "\t");
                    } else {
                        out.write(value);
                    }

                    if (count % 200 == 0) {
                        out.flush();
                    }

                    count++;
                }

                out.write("\n");
            }
        }

	/**
	 * 
	 * @param xsd
	 *            Schema input stream.
	 * @return
	 */
	private List<String> getNamespaceList(InputStream xsd) throws IOException{
		List<String> targetNamespaces = new ArrayList<String>();
		XSOMParser xsomParser = new XSOMParser();

		try {
			xsomParser.parse(xsd);
		} catch (SAXException saxe) {
			logger.fatal("Could not parse the schema inputstream: " + saxe);
		}

		try {
			schemaSet = xsomParser.getResult();
		} catch (SAXException saxe) {
			logger.fatal("Could not parse schema: " + saxe);
		}

		Nodes namespaces = docSchema.query("//xs:import/@namespace", context);
		Nodes schemaLocations = docSchema.query("//xs:import/@schemaLocation",
				context);

		for (int i = 0; i < namespaces.size(); i++) {
			nsToSchemaLocation.put(namespaces.get(i).getValue(),
					schemaLocations.get(i).getValue());
		}

		List<XSSchema> schemas = (List<XSSchema>) schemaSet.getSchemas();
		for (XSSchema schema : schemas) {
			targetNamespaces.add(schema.getTargetNamespace());
		}

		return targetNamespaces;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getTopStackFilenamesFromDB() {
		Statement stmt = null;
		Connection conn = BiotabUtility.getDatabaseConnection();
		List<String> filenames = new ArrayList<String>();

		logger.debug("Executing SQL.....");

		try {
			stmt = conn.createStatement();
			String sql = "";
			ResultSet rs = stmt.executeQuery(sql);
			
			//TODO: FIX THIS XML FILE LOCATION
			filenames.add("");
			/*while (rs.next()) {
				String path = rs.getString("path");
				int index = path.indexOf("\\", 2);
				int nextindex = path.indexOf("\\", index + 1);
				path = path.substring(0, index) + path.substring(nextindex);
				filenames.add(path);
			}*/

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException sqle) {
			logger.fatal(sqle.getMessage());
		} catch (Exception e) {
			logger.fatal(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				logger.fatal(sqle.getMessage());
			}
		}

		return filenames;
	}
	
	private List<Column> trimEmptyColumns(List<Column> columns) {
		List<Column> newColumns = new ArrayList<Column>();
		
		for(Column column : columns) {
			for(int i = 2; i < column.numberOfCells(); i++) {
				if(column.get(i).getElementValue() != null && !column.get(i).getElementValue().trim().equals("")) {
					newColumns.add(column);
					break;
				}
			}
		}
		
		return newColumns;
	}

    private List<Column> removeExcludedColumns(List<Column> columns) {
        List<Column> newColumns = new ArrayList<Column>();

        for(Column column : columns) {
            if(!exclusions.contains(column.get(0).getElementName())
                    &&!exclusionsNested.contains(column.get(0).getElementName())){
                newColumns.add(column);
            }
        }

        return newColumns;
    }
}
