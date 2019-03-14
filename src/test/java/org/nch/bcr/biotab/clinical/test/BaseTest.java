package org.nch.bcr.biotab.clinical.test;

import org.apache.log4j.Logger;
import org.nch.bcr.biotab.clinical.app.ClinicalBiotaberApp;
import org.nch.bcr.biotab.versions.Build;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

public class BaseTest {
	
	private static Logger log = Logger.getLogger(BaseTest.class);
	
	protected static String[] args =  new String[] {
		"-studies", "BLCA:BRCA",
		"-propPath", "C:\\TDE\\DEV\\code\\bio-tab-xmlbeans-skeleton\\src\\main\\config\\properties"
	};
	
	protected Object[][] parameters = null;
	
	@BeforeTest
	public void setup() throws Exception {
		ClinicalBiotaberApp.initCache(args);
	}
	
	@BeforeMethod
	public void clean() throws Exception {
		ClinicalBiotaberApp.loadProperties(args);
	}
	
	@DataProvider(name="dataProviderMethod")
	public Object[][] parameterMethod(){
		if (parameters == null) {
			try {
				ClinicalBiotaberApp.loadProperties(args);
				parameters = new Object[ClinicalBiotaberApp.xsdVersions.getDevelopment().getBuilds().size()+1][5];
				parameters[0][0] = "production";
				parameters[0][1] = ClinicalBiotaberApp.xsdVersions.getProduction().getBuild().getXsdMajorVersion();
				parameters[0][2] = ClinicalBiotaberApp.xsdVersions.getProduction().getBuild().getXsdMinorVersion();
				parameters[0][3] = ClinicalBiotaberApp.xsdVersions.getProduction().getBuild().getXsdPatchNumber();
				parameters[0][4] = ClinicalBiotaberApp.xsdVersions.getProduction().getBuild().getDslBuildNumber();
				
				int i=1;
				for (Build build : ClinicalBiotaberApp.xsdVersions.getDevelopment().getBuilds()) {
					parameters[i][0] = "development";
					parameters[i][1] = build.getXsdMajorVersion();
					parameters[i][2] = build.getXsdMinorVersion();
					parameters[i][3] = build.getXsdPatchNumber();
					parameters[i][4] = build.getDslBuildNumber();
					i++;
				}
	
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException("Could not load properties and the version information.", e);
			}
		}
		return parameters;
	}


}
