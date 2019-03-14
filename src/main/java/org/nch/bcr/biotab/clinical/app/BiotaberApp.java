package org.nch.bcr.biotab.clinical.app;

public class BiotaberApp {
	
	public static void main(String[] args) {
		ClinicalBiotaberApp clinApp = new ClinicalBiotaberApp();
		clinApp.start(args);
		
		OmfBiotaberApp omfApp = new OmfBiotaberApp();
		omfApp.start(args);
	}
}
