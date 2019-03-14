package org.nch.bcr.biotab.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlCursor.TokenType;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * Mo utils.
 * 
 * @author John Deardurff 
 */
public class XMLBeansUtils {

	
	/**
	 * Returns the QName for the "root element" of the argument
	 * 
	 * @param o the xmlBeans object for which the QName is returned.
	 */
	public static QName getQNameFor(XmlObject o) {
		XmlCursor c = o.newCursor();
		try {
			c.toFirstChild();
			return c.getName();
		} finally {
			c.dispose();
		}
	}
	
	public static Map<String,String>extractAttributes(XmlObject source){
		Map<String,String>result=new HashMap<String,String>();
		XmlCursor curs=source.newCursor();
		while(curs.hasNextToken()) {
			TokenType type=curs.toNextToken();
			if(TokenType.END.equals(type)) break;
			if(TokenType.ATTR.equals(type)) {
				QName q=curs.getName();
				String val=curs.getTextValue();
				result.put(q.getLocalPart(),val);
			}
		}
		return result;
	}
	
	/**
	 * extract the text content of an XML element 
	 * 
	 * @param source the xml element
	 * 
	 * @return the text content, or "" if element has no content
	 */
	public static String extractElementTextAsString(XmlObject source){
		XmlCursor c=null;
		try{
			c=source.newCursor();
			while(c.hasNextToken()){
				if(c.toNextToken().equals(TokenType.TEXT)){
					return c.getChars().trim();	
				}
			}
			return "";
		}finally{
			try{
				c.dispose();
			}catch(Exception e){}
		}
	}
	
	/**
	 * Fast-forward the cursor up to the element with the given QName
	 *  
	 * @param cursor
	 * @param name
	 * @return false if element does not exist
	 */
	public static boolean skipToElement(XmlCursor cursor, QName name){
		// walk through element tree in prefix order (root first, then children from left to right)
		if(name.equals(cursor.getName())) return true;
		boolean hasMoreChildren=true;
		int i=0;
		while(hasMoreChildren){
			hasMoreChildren = cursor.toChild(i++);
			if(hasMoreChildren){
				boolean foundInChild = skipToElement(cursor, name);
				if(foundInChild) return true;
				cursor.toParent();
			}
		}
		return false;
	}
	
	/**
	 * Fast-forward the cursor up to the element with one of the given QNames
	 *  
	 * @param cursor
	 * @param names - list of acceptable qnames
	 * @return false if element does not exist
	 */
	public static boolean skipToElement(XmlCursor cursor, QName[] names){
		// walk through element tree in prefix order (root first, then children from left to right)
		for(QName name: names){	
			if(name.equals(cursor.getName())) return true;
		}
		boolean hasMoreChildren=true;
		int i=0;
		while(hasMoreChildren){
			hasMoreChildren = cursor.toChild(i++);
			if(hasMoreChildren){
				boolean foundInChild = skipToElement(cursor, names);
				if(foundInChild) return true;
				cursor.toParent();
			}
		}
		return false;
	}
	
	/**
	 * extract an array of XML elements identified by their qname from an XML source 
	 * document. 
	 * 
	 * @param source - the xml fragment
	 * @param q - QName of elements to extract
	 * @param asClass - the XMLbeans class of the elements to extract
	 * @return List<T> - a list of XMLBeans objects with the correct runtime type
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T>extractAllChildElements(XmlObject source, QName q){
		List<T>results=new ArrayList<T>();
		XmlCursor cursor=null;
		try{
			if(source != null){
				cursor = source.newCursor();
				skipToElement(cursor, q);
				String ns=q.getNamespaceURI();
				boolean hasMore =true;
				while(hasMore){
					XmlObject next = XmlObject.Factory.parse(cursor.newXMLStreamReader());
					QName name = cursor.getName();
					if( (name!=null &&  (ns.equals(name.getNamespaceURI()) || "*".equals(ns))) 
							&& q.getLocalPart().equals(name.getLocalPart())){
						results.add((T)next);
						
					}
					hasMore = cursor.toNextSibling();
				}
			}
		}
		catch(XmlException e){
			// Should never happen. 
			e.printStackTrace();
		}
		finally{
			if(cursor!=null)cursor.dispose();
		}
		return results;
	}

	
	
	public static XmlObject getParentXmlObject(XQueryUtil xQuery, XmlObject root, XmlObject obj) {
		XmlObject result = null;
		XmlObject[] children = root.selectPath(xQuery.appendNamespaceDeclarationForXQuery(xQuery.getParentXPath(obj)));
		if (children.length > 0) {
			result = children[0];
		}
		return result;
	}
	
	public static String getProcurmentStatus(XmlObject xmlObject) {
		String result = "";
		if (xmlObject != null) {
			Map<String, String> attributes = XMLBeansUtils.extractAttributes(xmlObject);
			String _procurmentStatus = attributes.get("procurement_status");
			if (_procurmentStatus == null || _procurmentStatus.trim().equals("")) {
				_procurmentStatus = "";
			}
			result = _procurmentStatus;
		} else {
			// This is the case where the xmlObject is null, which implies that the cell value is also the empty string.
			// So, in this case it is safe to assume that the procurement status should be set to 'Not Available'.
			result = "Not Available";
		}
		return result;
	}
	
}
