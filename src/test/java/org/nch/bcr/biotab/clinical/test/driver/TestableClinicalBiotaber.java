package org.nch.bcr.biotab.clinical.test.driver;

import java.util.Properties;

import org.nch.bcr.biotab.app.BioTabFileDTO;
import org.nch.bcr.biotab.app.BioTabFileSet;
import org.nch.bcr.biotab.app.exceptions.BiotabInitializationException;
import org.nch.bcr.biotab.clinical.app.ClinicalBiotaber;
import org.nch.bcr.biotab.versions.XsdVersionFileException;

/**
 * An extension of the main app class so that we can add accessor methods to low level and self contained objects that we
 * would not want to expose for anything other than testing. For example, <code>ClinicalBiotaber</code> or is baseclass 
 * builds up instances of the Header and Table and we want to keep those instance tightly scoped to <code>ClinicalBiotaber</code> 
 * so that we do not have fragmented code mutating these core data objects. 
 * <p>
 * However, for unit tests we need low level access to these core data objects (i.e. header and table) so that we can probe them. 
 * </p> 
 * 
 * @author John Deardurff
 *
 */
public class TestableClinicalBiotaber extends ClinicalBiotaber {
	
	public TestableClinicalBiotaber(Properties properties, String diseaseCode) throws BiotabInitializationException, XsdVersionFileException {
		super(properties, diseaseCode, false);
	}
	
	/**
	 * Returns the <code>BioTabFileDTO</code> instance corresponding to the given <code>diseaseCode</code> and <code>sectionId</code>. 
	 * <p>
	 * <b>NOTE:</b> this method is intended to be called by unit tests ONLY. There is no reason why anything outside of this class or it's 
	 * 		subclasses should be accessing the BioTabFileDTO objects. That would not be a good thing since it would run the risk of having 
	 * 		fragmented code that mutates these class. Currently everything mutates (creates and/or modifies) these objects (instances of 
	 * 		BioTabFileDTO) is completly self contained by this or it's subclasses. 
	 * </p>
	 * 
	 *  
	 * @param diseaseCode the tcga disease code for the sought table and header. 
	 * @param sectionId the sectionId of of the sought table and header as specified in the sectionToFileMaps.groovy control file. 
	 * @return the table and header combination contained by an instance of <code>BioTabFileDTO</code>. 
	 */
	public BioTabFileDTO getBioTabFileDTO(String diseaseCode, String sectionId) {
		BioTabFileDTO result = null;
		for (BioTabFileDTO bioTabFileDTO: bioTabFileSet) {
			if (bioTabFileDTO.getSectionToFileMapNode().getSectionId().equalsIgnoreCase(sectionId) && bioTabFileDTO.getTable().getDiseaseCode().equalsIgnoreCase(diseaseCode)) {
				result = bioTabFileDTO;
			}
		}
		return result;
	}
	
	/**
	 * Returns the <code>BioTabFileDTO</code> instance corresponding to the given <code>diseaseCode</code> and <code>sectionId</code>. 
	 * <p>
	 * <b>NOTE:</b> this method is intended to be called by unit tests ONLY. There is no reason why anything outside of this class or it's 
	 * 		subclasses should be accessing the BioTabFileDTO objects. That would not be a good thing since it would run the risk of having 
	 * 		fragmented code that mutates these class. Currently everything mutates (creates and/or modifies) these objects (instances of 
	 * 		BioTabFileDTO) is completly self contained by this or it's subclasses. 
	 * </p>
	 * 
	 *  
	 * @param diseaseCode the tcga disease code for the sought table and header. 
	 * @param sectionId the sectionId of of the sought table and header as specified in the sectionToFileMaps.groovy control file. 
	 * @return the table and header combination contained by an instance of <code>BioTabFileDTO</code>. 
	 */
	public BioTabFileSet<BioTabFileDTO> getBioTabFileSet() {
		return bioTabFileSet;
	}

}
