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
			element(name: 'bcr_slide_uuid') {}
			element(name: 'bcr_slide_barcode') {}
			element(name: 'image_file_name') {}
			element(name: 'immunophenotypic_analysis_results') {}
			element(name: 'tumor_necrosis_percent') {}
			element(name: 'tumor_nuclei_percent') {}
			element(name: 'histological_type', parentName:'histological_types:prescreen') {}
			element(name: 'histological_percentage', parentName:'histological_types') {}
			element(name: 'histological_type_other', parentName:'prescreen') {}
			element(name: 'tma_coordinate') {}
			element(name: 'eber_ish_result') {}
			element(name: 'rna_integrity') {}
			element( name: 'immunophenotypic_analysis_results')			
			{
				condition { isIn( diseaseList: 'LUAD:DLBC') }
			}
			element( name: 'neoplasm_histologic_grade')
			{
				condition { isIn( diseaseList: 'LUAD:DLBC') }
			}
			element( name: 'tumor_necrosis_percent')
			{
				condition { isIn( diseaseList: 'LUAD:DLBC') }
			}
			element( name: 'tumor_nuclei_percent')
			{
				condition { isIn( diseaseList: 'LUAD:DLBC') }
			}
			element( name: 'tma_coordinate')
			{
				condition { isIn( diseaseList: 'LUAD:DLBC') }
			}
			element( name: 'histological_percentage')
			{
				condition { isIn( diseaseList: 'CESC:DLBC') }
			}
			element( name: 'histological_type_other')
			{
				condition { isIn( diseaseList: 'CESC:DLBC') }
			}
			element(name: 'note_text') {}
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
			element( name: 'tma_coordinate') {}
			element(name: 'note_text') {}
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
			element(name: 'bcr_slide_uuid') {}
			element(name: 'bcr_slide_barcode') {}
			element(name: 'image_file_name') {}
			element(name: 'genetic_abnormality_results') {}
			element(name: 'tma_coordinate') {}
			element(name: 'note_text') {}
        }
    }
}
