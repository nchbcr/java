package org.nch.bcr.biotab.util;

import org.apache.xmlbeans.SchemaParticle;
import org.apache.xmlbeans.SchemaProperty;

/**
 * Mo utils.
 * 
 * @author John Deardurff 
 */
public class XMLBeansXSDUtilsBasic {
	
	public static String getCdeFromSchemaParticle(SchemaParticle schemaParticle) {
		String result = "";
		SchemaProperty[] attrs = schemaParticle.getType().getAttributeProperties();
		 if (attrs == null) {
			 //System.err.println("------ NO ATTRIBUTES");
		 } else {
			 for (SchemaProperty attr : attrs) {

				 if ( attr.getName().getLocalPart().equals("cde")) {
					 result = attr.getDefaultText();
				 }
			 }
		 }
		return result;
	}
	
	public static String composeXPath(String parentPath, SchemaParticle schemaParticle) {
		return parentPath + XMLBeansXSDUtilsBasic.getXPathFromSchemaParticle(schemaParticle);
	}
	
	public static String getXPathFromSchemaParticle(SchemaParticle schemaParticle) {
		
		return "/*[local-name()='" + schemaParticle.getName().getLocalPart() + "' and namespace-uri()='" + schemaParticle.getName().getNamespaceURI() + "']";

	}


}
