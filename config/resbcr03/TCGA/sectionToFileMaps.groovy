sectionToFileLookup {
    sectionToFileMap (
            sectionId: 'patient',
            parentSectionId: '',
            formType: 'Enrollment',
            elementName: '[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']',
            repeating: false,
            biotabFilename: 'clinical_patient_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element( name: 'patient_pregnancy_count' )	
			{ 
				condition { isIn( diseaseList: 'CESC' ) }
			}
			
			/* when elements defined more than once in XSD, then exclude one that we do not want to see in biotabs */
			element( name: 'anatomic_neoplasm_subdivision', parentName: 'anatomic_neoplasm_subdivision_list')	
			{ 
				condition { isIn( diseaseList: 'COAD:LUAD:LUSC' ) }
			}
			
			element( name: 'kras_mutation_result', parentName: 'kras_mutation_result_list')	
			{ 
				condition { isIn( diseaseList: 'LUAD' )	} 
			}
			
			element( name: 'person_neoplasm_cancer_status', parentName: 'followup_history')	
			{ 
				condition { isIn( diseaseList: 'COAD:LUAD' ) } 
			}
			
			element( name: 'postoperative_rx_tx', parentName: 'patient:concurrent_rx_tx_info')
			{
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'targeted_molecular_therapy') {} // no value should exists for this, see 2.7.7 jira epic
			element( name: 'vital_status', parentName: 'followup_history')	
			{ 
				condition { isIn( diseaseList: 'COAD:LUAD' ) } 
			}
			
			element( name: 'days_to_last_followup', parentName: 'followup_history')	
			{ 
				condition { isIn( diseaseList: 'COAD:LUAD' ) } 
			}
			
			element( name: 'tumor_tissue_site', parentName: 'patient')	
			{ 
				condition { isIn( diseaseList: 'SKCM' ) } 
			}
			
			/* END: when elements defined more than once in XSD, then exclude one that we do not want to see in biotabs */
			
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
            element(name: 'days_to_last_known_alive') {}
            element(name: 'additional_studies') {}
            element(name: 'b_symptoms') {}
			element(name: 'bcr_treatment_uuid') {}
			element(name: 'bcr_treatment_barcode') {}

            //ER-specific elements, we don't produce ER biotabs
            element(name: 'patient_progression_status') {}
            element(name: 'history_prior_surgery_indicator') {}
            element(name: 'history_prior_surgery_type') {}
            element(name: 'history_prior_surgery_type_other') 
			{
                condition {	notIn(diseaseList: 'LIHC:CHOL') }
            }
            element(name: 'history_of_radiation_primary_site') {}
            element(name: 'history_of_radiation_metastatic_site') {}
            element(name: 'er_disease_extent_prior_er_treatment') {}
            element(name: 'er_response_type') {}
            element(name: 'er_estimated_duration_response') {}
            element(name: 'er_solid_tumor_response_documented_type') {}
            element(name: 'er_solid_tumor_response_documented_type_other') {}
            element(name: 'days_to_first_response') {}
            element(name: 'days_to_first_complete_response') {}
            element(name: 'days_to_first_partial_response') {}
            element(name: 'field') {}
            element(name: 'molecular_abnormality_results') {}
            element(name: 'molecular_abnormality_results_other') {}
            
			element(name: 'histological_type') 
			{
                condition { isIn(diseaseList: 'SKCM') }
            }
			
            element(name: 'histological_type_other') 
			{
                condition { isIn(diseaseList: 'CESC:COAD:ESCA:SKCM:STAD:LUAD:LUSC:OV') }
            }
			
            element(name: 'anatomic_neoplasm_subdivision_other') 
			{
                condition { isIn(diseaseList: 'COAD:ESCA:OV') }
            }
			
            //End ER-specific elements
			
            element(name: 'psa_value') 
			{
                condition { notIn(diseaseList: 'PRAD') }
            }
			
            element(name: 'days_to_psa') 
			{
                condition { notIn(diseaseList: 'PRAD') }
            }
			
            element(name: 'gleason_score') 
			{
                condition { notIn(diseaseList: 'PRAD') }
            }
			
            element(name: 'primary_pattern') 
			{
                condition { notIn(diseaseList: 'PRAD') }
            }
			
            element(name: 'secondary_pattern') 
			{
                condition { notIn(diseaseList: 'PRAD') }
            }
            element(name: 'tertiary_pattern') 
			{
                condition { notIn(diseaseList: 'PRAD') }
            }
			
            element(name: 'clinical_N') 
			{
                condition { isIn(diseaseList: 'KICH') }
            }
			
            element(name: 'clinical_T') 
			{
                condition { isIn(diseaseList: 'KICH') }
            }
			
            element(name: 'clinical_stage') 
			{
                condition { isIn(diseaseList: 'KICH') }
            }
			
            element(name: 'b_symptoms') 
			{
                condition { isIn(diseaseList: 'KICH:STAD') }
            }
			
            element(name: 'extranodal_involvement') 
			{
                condition { isIn(diseaseList: 'KICH') }
            }
			
            element(name: 'days_from_date_of_initial_pathologic_diagnosis_to_date_of_birth') 
			{
                // We are exluding this element for KICH because it was incorrectly added to the XSD.
                // It has the same CDE as days_to_birth. So, in version 2.7 of the XSDs, the days_to_birth
                // element in the XSD stays and this element will get removed. For biotabs we just exclude
                // this column as a work-a-round.
                condition { isIn(diseaseList: 'KICH') }
            }
			
            element(name: 'max') 
			{
                condition { isIn(diseaseList: 'ACC') }
            }
			
            element(name: 'nf1') 
			{
                condition { isIn(diseaseList: 'ACC') }
            }
			
            element(name: 'nf1_clinical_diagnosis') 
			{
                condition { isIn(diseaseList: 'ACC') }
            }
			
            //This question appears twice in the XSD, once as diagnosis_age
            //and once as age_at_initial_pathologic_diagnosis. The latter is the correct one.
            element(name: 'diagnosis_age') 
			{
                condition { isIn(diseaseList: 'LAML') }
            }
			
            element(name: 'serum_markers') 
			{
                condition { notIn(diseaseList: 'TGCT') }
            }
			
            element(name: 'igcccg_stage') 
			{
                condition { notIn(diseaseList: 'TGCT') }
            }
			
            element(name: 'masaoka_stage') 
			{
                condition { notIn(diseaseList: 'THYM') }
            }
			
			// 2.7.7 changes, excluding newly created elements in TCGA 
			element(name: 'agent') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
			
			element(name: 'chemotherapy_end') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
			
			element(name: 'comorbidity') 
			{
                condition { isIn(diseaseList: 'COAD:LUAD') }
            }
			
			element(name: 'days_to_clinical_diagnosis') 
			{
                condition { isIn(diseaseList: 'COAD:LUAD') }
            }
			
			element(name: 'days_to_most_recent_date_of_last_contact') 
			{
                condition { isIn(diseaseList: 'COAD:LUAD') }
            }
			
			element(name: 'days_to_treatment_end') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
			
			element(name: 'days_to_treatment_start') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
			
			element(name: 'eml4_alk_translocation_method_other') 
			{
                condition { isIn(diseaseList: 'LUAD') }
            }
			
			element(name: 'extranodal_radiation_field') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
			
			element(name: 'first_surgical_procedure') 
			{
                condition { isIn(diseaseList: 'LUAD') }
            }
			
			element(name: 'first_surgical_procedure_other') 
			{
                condition { isIn(diseaseList: 'LUAD') }
            }
			
			element(name: 'history_prior_surgery_type_other') 
			{
                condition { isIn(diseaseList: 'CHOL:LIHC') }
            }
			
			element(name: 'kras_mutation_result_other') 
			{
                condition { isIn(diseaseList: 'LUAD') }
            }
			
			element(name: 'margin_status_after_second_surgical_procedure') 
			{
                condition { isIn(diseaseList: 'LUAD') }
            }
			
			element(name: 'method_of_clinical_diagnosis') 
			{
                condition { isIn(diseaseList: 'COAD:LUAD') }
            }
			
			element(name: 'patient_sex') 
			{
                condition { isIn(diseaseList: 'COAD:LUAD') }
            }
			
			element(name: 'protocol_status') 
			{
                condition { isIn(diseaseList: 'COAD:LUAD') }
            }
			
			element(name: 'radiation_therapy_end') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
			
			element(name: 'radiation_therapy_total_dosage') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
			
			element(name: 'second_surgical_procedure_to_negative_margins') 
			{
                condition { isIn(diseaseList: 'LUAD') }
            }
			
			element(name: 'second_surgical_procedure_to_negative_margins_other') 
			{
                condition { isIn(diseaseList: 'LUAD') }
            }
			
			element(name: 'specify_single_agent_therapy') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
			
			element(name: 'targeted_nodal_region') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
			
			element(name: 'targeted_nodal_region_other') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
			
			element(name: 'treatment_outcome') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
			
			element(name: 'treatment_type') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
			
			element(name: 'treatment_type_other') 
			{
                condition { isIn(diseaseList: 'COAD:DLBC:LUAD') }
            }
        }
    }
    sectionToFileMap (
            sectionId: 'patent-nte',
            parentSectionId: 'patient',
            formType: 'Enrollment',
            elementName: '[local-name()=\'new_tumor_event\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'new_tumor_events\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']/*[local-name()=\'new_tumor_event\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']',
            repeating: false,
            biotabFilename: 'clinical_nte_' + diseaseCode + '.txt'
    ){
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			
			element(name: 'new_neoplasm_event_type', parentName: 'new_tumor_event') 
			{
                condition { isIn(diseaseList: 'LUAD') }
            }
			
			element(name: 'new_event_treatment_type') 
			{
                condition { isIn(diseaseList: 'COAD:LUAD') }
            }
			
			element(name: 'treatment_outcome')
			{
                condition { isIn(diseaseList: 'COAD:LUAD') }
            }
			
			element(name: 'primary_anatomic_site_count')
			{
                condition { isIn(diseaseList: 'SKCM') }
            }
			
			element(name: 'radiologic_tumor_length')
			{
                condition { isIn(diseaseList: 'SARC') }
            }
			element(name: 'radiologic_tumor_width')
			{
                condition { isIn(diseaseList: 'SARC') }
            }
			element(name: 'radiologic_tumor_depth')
			{
                condition { isIn(diseaseList: 'SARC') }
            }
			
			element(name: 'pathologic_tumor_length')
			{
                condition { isIn(diseaseList: 'SARC') }
            }
			element(name: 'pathologic_tumor_width')
			{
                condition { isIn(diseaseList: 'SARC') }
            }
			element(name: 'pathologic_tumor_depth')
			{
                condition { isIn(diseaseList: 'SARC') }
            }
        }
    }
    sectionToFileMap (
            sectionId: 'drug',
            parentSectionId: 'patient',
            formType: 'Pharmaceutical Therapy',
            elementName: '[local-name()=\'drug\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/pharmaceutical/2.7\']','[local-name()=\'bcr_patient_uuid\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'drugs\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/pharmaceutical/2.7\']/*[local-name()=\'drug\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/pharmaceutical/2.7\']','//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']',
            repeating: false,
            biotabFilename: 'clinical_drug_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
            element(name: 'history_prior_systemic_therapy') {} //Element added for ER only
            
			element(name: 'number_cycles') 
			{
                condition { isIn(diseaseList: 'KICH') }
            }
			
            element(name: 'prescribed_dose') 
			{
                condition { isIn(diseaseList: 'KICH:DLBC') }
            }
			
            element(name: 'prescribed_dose_units') {
                condition { isIn(diseaseList: 'KICH:DLBC') }
            }
			
            element(name: 'regimen_indication') {
                condition { isIn(diseaseList: 'KICH') }
            }
			
            element(name: 'regimen_indication_notes') {
                condition { isIn(diseaseList: 'KICH:DLBC') }
            }
			
            element(name: 'regimen_number') {
                condition { isIn(diseaseList: 'KICH:DLBC') }
            }
			
            element(name: 'route_of_administration') {
                condition { isIn(diseaseList: 'KICH:DLBC') }
            }
			
            element(name: 'therapy_type_notes') {
                condition { isIn(diseaseList: 'KICH') }
            }
			
            element(name: 'total_dose') {
                condition { isIn(diseaseList: 'KICH') }
            }
			
            element(name: 'total_dose_units') {
                condition { isIn(diseaseList: 'KICH:DLBC') }
            }
			
			element(name: 'clinical_trail_drug_classification')	{ 
				condition {  isIn(diseaseList: 'DLBC') }
			}
			
			element(name: 'measure_of_response') { 
				condition { isIn(diseaseList: 'DLBC') }
			}
		}
    }
    sectionToFileMap (
            sectionId: 'radiation',
            parentSectionId: 'patient',
            formType: 'Radiation Therapy',
            elementName: '[local-name()=\'radiation\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/radiation/2.7\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'radiations\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/radiation/2.7\']/*[local-name()=\'radiation\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/radiation/2.7\']',
            repeating: false,
            biotabFilename: 'clinical_radiation_' + diseaseCode + '.txt'
    ){
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}

            //ER-specific elements
            element(name: 'prior_radiation_type_metastasis') {}
            element(name: 'prior_radiation_type_notes_metastasis') {}
            element(name: 'radiation_dosage_metastasis') {}
            //End ER-specific elements
        }
    }

	sectionToFileMap (
		sectionId: 'ablation',
		parentSectionId: 'patient',
		formType: 'Ablation',
		elementName: '[local-name()=\'ablation\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/ablation/2.7\']',
		containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'ablations\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/ablation/2.7\']/*[local-name()=\'ablation\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/ablation/2.7\']',
		repeating: false,
		biotabFilename: 'clinical_ablation_' + diseaseCode + '.txt'
	){
		elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
			element(name: 'day_of_form_completion') {}
			element(name: 'month_of_form_completion') {}
			element(name: 'year_of_form_completion') {}
		}
	}
	
    sectionToFileMap (
            sectionId: 'followup',
            parentSectionId: 'patient',
            formType: 'Follow Up',
            elementName: '[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/1.0\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/1.0\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v1.0_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }

    sectionToFileMap (
            sectionId: 'followup',
            parentSectionId: 'patient',
            formType: 'Follow Up',
            elementName: '[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/1.5\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/1.5\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v1.5_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }

    sectionToFileMap (
            sectionId: 'followup',
            parentSectionId: 'patient',
            formType: 'Follow Up',
            elementName: '[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/1.7\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/1.7\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v1.7_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }

    sectionToFileMap (
            sectionId: 'followup',
            parentSectionId: 'patient',
            formType: 'Follow Up',
            elementName: '[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/2.0\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/2.0\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v2.0_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			// when elements defined more than once in XSD, then exclude one that we do not want to see in biotabs
			element( name: 'brachytherapy_first_reference_point_administered_total_dose', parentName: 'radiation_therapy_info')	{ 
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'chemotherapy_negation_radiation_therapy_concurrent_administered_text', parentName: 'concurrent_rx_tx_info:follow_up')	{ 
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'chemotherapy_negation_radiation_therapy_concurrent_not_administered_reason', parentName: 'concurrent_rx_tx_info:follow_up')	{ 
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'chemotherapy_regimen_type', parentName: 'concurrent_rx_tx')	{ 
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'concurrent_chemotherapy_dose', parentName: 'concurrent_rx_tx')	{ 
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'dose_frequency_text', parentName: 'concurrent_rx_tx')	{ 
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'external_beam_radiation_therapy_administered_paraaortic_region_lymph_node_dose', parentName: 'follow_up:radiation_therapy_info') { 
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'other_chemotherapy_agent_administration_specify', parentName: 'concurrent_rx_tx') { 
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'postoperative_rx_tx', parentName: 'concurrent_rx_tx_info:radiation_supplemental:treatment')	{ 
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'prescribed_dose_units', parentName: 'concurrent_rx_tx:follow_up') { 
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'radiation_therapy', parentName: 'treatment') {
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'radiation_therapy_not_administered_reason', parentName: 'radiation_therapy_info') {
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'radiation_therapy_not_administered_specify', parentName: 'radiation_therapy_info') { 
				condition { isIn( diseaseList: 'CESC' )	} 
			}
			
			element( name: 'radiation_type_notes', parentName: 'radiation_therapy_info')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'rt_administered_type', parentName: 'radiation_therapy_info')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'rt_pelvis_administered_total_dose', parentName: 'follow_up:radiation_therapy_info')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'targeted_molecular_therapy')	{ } // no value should exists for this, see 2.7.7 jira epic

			element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }

    sectionToFileMap (
            sectionId: 'followup',
            parentSectionId: 'patient',
            formType: 'Follow Up',
            elementName: '[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/2.1\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/2.1\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v2.1_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }

    sectionToFileMap (
            sectionId: 'followup',
            parentSectionId: 'patient',
            formType: 'Follow Up',
            elementName: '[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/4.0\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/4.0\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v4.0_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			// when elements defined more than once in XSD, then exclude one that we do not want to see in biotabs
			element( name: 'brachytherapy_first_reference_point_administered_total_dose', parentName: 'follow_up')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'chemotherapy_negation_radiation_therapy_concurrent_administered_text', parentName: 'follow_up:radiation_supplemental')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'chemotherapy_negation_radiation_therapy_concurrent_not_administered_reason', parentName: 'follow_up:radiation_supplemental')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'chemotherapy_regimen_type', parentName: 'chemotherapy_regimen_types')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'concurrent_chemotherapy_dose', parentName: 'follow_up')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'dose_frequency_text', parentName: 'follow_up')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'external_beam_radiation_therapy_administered_paraaortic_region_lymph_node_dose', parentName: 'follow_up:radiation_supplemental')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'other_chemotherapy_agent_administration_specify', parentName: 'follow_up')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'postoperative_rx_tx', parentName: 'concurrent_rx_tx_info:radiation_supplemental:treatment')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'prescribed_dose_units', parentName: 'concurrent_rx_tx:follow_up')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'radiation_therapy', parentName: 'treatment')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'radiation_therapy_not_administered_reason', parentName: 'follow_up')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'radiation_therapy_not_administered_specify', parentName: 'follow_up')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'radiation_type_notes', parentName: 'follow_up')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'rt_administered_type', parentName: 'follow_up')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'rt_pelvis_administered_total_dose', parentName: 'follow_up:radiation_supplemental')	{ condition { isIn( diseaseList: 'CESC' )	} }
			element( name: 'targeted_molecular_therapy')	{ } // no value should exists for this, see 2.7.7 jira epic
			
			element( name: 'agent_total_dose_count', parentName: 'follow_up')	
			{ 
				condition { isIn( diseaseList: 'CESC' )	}
			}

			element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }

    sectionToFileMap (
            sectionId: 'followup',
            parentSectionId: 'patient',
            formType: 'Follow Up',
            elementName: '[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/4.4\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/4.4\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v4.4_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }

    sectionToFileMap (
            sectionId: 'followup',
            parentSectionId: 'patient',
            formType: 'Follow Up',
            elementName: '[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/4.8\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/4.8\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v4.8_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }

	sectionToFileMap (
            sectionId: 'followup-nte',
            parentSectionId: 'followup',
            formType: 'Follow Up',
            elementName: 'local-name()=\'new_tumor_event\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/1.0\']/*[local-name()=\'new_tumor_events\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']/*[local-name()=\'new_tumor_event\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v1.0_nte_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'new_event_treatment_type') 
			{
				condition { isIn( diseaseList: 'COAD' )	}
			}
			element(name: 'treatment_outcome') 
			{
				condition { isIn( diseaseList: 'COAD' )	}
			}
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }
	
	sectionToFileMap (
            sectionId: 'followup-nte',
            parentSectionId: 'followup',
            formType: 'Follow Up',
			elementName: 'local-name()=\'new_tumor_event\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/2.0\']/*[local-name()=\'new_tumor_events\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']/*[local-name()=\'new_tumor_event\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v2.0_nte_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }
	
	// special made for THCA follow-up section
    sectionToFileMap (
            sectionId: 'followup-nte',
            parentSectionId: 'followup',
            formType: 'Follow Up',
			elementName: '[local-name()=\'new_tumor\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/2.0\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/2.0\']/*[local-name()=\'new_tumor\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/2.0\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v2.0_nte_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }

    sectionToFileMap (
            sectionId: 'followup-nte',
            parentSectionId: 'followup',
            formType: 'Follow Up',
            elementName: 'local-name()=\'new_tumor_event\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/4.0\']/*[local-name()=\'new_tumor_events\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']/*[local-name()=\'new_tumor_event\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v4.0_nte_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'radiologic_tumor_length')
			{
                condition { isIn(diseaseList: 'SARC') }
            }
			element(name: 'radiologic_tumor_width')
			{
                condition { isIn(diseaseList: 'SARC') }
            }
			element(name: 'radiologic_tumor_depth')
			{
                condition { isIn(diseaseList: 'SARC') }
            }
			
			element(name: 'pathologic_tumor_length')
			{
                condition { isIn(diseaseList: 'SARC') }
            }
			element(name: 'pathologic_tumor_width')
			{
                condition { isIn(diseaseList: 'SARC') }
            }
			element(name: 'pathologic_tumor_depth')
			{
                condition { isIn(diseaseList: 'SARC') }
            }
			
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }

    sectionToFileMap (
            sectionId: 'followup-nte',
            parentSectionId: 'followup',
            formType: 'Follow Up',
            elementName: 'local-name()=\'new_tumor_event\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/4.4\']/*[local-name()=\'new_tumor_events\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']/*[local-name()=\'new_tumor_event\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v4.4_nte_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }

    sectionToFileMap (
            sectionId: 'followup-nte',
            parentSectionId: 'followup',
            formType: 'Follow Up',
            elementName: 'local-name()=\'new_tumor_event\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']',
            containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_ups\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'follow_up\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/followup/2.7/4.8\']/*[local-name()=\'new_tumor_events\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']/*[local-name()=\'new_tumor_event\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/shared/new_tumor_event/2.7/1.0\']',
            repeating: false,
            biotabFilename: 'clinical_follow_up_v4.8_nte_' + diseaseCode + '.txt'
    ) {
        elementExclusionList {
			element(name: 'targeted_molecular_therapy') {}
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
        }
    }
}