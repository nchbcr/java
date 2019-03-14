package org.nch.bcr.biotab.config;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

/**
 * Part of the DSL implementation which -- in part -- requires the parsing of the 
 * sectionToFileMaps.grovy file. This class tells how to parse the 'condition' closure. 
 * 
 * @author John Deardurff
 *
 */
public class ConditionFactory extends AbstractFactory {
	
	@Override
	public boolean isLeaf() {
		return false;
	}
	
	@Override
	public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) 
		throws InstantiationException, IllegalAccessException {

		return new ConditionNode();
	}
	
	@Override
	public void setParent(FactoryBuilderSupport builder, Object parent, Object element) {
		
		if (parent != null && parent instanceof ElementNode) {
			((ElementNode)parent).setConditionNode((ConditionNode)element);
		}
	}
	
	@Override
	public void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object invoice) {}
	
}
