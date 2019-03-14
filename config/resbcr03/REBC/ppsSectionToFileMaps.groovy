sectionToFileLookup {
    sectionToFileMap (
            sectionId: 'prescreen',
            parentSectionId: 'patient',
            formType: 'Pathology prescreen',
            elementName: '[local-name()=\'prescreen\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']/*[local-name()=\'prescreen\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']',
            repeating: false,
            biotabFilename: 'clinical_pps_' + diseaseCode + '.txt'
    ) {
		elementExclusionList {
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
            element(name: 'genetic_testing_results') {}
            element(name: 'genetic_abnormalities') {}
		}
	}

    sectionToFileMap (
            sectionId: 'genetic_testing_results',
            parentSectionId: 'prescreen',
            formType: 'Pathology prescreen',
            elementName: '[local-name()=\'genetic_testing_results\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']/*[local-name()=\'prescreen\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']/*[local-name()=\'genetic_testing_results\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']/*[local-name()=\'genetic_testing_result\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']',
            repeating: false,
            biotabFilename: 'clinical_pps_genetic_tests_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }

    sectionToFileMap (
            sectionId: 'genetic_abnormality_testing_results',
            parentSectionId: 'prescreen',
            formType: 'Pathology prescreen',
            elementName: '[local-name()=\'genetic_testing_results\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']/*[local-name()=\'prescreen\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']/*[local-name()=\'genetic_abnormalities\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']/*[local-name()=\'genetic_abnormality_testing_result\' and namespace-uri()=\'http://tcga.nci/bcr/xml/prescreen/2.7\']',
            repeating: false,
            biotabFilename: 'clinical_pps_genetic_abnormality_tests_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }
}
