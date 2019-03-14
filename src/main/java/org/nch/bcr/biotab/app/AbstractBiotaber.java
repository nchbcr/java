package org.nch.bcr.biotab.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.io.DirectoryWalker;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.nch.bcr.biotab.app.exceptions.BiotabInitializationException;
import org.nch.bcr.biotab.app.exceptions.BiotabTableCreationException;
import org.nch.bcr.biotab.clinical.app.BaseBiotaberApp;
import org.nch.bcr.biotab.domain.table.Cell;
import org.nch.bcr.biotab.domain.table.DiscrepancyResolutionNotCompleteException;
import org.nch.bcr.biotab.domain.table.Row;
import org.nch.bcr.biotab.domain.table.Table;
import org.nch.bcr.biotab.util.XMLBeansUtils;
import org.nch.bcr.biotab.util.XMLFileLoader;
import org.nch.bcr.biotab.util.XQueryUtil;


/**
 * This is the "worker" class, the main unit of work for a thread. It will generate clinical biotab files for a given study.  
 * 
 * @author John Deardurff 
 */
public abstract class AbstractBiotaber extends DirectoryWalker implements Runnable {
	
	protected static Logger log = Logger.getLogger(AbstractBiotaber.class);
	
	protected JobDTO jobDTO = null;
	protected BioTabFileSet<BioTabFileDTO> bioTabFileSet = new BioTabFileSet<BioTabFileDTO>();
	protected File studyInputFolder = null;
	protected File sampleXmlFile = null;
	
	/**
	 * 
	 * @param properties all system properties and all application properties.
	 * @param diseaseCode the disease code for which clinical biotab files are generated.
	 * 
	 * @throws BiotabInitializationException if there are any issues intitializing this application. Some such issues are populating the internal cache of 
	 * 		tblClinicalCrfDataDictionary, parsing sectionToFileMaps.groovy, etc. 
	 */
	public AbstractBiotaber(Properties properties, BaseBiotaberApp.XmlFileType xmlFileType, String diseaseCode) throws BiotabInitializationException {
		super(new XmlFileFilter(xmlFileType), 25);
		try {
			jobDTO = new JobDTO(properties, xmlFileType, diseaseCode);
			studyInputFolder = new File(properties.getProperty("clinical.xml.input.path") + File.separator + diseaseCode);
			sampleXmlFile = findSampleXmlFile();
		} catch (IOException e) {
			throw new BiotabInitializationException(MessageFormat.format("Could not generate any biotab files for disease: [{0}]", diseaseCode), e);
		}
	}

	public JobDTO getJobDTO() {
		return jobDTO;
	}

	public void setJobDTO(JobDTO jobDTO) {
		this.jobDTO = jobDTO;
	}
	
	/**
	 * Call only after the headers are created. This method expects to have a full 
	 * set of <code>BioTabFileDTO</code>. 
	 * 
	 * @throws BiotabTableCreationException
	 */
	protected void createTables() throws BiotabTableCreationException {
		try {
			// Second, walk through all of the XML files for this study, creating a populating a table object for each study. 
			// 		This is a nested loop (i.e. for each XML file,  for each Header,  for each HeaderCell create TableCell. 
			this.walk(studyInputFolder, null);
		} catch (IOException e) {
			throw new BiotabTableCreationException("Could not generate a table object", e);
		}
	}
	
	/**
	 * Saves the data in the <code>Header</code> and <code>Table</code> to biotab files. Call this method <b>ONLY</b> 
	 * after calling <code>createTables()</code>. 
	 */
	protected void saveTableAsBiotabs() {
		BufferedWriter writer = null;
		try {
			for (BioTabFileDTO bioTabFileDTO : bioTabFileSet) {
				if (shouldWriteTable(bioTabFileDTO)) {
					// At this point the table is completely built, sorted, and we know that we should write a file for it. 
					// Now we will check to see if we should purge out all empty columns. Since purging out all empty columns
					// takes is an N^2 (low performance) operation due to the two level deep nested looping, we wait until the 
					// very end to do this. 
					if (!Boolean.valueOf(jobDTO.getProperty("clinical.biotab.include.empty.columns"))) {
						bioTabFileDTO.getTable().purgeEmptyColumns();
					}
					StringBuilder content = bioTabFileDTO.getTable().toBiotabString(jobDTO);
					String strContent = content.toString().replaceAll("\r\n", "\n");
					// The case were the content is totally empty should never happen since there will 
					// always be at least a header. 
					if (content.length()>0) {
						File bioTabFolder = new File(jobDTO.getProperty("clinical.biotab.output.folder") + File.separator +  jobDTO.getDiseaseCode());
						bioTabFolder.mkdirs();
						File bioTabFile = new File(bioTabFolder.getAbsolutePath() +
                                File.separator +
                                "nationwidechildrens.org_" +
                                bioTabFileDTO.getSectionToFileMapNode().getBiotabFilename());
						bioTabFile.createNewFile();
						writer  = new BufferedWriter(new FileWriter(bioTabFile));
						//writer.write(content.toString());
						writer.write(strContent);
						writer.flush();
						writer.close();
					}
				}
			}
		} catch (IOException e) {
			log.fatal(MessageFormat.format(jobDTO.getXmlFileType() + " Biotab Generation failed for one or more files for study: [{0}]", jobDTO.getDiseaseCode()), e);
		} catch (DiscrepancyResolutionNotCompleteException e) {
			log.fatal(MessageFormat.format(jobDTO.getXmlFileType() + " Biotab Generation failed for one or more files for study: [{0}]", jobDTO.getDiseaseCode()), e);
		} finally {
			// Make sure file handles are closed. 
			if (writer != null) {
				try {
					writer.close();
				} catch (Throwable e) {}
			}
		}
		
	}
	
	private boolean shouldWriteTable(BioTabFileDTO bioTabFileDTO) {
		boolean result = true;
		if (!Boolean.valueOf(jobDTO.getProperty("clinical.biotab.write.empty.file"))) {
			if (bioTabFileDTO.getTable().isEmpty()) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * NOTE: We bootstap the XMLBeans object structure by loading an XML file. So, we get a sample XML file 
	 * for the given study. It is important to note that the existence of specific optional XML elements in the 
	 * XML -- or lack thereof -- has no impact on determining the full set of headers. 
	 *  
	 * @return a sample file. which file is irrelevant
	 * 
	 * @throws BiotabInitializationException if there are any issues intitializing this application. Some such issues are populating the internal cache of 
	 * 		tblClinicalCrfDataDictionary, parsing sectionToFileMaps.groovy, etc. 
	 */
	protected File findSampleXmlFile() throws BiotabInitializationException {
		return findSampleXmlFile(studyInputFolder);
	}
	
	private File findSampleXmlFile(File folder) throws BiotabInitializationException {
		File result = null;
		
		try {
			for (File child : folder.listFiles(new XmlFileFilter(jobDTO.getXmlFileType()))) {
				if (child.isFile()) {
					result = child;
					break;
				} else {
					result = findSampleXmlFile(child);
					if (result != null) {
						break;
					}
				}
			}
		} catch (Throwable e) {
			throw new BiotabInitializationException(MessageFormat.format(
					"Could not find a sample XML within folder: [{0}]", (folder == null ? "NULL" : folder.getAbsolutePath())), e);
		}
		return result;
	}
	
	public static class XmlFileFilter implements FileFilter {
		//private boolean clinical = true;
		private BaseBiotaberApp.XmlFileType xmlFileFilter;
		public XmlFileFilter(BaseBiotaberApp.XmlFileType xmlFileFilter) {
			this.xmlFileFilter = xmlFileFilter;
		}

		public boolean accept(File pathname) {
			boolean result = false;
            //In production, we do care if we have a real file
			if (pathname.isFile()) {
                //We have found a file, now check to see if it is a valid file
				result = acceptLogic(pathname);
			} else {
                //This should return true because we want to accept
                //this folder as a possible candidate to find XML files, recursively
				return true;
			}
			return result;
		}

        /*
        For testing only.
         */
        protected boolean acceptTest(File pathname){
            //We don't care if we have a real file for testing
            return acceptLogic(pathname);
        }

        private boolean acceptLogic(File pathname){
            boolean result = false;
            if (pathname.getName().endsWith(".xml") && !pathname.getAbsolutePath().contains("invalid")) {
                switch (xmlFileFilter) {
                    case CLINICAL:
                        if (pathname.getName().contains("clinical")) {
                            result = true;
                        }
                        break;
                    case OMF:
                        if (pathname.getName().contains("omf")) {
                            result = true;
                        }
                        break;
					case SSF:
						if (pathname.getName().contains("ssf")) {
							result = true;
						}
						break;
					case PPS:
						if (pathname.getName().contains("pps")) {
							result = true;
						}
						break;
                }
            }

            return result;
        }
	}
	
	/**
	 * This is an override of <code>DirectoryWalker</code> which parses out an XML file each time it is called. 
	 */
	@Override
	protected void handleFile(File file, int depth, Collection results) {
		try {
			XmlObject clinincalRoot = XMLFileLoader.parseClinicalXML(this.getJobDTO(), file);
			XQueryUtil xQueryUtil = new XQueryUtil(clinincalRoot);
			
			for (BioTabFileDTO bioTabFileDTO: bioTabFileSet) {
				Table<Row<Cell>> table = bioTabFileDTO.getTable();
				if (table == null) {
					table = new Table<Row<Cell>>(this.getJobDTO().getDiseaseCode(), bioTabFileDTO.getHeader());
					bioTabFileDTO.setTable(table);
				}
				
				String barcode = XMLFileLoader.getTcgaNumber(file);

				if (!bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("patient")) {
					String xpath = bioTabFileDTO.getSectionToFileMapNode().getContainerXPath();
					
					//System.err.println("XPATH container: " + xpath);
				
					XmlObject[] children = clinincalRoot.selectPath(xQueryUtil.appendNamespaceDeclarationForXQuery(xpath));
					for (int i=0; i<children.length; i++) {
						
						//System.err.println("++++ ACTUAL XPATH (parent) " + xQueryUtil.getXPath(children[i]));
						
						
						Row<Cell> row = Row.createInstance(getJobDTO(), bioTabFileDTO, xQueryUtil, barcode, clinincalRoot,
								bioTabFileDTO.getHeader(), xQueryUtil.getXPath(children[i]), false);
						
						if (bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("patent-nte") || bioTabFileDTO.getSectionToFileMapNode().getSectionId().equals("followup-nte")) {
							// We want to make sure that all elements other than the barcode and the uuid have a value of [Not Available]
							// The barcode and uuid will or at least should always have a value, but these are not taken into account 
							// when determining if a row is blank. 
							//
							// This is used for the NTE biotabs, because there are a number of NTE rows in OC that have no answers. This 
							// propagates into the XML as XML blocks with no values. 
							if (!row.isRowEmpty(table.getHeader())) {
								table.addRow(row);
							}
						} else {
							table.addRow(row);
						}
					}
					
				} else {
					
					Row<Cell> row = Row.createInstance(getJobDTO(), bioTabFileDTO, xQueryUtil, barcode, clinincalRoot, bioTabFileDTO.getHeader(), null, false);
					table.addRow(row);
				}

				//log.trace("*******************");
				//log.trace(MessageFormat.format("TABLE for study: [{0}] and section: [{1}]", jobDTO.getDiseaseCode(), bioTabFileDTO.getSectionToFileMapNode().getSectionId()));
				//log.trace(table.toString());
				//log.trace("*******************");
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
}
