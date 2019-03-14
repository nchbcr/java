package org.nch.bcr.biotab.clinical.app;

import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.converters.IParameterSplitter;

/**
 * So that we can have a ':' separated list of disease codes on the command line. 
 * 
 * @author John Deardurff
 *
 */
public class CommandLineStudySplitter implements IParameterSplitter {
	
	public List<String> split(String value) {
		return Arrays.asList(value.split(":"));
	}

}
