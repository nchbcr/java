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
			// exclusions for LUNG
			element(name:'acetaminophen_use_category', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'adult_smoke_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'adult_smoke_exposure_per_day', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'adult_smoke_exposure_years', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'age_at_first_dx_of_lung_cancer', parentName:'family_history_lung_cancer') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'age_at_first_dx_of_lung_cancer_unknown', parentName:'family_history_lung_cancer') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'alk_mutation_detected_result', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'asbestos_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'aspirin_use_category', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'asthma_indicator', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'birth_country', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'blood_relative_with_cancer', parentName:'family_history_lung_cancer') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'cad_heart_attack_indicator', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'chest_radiation_indicator', parentName:'other_malignancy') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'childhood_smoke_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'childhood_smoke_exposure_per_day', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'childhood_smoke_exposure_years', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'copd_indicator', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'country', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'crystalline_silica_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'day_of_sample_procurement', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'days_to_sample_procurement', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'diabetes_indicator', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'diesel_exhaust_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'difficult_to_answer_epidemiology', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'difficult_to_answer_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'difficult_to_answer_family', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'difficult_to_answer_smoking', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'drug_tx_indicator', parentName:'other_malignancy') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'egfr_mutation_exon19_deletion', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'egfr_mutation_exon20_insertion', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'egfr_mutation_g719a_g719c_g719s', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'egfr_mutation_l858r', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'egfr_mutation_l861q', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'egfr_mutation_other', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'egfr_mutation_specify', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'egfr_mutation_t790m', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'family_history_lung_cancer', parentName:'family_history_lung_cancer_list') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'family_history_lung_cancer_indicator', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'family_history_lung_cancer_list', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'highest_education_achieved', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'hiv_aids_indicator', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'hypertension_indicator', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'lung_surgical_procedure_administered', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'marital_status', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'maximum_tumor_diameter', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'meddra_code', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'mediastinal_lymph_node_exam_type', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'metformin_use_category', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'method_of_sample_procurement', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'mineral_dust_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'month_of_sample_procurement', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'neoplasm_histologic_grade', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'occupation_assessment_indicator', parentName:'occupational_history') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'occupation_primary_job', parentName:'occupational_history') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'occupational_history', parentName:'occupational_history_list') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'occupational_history_list', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_dust_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_dust_exposure_type', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_dx_site', parentName:'other_malignancy') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_gas_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_gas_exposure_type', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_malignancy', parentName:'other_malignancy_list') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_malignancy_list', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_radiation_other_malignancy', parentName:'other_malignancy') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_therapy_other_malignancy', parentName:'other_malignancy') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_therapy_other_malignancy_text', parentName:'other_malignancy') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_therapy_other_malignancy_unknown', parentName:'other_malignancy') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_tobacco_product_daily_use', parentName:'other_tobacco_product_usage') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_tobacco_product_duration', parentName:'other_tobacco_product_usage') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_tobacco_product_indicator', parentName:'other_tobacco_product_usage') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_tobacco_product_type', parentName:'other_tobacco_product_usage') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_tobacco_product_usage', parentName:'other_tobacco_product_usage_list') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'other_tobacco_product_usage_list', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'oxygen_use_indicator', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'oxygen_use_type', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'person_occupation_years_number', parentName:'occupational_history') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'physical_exercise_days_per_week', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'pneumonia_indicator', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'radioactive_material_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'relative_death_indicator', parentName:'family_history_lung_cancer') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'relative_smoking_history_indicator', parentName:'family_history_lung_cancer') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'smoking_avg_cigarettes_per_day', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'smoking_cessation_duration', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'smoking_cessation_duration_units', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'smoking_cessation_indicator', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'smoking_duration_years', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'smoking_frequency', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'smoking_history_indicator', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'smoking_time_in_day_begins', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'social_smoke_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'social_smoke_exposure_per_day', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'social_smoke_exposure_years', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'statin_use_category', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'stroke_indicator', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'surgery_indicator', parentName:'other_malignancy') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'weight_prior_to_diagnosis', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'wood_dust_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'workplace_smoke_exposure', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'workplace_smoke_exposure_per_day', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'workplace_smoke_exposure_years', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'year_of_asthma_dx', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'year_of_cad_heart_attack_dx', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'year_of_copd_dx', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'year_of_diabetes_dx', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'year_of_hiv_aids_dx', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'year_of_hypertension_dx', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'year_of_other_malignancy', parentName:'other_malignancy') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'year_of_pneumonia_dx', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'year_of_sample_procurement', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }
			element(name:'year_of_stroke_dx', parentName:'patient') { condition { isIn(diseaseList: 'LUNG') } }



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
			element(name: 'targeted_molecular_therapy') {
				condition {
					isIn(diseaseList: 'DLBC:LUAD:LUSC:LUNG:CESC')
				}
			}
			element(name: 'pathologic_T') {
				condition {
					isIn(diseaseList: 'DLBC')
				}
			}
			element(name: 'pathologic_M') {
				condition {
					isIn(diseaseList: 'DLBC')
				}
			}
			element(name: 'pathologic_N') {
				condition {
					isIn(diseaseList: 'DLBC')
				}
			}
			element(name: 'clinical_T') {
				condition {
					isIn(diseaseList: 'DLBC:KICH')
				}
			}
			element(name: 'clinical_N') {
				condition {
					isIn(diseaseList: 'DLBC:KICH')
				}
			}
			element(name: 'clinical_M') {
				condition {
					isIn(diseaseList: 'DLBC')
				}
			}
			element(name: 'system_version') {
				condition {
					isIn(diseaseList: 'DLBC')
				}
			}
			element(name: 'bone_marrow_biopsy_done') {
				condition {
					isIn(diseaseList: 'DLBC')
				}
			}
			element(name: 'days_to_patient_progression_free') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
            element(name: 'histological_type_other') {
                condition {
                    isIn(diseaseList: 'COAD:ESCA:SKCM:STAD:OV')
                }
            }
			element(name: 'stage_other') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG:DLBC:CESC')
				}
			}
			element(name: 'days_to_additional_surgery_locoregional_procedure') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'days_to_additional_surgery_metastatic_procedure') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'dlco_predictive_percent') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'year_of_tobacco_smoking_onset') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'pulmonary_function_test_performed') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			
			element(name: 'performance_status_scale_timing') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'kras_mutation_found') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'additional_surgery_locoregional_procedure') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'additional_surgery_metastatic_procedure') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'diagnosis') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'kras_gene_analysis_performed') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'egfr_mutation_performed') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'eml4_alk_translocation_performed') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'eml4_alk_translocation_result') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'eml4_alk_translocation_method') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'location_in_lung_parenchyma') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'patient_pregnancy_count') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'kras_mutation_result') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'egfr_mutation_result') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'days_to_tumor_progression') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'pre_bronchodilator_fev1_fvc_percent') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'post_bronchodilator_fev1_fvc_percent') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'pre_bronchodilator_fev1_percent') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'post_bronchodilator_fev1_percent') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'patient_history_immune_system_and_related_disorders_name') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'patient_history_immune_system_and_related_disorders_text') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			
			element(name: 'assessment_timepoint_category') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'new_neoplasm_event_post_initial_therapy_diagnosis_method_text ') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'pathologic_stage') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'female_breast_feeding_or_pregnancy_status_indicator') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'tumor_response_cdus_type') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'radiation_therapy_not_administered_reason') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'radiation_therapy_not_administered_specify') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'brachytherapy_method_type') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'brachytherapy_method_other_specify_text') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'brachytherapy_first_reference_point_administered_total_dose') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'rt_administered_type') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'radiation_type_notes') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'rt_pelvis_administered_total_dose') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'external_beam_radiation_therapy_administered_paraaortic_region_lymph_node_dose') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'chemotherapy_negation_radiation_therapy_concurrent_not_administered_reason') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'chemotherapy_negation_radiation_therapy_concurrent_administered_text') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'chemotherapy_regimen_type') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'other_chemotherapy_agent_administration_specify') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'concurrent_chemotherapy_dose') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'dose_frequency_text') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'agent_total_dose_count') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
		
			element(name: 'radiation_therapy', parentName:'patient') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'anatomic_neoplasm_subdivision', parentName:'patient') {
				condition {
					isIn(diseaseList: 'LUSC:LUAD:LUNG')
				}
			}
			element(name: 'postoperative_rx_tx', parentName:'concurrent_rx_tx_info:patient') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			
			element( name: 'vital_status', parentName: 'followup_history')
			{
				condition { isIn( diseaseList: 'LUAD:LUSC:LUNG' ) }
			}
			
			element( name: 'person_neoplasm_cancer_status', parentName: 'followup_history')
			{
				condition { isIn( diseaseList: 'LUAD:LUSC:LUNG' ) }
			}
			
			element( name: 'days_to_last_followup', parentName: 'followup_history')
			{
				condition { isIn( diseaseList: 'LUAD:LUSC:LUNG' ) }
			}
			
			element(name: 'days_to_performance_status_assessment') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			
			// if this element exists in COAD:ESCA:STAD:OV then, exclude adding it in the biotab
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
			element(name: 'days_to_last_known_alive') {
				condition {
					notIn(diseaseList: 'DLBC:LUAD:LUSC:LUNG:CESC')
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
            element(name: 'clinical_stage') {
                condition {
                    isIn(diseaseList: 'KICH')
                }
            }
            element(name: 'b_symptoms') {
                condition {
                    isIn(diseaseList: 'KICH:STAD:CESC:LUAD:LUSC:LUNG')
                }
            }
			element(name: 'concurrent_chemotherapy_indicator') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
			element(name: 'prescribed_dose_units') {
                condition {
                    isIn(diseaseList: 'CESC')
                }
            }
			element(name: 'comorbidity')
			{
				condition { isIn(diseaseList: 'LUAD:LUSC:LUNG') }
			}
			element(name: 'live_birth_number') {
				condition {
					isIn(diseaseList: 'CESC')
				}
			}
            element(name: 'extranodal_involvement') {
                condition {
                    isIn(diseaseList: 'KICH:LUAD:LUSC:LUNG:CESC')
                }
            }
			element(name: 'cancer_diagnosis_cancer_type_icd9_text_name') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'days_to_clinical_diagnosis') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'days_to_most_recent_date_of_last_contact') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			
			element(name: 'eml4_alk_translocation_method_other') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			
			element(name: 'family_member_relationship_type') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			
			element(name: 'first_surgical_procedure') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'first_surgical_procedure_other') {
				condition {
					isIn(diseaseList: 'LUAD:LUSC:LUNG')
				}
			}
			element(name: 'kras_mutation_result_other')
			{
				condition { isIn(diseaseList: 'LUAD:LUSC:LUNG') }
			}			
			element(name: 'margin_status')
			{
				condition { isIn(diseaseList: 'LUAD:LUSC:LUNG') }
			}
			element(name: 'margin_status_after_second_surgical_procedure')
			{
				condition { isIn(diseaseList: 'LUAD:LUSC:LUNG') }
			}
			
			element(name: 'method_of_clinical_diagnosis')
			{
				condition { isIn(diseaseList: 'LUSC:LUAD:LUNG') }
			}
			
			element(name: 'patient_sex')
			{
				condition { isIn(diseaseList: 'LUSC:LUAD:LUNG') }
			}
			
			element(name: 'protocol_status')
			{
				condition { isIn(diseaseList: 'LUSC:LUAD:LUNG') }
			}
			
			element(name: 'metastatic_site_at_diagnosis')
			{
				condition { isIn(diseaseList: 'LUSC:LUAD:LUNG') }
			}
			
			element(name: 'metastatic_site_at_diagnosis_other')
			{
				condition { isIn(diseaseList: 'LUSC:LUAD:LUNG') }
			}
			
			element(name: 'prior_systemic_therapy_type')
			{
				condition { isIn(diseaseList: 'LUSC:LUAD:LUNG') }
			}
			
			element(name: 'relative_family_cancer_history')
			{
				condition { isIn(diseaseList: 'LUSC:LUAD:LUNG') }
			}
			
			element(name: 'second_surgical_procedure_to_negative_margins')
			{
				condition { isIn(diseaseList: 'LUAD:LUSC:LUNG') }
			}
			
			element(name: 'second_surgical_procedure_to_negative_margins_other')
			{
				condition { isIn(diseaseList: 'LUAD:LUSC:LUNG') }
			}
			
			element(name: 'eml4_alk_translocation_identified')
			{
				condition { isIn(diseaseList: 'LUAD:LUSC:LUNG') }
			}
			
			element(name: 'egfr_mutation_identified')
			{
				condition { isIn(diseaseList: 'LUAD:LUSC:LUNG') }
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
			element(name: 'new_neoplasm_event_type', parentName: 'new_tumor_event') { condition { isIn(diseaseList: 'LUNG') } }
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