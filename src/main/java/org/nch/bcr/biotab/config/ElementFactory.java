package org.nch.bcr.biotab.config;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

/**
 * Part of the DSL implementation which -- in part -- requires the parsing of the 
 * sectionToFileMaps.grovy file. This class tells how to parse the 'element' closure. 
 * 
 * @author John Deardurff
 *
 */
public class ElementFactory extends AbstractFactory {
	
	@Override
	public boolean isLeaf() {
		return false;
	}
	
	@Override
	public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) 
		throws InstantiationException, IllegalAccessException {

		return new ElementNode((String) attributes.get("name"));
	}
	
	@Override
	public void setParent(FactoryBuilderSupport builder, Object parent, Object element) {
		
		if (parent != null && parent instanceof ElementExclusionListNode) {
			((ElementExclusionListNode)parent).addElementNode((ElementNode)element);
		}
	}
	
	@Override
	public void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object invoice) {}
	
}
