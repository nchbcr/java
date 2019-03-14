package nci.tcga.bcr.process;

import com.beust.jcommander.Parameter;

/**
 * The arguments for the command line. 
 * 
 * @author John Deardurff 
 */
public class CommandLineArgs {
	
	public static final String PROPERTIES_PATH = "-propPath";
	@Parameter(names = PROPERTIES_PATH, description = "The fully qualified path to the properties file.")
	public String propPath = "";

}
