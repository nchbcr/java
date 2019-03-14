sectionToFileLookup {
    sectionToFileMap (
            sectionId: 'ssf_tumor',
            parentSectionId: 'patient',
            formType: 'Sample Submission',
            elementName: '[local-name()=\'tumor_samples\' and namespace-uri()=\'http://tcga.nci/bcr/xml/ssf/2.7\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/ssf/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/ssf/2.7\']/*[local-name()=\'tumor_samples\' and namespace-uri()=\'http://tcga.nci/bcr/xml/ssf/2.7\']/*[local-name()=\'tumor_sample\' and namespace-uri()=\'http://tcga.nci/bcr/xml/ssf/2.7\']',
            repeating: false,
            biotabFilename: 'ssf_tumor_samples_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			
			element(name: 'country') {}
			element(name: 'days_to_pathology_review') {}
			element(name: 'digital_image_submitted') {}
			element(name: 'ffpe_tumor_slide_submitted') {}
			element(name: 'other_vessel_used') {}
			element(name: 'path_confirm_diagnosis_matching') {}
			element(name: 'path_confirm_report_attached') {}
			element(name: 'path_confirm_tumor_necrosis_metrics') {}
			element(name: 'path_confirm_tumor_nuclei_metrics') {}
			element(name: 'reason_path_confirm_diagnosis_not_matching') {}
			element(name: 'sample_prescreened') {}
			element(name: 'submitted_for_lce') {}
			element(name: 'top_slide_submitted') {}
			element(name: 'tumor_necrosis_percent') {}
			element(name: 'tumor_nuclei_percent') {}
			element(name: 'tumor_weight') {}
			element(name: 'vessel_used') {}
			element(name: 'hiv_status') {}
			element(name: 'maximum_tumor_dimension') {}
			element(name: 'follicular_percent') {}
			element(name: 'histological_type_other') {}
			element(name: 'histological_percentage') {}
			element(name: 'tumor_morphology_percentage') {}
			element(name: 'tumor_focality') {}
			element(name: 'laterality') {}
			element(name: 'site_of_disease_description') {}
	
            element(name: 'days_to_preop_psa') {}
            element(name: 'psa_result_preop') {}
            element(name: 'gleason_score_combined') {}
            element(name: 'gleason_score_primary') {}
            element(name: 'gleason_score_secondary') {}
            element(name: 'highest_gleason_score') {}
			
			element(name: 'adenocarcinoma_invasion') {}
			element(name: 'b_cell_tumor_slide_submitted') {}
			element(name: 'bcg_treatment_indicator') {}
			element(name: 'biochemical_phenotype') {}
			element(name: 'biochemical_testing_performed') {}
			element(name: 'bladder_cancer_history') {}
			element(name: 'cytogenetic_report_submitted') {}
			element(name: 'days_to_bcg_treatment') {}
			element(name: 'days_to_pleurodesis') {}
			element(name: 'days_to_prior_biopsy') {}
			element(name: 'diagnosis_subtype') {}
			element(name: 'differential_report_submitted') {}
			element(name: 'germline_testing_performed') {}
			element(name: 'ipm_indicator') {}
			element(name: 'metastatic_diagnosis') {}
			element(name: 'mucinous_cystic_indicator') {}
			element(name: 'oncocytic_variant_indicator') {}
			element(name: 'percent_myeloblasts_for_submitted_specimen') {}
			element(name: 'pleurodesis_performed') {}
			element(name: 'prior_biopsy') {}
			element(name: 'prior_procedure') {}
			element(name: 'prior_procedure_extent') {}
			
			element(name: 't_cell_tumor_slide_submitted') {}
			element(name: 'testicular_cancer_history') {}
			element(name: 'tobacco_smoking_history') {}
			element(name: 'total_cells_submitted') {}
			element(name: 'treatment_after_biopsy') {}
        }
    }

    sectionToFileMap (
            sectionId: 'ssf_normal',
            parentSectionId: 'patient',
            formType: 'Sample Submission',
            elementName: '[local-name()=\'normal_controls\' and namespace-uri()=\'http://tcga.nci/bcr/xml/ssf/2.7\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/ssf/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/ssf/2.7\']/*[local-name()=\'normal_controls\' and namespace-uri()=\'http://tcga.nci/bcr/xml/ssf/2.7\']/*[local-name()=\'normal_control\' and namespace-uri()=\'http://tcga.nci/bcr/xml/ssf/2.7\']',
            repeating: false,
            biotabFilename: 'ssf_normal_controls_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			
			element(name: 'country') {}
            element(name: 'vessel_used') {}
            element(name: 'other_vessel_used') {}
			element(name: 'tumor_weight') {}
			element(name: 'sample_prescreened') {}
			element(name: 'top_slide_submitted') {}
			element(name: 'digital_image_submitted') {}
			element(name: 'surgery_reason') {}
        }
    }
}