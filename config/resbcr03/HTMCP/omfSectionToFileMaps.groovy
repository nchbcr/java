sectionToFileLookup {
    sectionToFileMap (
            sectionId: 'omf',
            parentSectionId: '',
            formType: 'Other',
            elementName: '[local-name()=\'omf\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/omf/2.7\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/omf/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/omf/2.7\']/*[local-name()=\'omfs\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/omf/2.7\']/*[local-name()=\'omf\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/omf/2.7/4.0\']',
            repeating: false,
            biotabFilename: 'clinical_omf_v4.0_' + diseaseCode + '.txt'
    ) {
		elementExclusionList {
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
	
		}
	}
}