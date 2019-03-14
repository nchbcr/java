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
            element(name: 'days_to_last_known_alive') {}
            element(name: 'additional_studies') {}
            element(name: 'b_symptoms') {}

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
            element(name: 'clinical_N') {
                condition {
                    isIn(diseaseList: 'KICH')
                }
            }
            element(name: 'clinical_T') {
                condition {
                    isIn(diseaseList: 'KICH')
                }
            }
            element(name: 'clinical_stage') {
                condition {
                    isIn(diseaseList: 'KICH')
                }
            }
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
        }
    }
}