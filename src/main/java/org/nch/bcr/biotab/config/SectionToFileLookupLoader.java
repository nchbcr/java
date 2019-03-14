package org.nch.bcr.biotab.config;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.nch.bcr.biotab.clinical.app.BaseBiotaberApp;

/**
 * 
 * @author John Deardurff 
 */
public class SectionToFileLookupLoader {
	
	private static final SectionToFileLookupFactoryBuilder builder = new SectionToFileLookupFactoryBuilder(true);

	public static SectionToFileLookupNode load(BaseBiotaberApp.XmlFileType xmlFileType, String diseaseCode) throws IOException {
		
		String filePath = null;
		switch (xmlFileType) {
			case CLINICAL: 
				filePath = System.getProperties().getProperty("configuration.path") + File.separator + "sectionToFileMaps.groovy";
				break;
			case OMF: 
				filePath = System.getProperties().getProperty("configuration.path") + File.separator + "omfSectionToFileMaps.groovy";
				break;
			case SSF:
				filePath = System.getProperties().getProperty("configuration.path") + File.separator + "ssfSectionToFileMaps.groovy";
				break;
			case PPS:
				filePath = System.getProperties().getProperty("configuration.path") + File.separator + "ppsSectionToFileMaps.groovy";
				break;
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line  = reader.readLine();
		while (line != null) {
			stringBuilder.append(line + System.getProperty("line.separator"));
			line = reader.readLine();
		}
		reader.close();
			
		Binding groovyBinding = new Binding();
		groovyBinding.setVariable("builder", builder);
		groovyBinding.setVariable("diseaseCode", diseaseCode.toLowerCase());
		final GroovyShell interp = new GroovyShell(groovyBinding);
		SectionToFileLookupNode result = (SectionToFileLookupNode)(interp.evaluate("builder." + stringBuilder.toString()));
		org.codehaus.groovy.reflection.ClassInfo.clearModifiedExpandos(); 
		
		return result;
	}

}
