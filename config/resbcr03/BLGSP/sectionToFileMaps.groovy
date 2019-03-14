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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
            element(name: 'additional_studies') {}
            
            //ER-specific elements, we don't produce ER biotabs
            element(name: 'patient_progression_status') {}
            element(name: 'history_prior_surgery_indicator') {}
            element(name: 'history_prior_surgery_type') {}
            element(name: 'history_prior_surgery_type_other') {}
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
            element(name: 'histological_type') {
                condition {
                    isIn(diseaseList: 'SKCM')
                }
            }
            element(name: 'histological_type_other') {
                condition {
                    isIn(diseaseList: 'COAD:ESCA:SKCM:STAD:LUAD:LUSC:OV')
                }
            }
            element(name: 'anatomic_neoplasm_subdivision_other') {
                condition {
                    isIn(diseaseList: 'COAD:ESCA:STAD:OV')
                }
            }
            //End ER-specific elements

            element(name: 'psa_value') {
                condition {
                    notIn(diseaseList: 'PRAD')
                }
            }
            element(name: 'days_to_psa') {
                condition {
                    notIn(diseaseList: 'PRAD')
                }
            }
            element(name: 'gleason_score') {
                condition {
                    notIn(diseaseList: 'PRAD')
                }
            }
            element(name: 'primary_pattern') {
                condition {
                    notIn(diseaseList: 'PRAD')
                }
            }
            element(name: 'secondary_pattern') {
                condition {
                    notIn(diseaseList: 'PRAD')
                }
            }
            element(name: 'tertiary_pattern') {
                condition {
                    notIn(diseaseList: 'PRAD')
                }
            }
            element(name: 'clinical_N') {}
            element(name: 'clinical_T') {}
            element(name: 'clinical_stage') {}
            element(name: 'b_symptoms') {
                condition {
                    isIn(diseaseList: 'KICH:STAD')
                }
            }
            element(name: 'extranodal_involvement') {
                condition {
                    isIn(diseaseList: 'KICH')
                }
            }
            element(name: 'days_from_date_of_initial_pathologic_diagnosis_to_date_of_birth') {
                // We are exluding this element for KICH because it was incorrectly added to the XSD.
                // It has the same CDE as days_to_birth. So, in version 2.7 of the XSDs, the days_to_birth
                // element in the XSD stays and this element will get removed. For biotabs we just exclude
                // this column as a work-a-round.
                condition {
                    isIn(diseaseList: 'KICH')
                }
            }
            element(name: 'max') {
                condition {
                    isIn(diseaseList: 'ACC')
                }
            }
            element(name: 'nf1') {
                condition {
                    isIn(diseaseList: 'ACC')
                }
            }
            element(name: 'nf1_clinical_diagnosis') {
                condition {
                    isIn(diseaseList: 'ACC')
                }
            }
            //This question appears twice in the XSD, once as diagnosis_age
            //and once as age_at_initial_pathologic_diagnosis. The latter is the correct one.
            element(name: 'diagnosis_age') {
                condition {
                    isIn(diseaseList: 'LAML')
                }
            }
            element(name: 'serum_markers') {
                condition {
                    notIn(diseaseList: 'TGCT')
                }
            }
            element(name: 'igcccg_stage') {
                condition {
                    notIn(diseaseList: 'TGCT')
                }
            }
            element(name: 'masaoka_stage') {
                condition {
                    notIn(diseaseList: 'THYM')
                }
            }
			element(name: 'treatment_outcome_first_course') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'clinical_M') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pathologic_M') {}
			element(name: 'pathologic_N') {}
			element(name: 'pathologic_T') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stage_other') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}
			element(name: 'regimen_indication') {}
			element(name: 'chemo_end_reporting_period') {}
			element(name: 'ajcc_metastasis_clinical_cm') {}
			element(name: 'ajcc_nodes_clinical_cn') {}
			element(name: 'ajcc_tumor_clinical_ct') {}
			element(name: 'ajcc_clinical_tumor_stage') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'ajcc_metastasis_pathologic_pm') {}
			element(name: 'ajcc_nodes_pathologic_pn') {}
			element(name: 'ajcc_tumor_pathologic_pt') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'pharma_adjuvant_cycles_count') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}
			element(name: 'therapy_regimen') {}
			element(name: 'psa_most_recent_results') {}
			element(name: 'days_to_psa') {}
			element(name: 'gleason_score') {}
			element(name: 'gleason_pattern_primary') {}
			element(name: 'gleason_pattern_secondary') {}
			element(name: 'gleason_pattern_tertiary') {}
			element(name: 'serum_markers') {}
			element(name: 'igcccg_stage') {}
			element(name: 'masaoka_stage') {}
			element(name: 'bcr_slide_uuid') {}
			element(name: 'bcr_slide_barcode') {}
			element(name: 'image_file_name') {}
			element(name: 'primary_therapy_outcome_success', parentName:'radiation') {}
			element(name: 'system_version') {}
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'progression_determined_by', parentName:'progression_determined_by_list') {}
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
            element(name: 'history_prior_systemic_therapy') {} //Element added for ER only
            element(name: 'number_cycles') {
                condition {
                    isIn(diseaseList: 'KICH')
                }
            }
            element(name: 'prescribed_dose') {
                condition {
                    isIn(diseaseList: 'KICH:DLBC')
                }
            }
            element(name: 'prescribed_dose_units') {
                condition {
                    isIn(diseaseList: 'KICH:DLBC')
                }
            }
            element(name: 'regimen_indication') {
                condition {
                    isIn(diseaseList: 'KICH')
                }
            }
            element(name: 'regimen_indication_notes') {
                condition {
                    isIn(diseaseList: 'KICH:DLBC')
                }
            }
            element(name: 'regimen_number') {
                condition {
                    isIn(diseaseList: 'KICH:DLBC')
                }
            }
            element(name: 'route_of_administration') {
                condition {
                    isIn(diseaseList: 'KICH:DLBC')
                }
            }
            element(name: 'therapy_type_notes') {
                condition {
                    isIn(diseaseList: 'KICH')
                }
            }
            element(name: 'total_dose') {
                condition {
                    isIn(diseaseList: 'KICH')
                }
            }
            element(name: 'total_dose_units') {
                condition {
                    isIn(diseaseList: 'KICH:DLBC')
				}
            }
			element(name: 'clinical_trail_drug_classification')	{ 
				condition { 
					isIn(diseaseList: 'DLBC') 
				}
			}
			element(name: 'measure_of_response') { 
				condition { 
					isIn(diseaseList: 'DLBC')
				}
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
			element(name: 'day_of_form_completion') {}
			element(name: 'month_of_form_completion') {}
			element(name: 'year_of_form_completion') {}
		}
	}
	
	sectionToFileMap (
		sectionId: 'treatment',
		parentSectionId: 'patient',
		formType: 'Treatment',
		elementName: '[local-name()=\'treatment\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/treatment/2.7\']',
		containerXPath: '//*[local-name()=\'tcga_bcr\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'patient\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/' + diseaseCode + '/2.7\']/*[local-name()=\'treatments\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/treatment/2.7\']/*[local-name()=\'treatment\' and namespace-uri()=\'http://tcga.nci/bcr/xml/clinical/treatment/2.7\']',
		repeating: false,
		biotabFilename: 'clinical_treatment_' + diseaseCode + '.txt'
	){
		elementExclusionList {
			element(name: 'day_of_form_completion') {}
			element(name: 'month_of_form_completion') {}
			element(name: 'year_of_form_completion') {}
			element(name: 'treatment_outcome') {}
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'treatment_outcome_first_course') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}			
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}			
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}			
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}			
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}			
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}			
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}			
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}			
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}
			element(name: 'progression_determined_by', parentName:'progression_determined_by_list') {}
        }
    }
	
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}			
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}			
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}			
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
            element(name: 'day_of_form_completion') {}
            element(name: 'month_of_form_completion') {}
            element(name: 'year_of_form_completion') {}
			element(name: 'chemotherapy_end') {}
			element(name: 'days_to_cancer_debulking_surgery') {}
			element(name: 'days_to_chemotherapy_end') {}
			element(name: 'days_to_chemotherapy_start') {}
			element(name: 'days_to_radiation_therapy_end') {}
			element(name: 'days_to_radiation_therapy_start') {}
			element(name: 'days_to_stem_cell_transplantation') {}
			element(name: 'extranodal_radiation_field') {}
			element(name: 'lymphoma_treatment_type') {}
			element(name: 'lymphoma_treatment_type_other') {}
			element(name: 'pharm_regimen') {}
			element(name: 'pharm_regimen_other') {}
			element(name: 'number_cycles') {}
			element(name: 'primary_therapy_outcome_success') {}
			element(name: 'radiation_therapy_end') {}
			element(name: 'radiation_therapy_total_dosage') {}
			element(name: 'regimen_indication') {}
			element(name: 'specify_single_agent_therapy') {}
			element(name: 'stem_cell_transplantation') {}
			element(name: 'targeted_nodal_region') {}
			element(name: 'targeted_nodal_region_other') {}			
        }
    }
}