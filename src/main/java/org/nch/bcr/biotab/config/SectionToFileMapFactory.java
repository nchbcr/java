package org.nch.bcr.biotab.config;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

/**
 * Part of the DSL implementation which -- in part -- requires the parsing of the 
 * sectionToFileMaps.grovy file. This class tells how to parse the 'sectionToFileMap' closure. 
 * 
 * @author John Deardurff
 *
 */
public class SectionToFileMapFactory extends AbstractFactory {
	
	@Override
	public boolean isLeaf() {
		return false;
	}
	
	@Override
	public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) 
		throws InstantiationException, IllegalAccessException {

		SectionToFileMapNode result = new SectionToFileMapNode(
				
				(String) attributes.get("sectionId"), 
				(String) attributes.get("parentSectionId"),
				(String) attributes.get("formType"),
				(String) attributes.get("elementName"), 
				(String) attributes.get("containerXPath"), 
				(Boolean) attributes.get("repeating"),
				(String) attributes.get("biotabFilename"));
		
		return result;
	}
	
	@Override
	public void setParent(FactoryBuilderSupport builder, Object parent, Object element) {
		
		if (parent != null && parent instanceof SectionToFileLookupNode) {
			((SectionToFileLookupNode)parent).addSectionToFileMap((SectionToFileMapNode)element);
		}
	}
	
	@Override
	public void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object invoice) {}
	
	

}
