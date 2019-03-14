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
            element(name: 'b_symptoms') {
                condition {
                    isIn(diseaseList: 'KICH:STAD')
                }
            }
            element(name: 'days_to_preop_psa') {
                condition {
                    notIn(diseaseList: 'PRAD')
                }
            }
            element(name: 'psa_result_preop') {
                condition {
                    notIn(diseaseList: 'PRAD')
                }
            }
            element(name: 'gleason_score_combined') {
                condition {
                    notIn(diseaseList: 'PRAD')
                }
            }
            element(name: 'gleason_score_primary') {
                condition {
                    notIn(diseaseList: 'PRAD')
                }
            }
            element(name: 'gleason_score_secondary') {
                condition {
                    notIn(diseaseList: 'PRAD')
                }
            }
            element(name: 'highest_gleason_score') {
                condition {
                    notIn(diseaseList: 'PRAD')
                }
            }
            element(name: 'histological_percentage') {
                condition {
                    notIn(diseaseList: 'TGCT')
                }
            }
			
			element(name: 'adenocarcinoma_invasion') {
				condition {
					notIn(diseaseList: 'PAAD')
				}
			}
			element(name: 'b_cell_tumor_slide_submitted') {
				condition {
					notIn(diseaseList: 'DLBC')
				}
			}
			element(name: 'bcg_treatment_indicator') {
				condition {
					notIn(diseaseList: 'BLCA')
				}
			}
			element(name: 'biochemical_phenotype') {
				condition {
					notIn(diseaseList: 'PCPG')
				}
			}
			element(name: 'biochemical_testing_performed') {
				condition {
					notIn(diseaseList: 'PCPG')
				}
			}
			element(name: 'bladder_cancer_history') {
				condition {
					notIn(diseaseList: 'BLCA')
				}
			}
			element(name: 'cytogenetic_report_submitted') {
				condition {
					notIn(diseaseList: 'DLBC')
				}
			}
			element(name: 'days_to_bcg_treatment') {
				condition {
					notIn(diseaseList: 'BLCA')
				}
			}
			element(name: 'days_to_pleurodesis') {
				condition {
					notIn(diseaseList: 'MESO')
				}
			}
			element(name: 'days_to_prior_biopsy') {
				condition {
					notIn(diseaseList: 'LGG')
				}
			}
			element(name: 'diagnosis_subtype') {
				condition {
					notIn(diseaseList: 'BLCA')
				}
			}
			element(name: 'differential_report_submitted') {
				condition {
					notIn(diseaseList: 'LAML')
				}
			}
			element(name: 'follicular_percent') {
				condition {
					notIn(diseaseList: 'DLBC')
				}
			}
			element(name: 'germline_testing_performed') {
				condition {
					notIn(diseaseList: 'PCPG')
				}
			}
			element(name: 'histological_type_other') {
				condition {
					notIn(diseaseList: 'BRCA:PAAD:PRAD:THCA')
				}
			}
			element(name: 'ipm_indicator') {
				condition {
					notIn(diseaseList: 'PAAD')
				}
			}
			element(name: 'laterality') {
				condition {
					notIn(diseaseList: 'ACC:BRCA:KICH:KIRC:KIRP:LUAD:LUSC:PCPG:TGCT:THCA:UVM')
				}
			}
			element(name: 'maximum_tumor_dimension') {
				condition {
					notIn(diseaseList: 'ACC:KICH:KIRC:KIRP')
				}
			}
			element(name: 'metastatic_diagnosis') {
				condition {
					notIn(diseaseList: 'PCPG')
				}
			}
			element(name: 'method_of_sample_procurement') {
				condition {
					notIn(diseaseList: 'ACC:BLCA:BRCA:CESC:CHOL:COAD:DLBC:ESCA:GBM:KICH:KIRC:KIRP:LGG:LIHC:LUAD:LUSC:MESO:OV:PAAD:PCPG:PRAD:READ:SARC:SKCM:STAD:TGCT:THCA:THYM:UCEC:UCS:UVM')
				}
			}
			element(name: 'mucinous_cystic_indicator') {
				condition {
					notIn(diseaseList: 'PAAD')
				}
			}
			element(name: 'oncocytic_variant_indicator') {
				condition {
					notIn(diseaseList: 'THCA')
				}
			}
			element(name: 'other_method_of_sample_procurement') {
				condition {
					notIn(diseaseList: 'BLCA:BRCA:CESC:CHOL:COAD:DLBC:KICH:KIRC:KIRP:LIHC:LUAD:LUSC:MESO:OV:PAAD:PCPG:PRAD:READ:SARC:SKCM:STAD:THYM:UCEC:UCS:UVM')
				}
			}
			element(name: 'percent_myeloblasts_for_submitted_specimen') {
				condition {
					notIn(diseaseList: 'LAML')
				}
			}
			element(name: 'pleurodesis_performed') {
				condition {
					notIn(diseaseList: 'MESO')
				}
			}
			element(name: 'prior_biopsy') {
				condition {
					notIn(diseaseList: 'LGG')
				}
			}
			element(name: 'prior_procedure') {
				condition {
					notIn(diseaseList: 'LGG')
				}
			}
			element(name: 'prior_procedure_extent') {
				condition {
					notIn(diseaseList: 'LGG')
				}
			}
			element(name: 'site_of_disease_description') {
				condition {
					notIn(diseaseList: 'BLCA:COAD:ESCA:HNSC:LGG:LUAD:LUSC:PAAD:READ:SARC:STAD:THCA:THYM:UCEC:UCS:UVM')
				}
			}
			/*
			element(name: 'site_of_disease_text') {
				condition {
					notIn(diseaseList: 'DLBC:LUAD:LUSC:PAAD:PCPG:SARC:SKCM:STAD:THCA')
				}
			}
			*/
			element(name: 't_cell_tumor_slide_submitted') {
				condition {
					notIn(diseaseList: 'DLBC')
				}
			}
			element(name: 'testicular_cancer_history') {
				condition {
					notIn(diseaseList: 'TGCT')
				}
			}
			element(name: 'tobacco_smoking_history') {
				condition {
					notIn(diseaseList: 'MESO')
				}
			}
			element(name: 'total_cells_submitted') {
				condition {
					notIn(diseaseList: 'LAML')
				}
			}
			element(name: 'treatment_after_biopsy') {
				condition {
					notIn(diseaseList: 'LGG')
				}
			}
			element(name: 'tumor_focality') {
				condition {
					notIn(diseaseList: 'THCA')
				}
			}
			element(name: 'tumor_morphology_percentage') {
				condition {
					notIn(diseaseList: 'UVM')
				}
			}
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
			
			element(name: 'country') {
                condition {
                    notIn(diseaseList: 'UCEC')
                }
            }
            element(name: 'vessel_used') {
                condition {
                    notIn(diseaseList: 'UCEC')
                }
            }
            element(name: 'other_vessel_used') {
                condition {
                    notIn(diseaseList: 'UCEC')
                }
            }
			element(name: 'tumor_weight') {
                condition {
                    notIn(diseaseList: 'UCEC')
                }
            }
			element(name: 'sample_prescreened') {
                condition {
                    notIn(diseaseList: 'UCEC')
                }
            }
			element(name: 'top_slide_submitted') {
                condition {
                    notIn(diseaseList: 'UCEC')
                }
            }
			element(name: 'digital_image_submitted') {
                condition {
                    notIn(diseaseList: 'UCEC')
                }
            }
			element(name: 'surgery_reason') {
                condition {
                    notIn(diseaseList: 'UCEC')
                }
            }
        }
    }
}