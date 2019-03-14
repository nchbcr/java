package org.nch.bcr.biotab.clinical.app;

import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import org.apache.xmlbeans.SchemaParticle;
import org.apache.xmlbeans.XmlObject;
import org.nch.bcr.biotab.app.AbstractBiotaber;
import org.nch.bcr.biotab.app.BioTabFileDTO;
import org.nch.bcr.biotab.app.exceptions.BiotabHeaderCreationException;
import org.nch.bcr.biotab.app.exceptions.BiotabInitializationException;
import org.nch.bcr.biotab.app.exceptions.BiotabTableCreationException;
import org.nch.bcr.biotab.config.SectionToFileMapNode;
import org.nch.bcr.biotab.domain.table.Header;
import org.nch.bcr.biotab.domain.table.HeaderCell;
import org.nch.bcr.biotab.util.XMLBeansXSDUtils;
import org.nch.bcr.biotab.util.XMLFileLoader;
import org.nch.bcr.biotab.versions.XsdVersionFileException;

public class OmfBiotaber extends AbstractBiotaber {
	
	protected boolean writeBiotabs = true;
	
	public OmfBiotaber(Properties properties, String diseaseCode) throws BiotabInitializationException, XsdVersionFileException {
		super(properties, BaseBiotaberApp.XmlFileType.OMF, diseaseCode);
		init();
	}
	
	public OmfBiotaber(Properties properties, String diseaseCode, boolean writeBiotabs) throws BiotabInitializationException, XsdVersionFileException {
		this(properties, diseaseCode);
		this.writeBiotabs = writeBiotabs;
		init();
	}
	
	protected void init() throws BiotabInitializationException, XsdVersionFileException {

	}
	
	@Override
	public void run() {
		try {
			
			// First create all of the header objects for this study. Each bio tab file needs a header object. 
			long startTime = System.currentTimeMillis();
			log.info("--- OMF Biotab Generation (Creating Headers for study " + this.getJobDTO().getDiseaseCode() + ")...");
			loadHeadersForStudy();
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			log.info(MessageFormat.format("--- OMF Biotab Generation (Header Creation Complete) for study ({0}) (RUNTIME: {1,number,#.###} ) minutes:", 
					this.getJobDTO().getDiseaseCode(), ((float)duration/1000.0)/60.0 ));
			
			
			// Second, walk through all of the XML files for this study, creating a populating a table object for each study.
			startTime = System.currentTimeMillis();
			log.info("--- OMF Biotab Generation (Creating Tables for study + " + this.getJobDTO().getDiseaseCode() + ")...");
			createTables();
			endTime = System.currentTimeMillis();
			duration = endTime - startTime;
			log.info(MessageFormat.format("--- OMF Biotab Generation (Table Creation Complete) for study ({0}) (RUNTIME: {1,number,#.###} ) minutes:", 
					this.getJobDTO().getDiseaseCode(), ((float)duration/1000.0)/60.0 ));
			
			
		} catch (BiotabHeaderCreationException e) {
			log.fatal("OMF Biotab Generation encountered errors creating headers for one of the biotab files.", e);
		}  catch (BiotabTableCreationException e) {
			log.fatal("OMF Biotab Generation encountered errors populating one of the tables for the biotab files.", e);
		}
		
		if (writeBiotabs) {
			saveTableAsBiotabs();
		}
	}
	
	/**
	 * All of the biotab headers are created up front based off a single XML document. Actually, we are not creating the header 
	 * based on the content of XML, rather based on the structure of the XSD. We just need an XML document so "bootstrap" the XMLBeans
	 * framework so that we can navigate to XSD structure that gets embedded into the compiled XSDs that happen was upstream. 
	 * 
	 * @param firstClinicalXMLFile any clinical XMl file for the specified study. 
	 * @return all of the biotab headers for this study. 
	 * 
	 * @throws BiotabHeaderCreationException thrown whenever there is any type of error creating any of the headers. 
	 */	
	protected void loadHeadersForStudy() throws BiotabHeaderCreationException {	
		XmlObject clinincalRoot;
		try {
			clinincalRoot = XMLFileLoader.parseClinicalXML(this.getJobDTO(), sampleXmlFile);
		} catch (Throwable e) {
			throw new BiotabHeaderCreationException("Sample OMF XML file for XML could not be read", e);
		} 
		for (SectionToFileMapNode section : this.jobDTO) {

			SchemaParticle particle = XMLBeansXSDUtils.getSchemaParticleForSection(jobDTO, section, clinincalRoot);
			if (particle != null) {
				List<Header<HeaderCell>> headers = XMLBeansXSDUtils.getSectionHeaders(this.getJobDTO(), section, particle, section.getContainerXPath());
				for (Header<HeaderCell> header : headers) {
					BioTabFileDTO bioTabFileDTO = new BioTabFileDTO(section);
					bioTabFileDTO.setHeader(header);
					this.bioTabFileSet.addBioTabFileDTO(bioTabFileDTO);
					

					// TODO: REMOVE
					//if (section.getSectionId().equals("followup-nte")) {
						//System.err.println("HEADER: ");
						//System.err.println(header.toString());
					//}
				}
			}
		}
	}
}
