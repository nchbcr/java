package org.nch.bcr.biotab.config;

import groovy.util.FactoryBuilderSupport;

/**
 * 
 * @author John Deardurff 
 */
public class SectionToFileLookupFactoryBuilder extends FactoryBuilderSupport {
	
	public SectionToFileLookupFactoryBuilder(boolean init) {
		super(init);
	}
	
	public void registerObjectFactories() { 
		registerFactory("sectionToFileLookup", new SectionToFileLookupFactory());
		registerFactory("sectionToFileMap", new SectionToFileMapFactory());
		registerFactory("elementExclusionList", new ElementExclusionListFactory());
		registerFactory("element", new ElementFactory());
		registerFactory("condition", new ConditionFactory());
		registerFactory("notIn", new NotInFactory());
		registerFactory("isIn", new IsInFactory());
	}
}
