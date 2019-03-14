package org.nch.bcr.biotab.clinical.app;

import java.util.List;

import com.beust.jcommander.Parameter;

/**
 * The arguments for the command line. 
 * 
 * @author John Deardurff 
 */
public class CommandLineArgs {
	
	public static final String BUILD = "-build";
	@Parameter(names = BUILD, description = "The fully build number of the xsds that the biotabs should reference.")
	public String build = "";
	
	public static final String PROPERTIES_PATH = "-propPath";
	@Parameter(names = PROPERTIES_PATH, description = "The fully qualified path to the properties file.")
	public String propPath = "";
	
	public static final String STUDIES = "-studies";
	@Parameter(names = STUDIES, splitter = CommandLineStudySplitter.class, description = "Colon (:) separated list of studies.", required = false)
	public List<String> studies;


}
