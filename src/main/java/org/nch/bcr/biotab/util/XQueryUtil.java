package org.nch.bcr.biotab.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Mo utils. 
 * 
 * @author John Deardurff 
 */
public class XQueryUtil {
	
	private Map<String, String> namespaces = new HashMap<String, String>();
	
	public XQueryUtil(Map<String, String> namespaces) {
		this.namespaces = namespaces;
	}
	
	public XQueryUtil(XmlObject xmlObject) {
		XmlCursor xmlCursor = xmlObject.newCursor();
		xmlCursor.toFirstChild();

		Map<String, String> tmpNamespaces = new HashMap<String, String>();
		xmlCursor.getAllNamespaces(tmpNamespaces);
		for (String key : tmpNamespaces.keySet()) {
			if (key != null && tmpNamespaces.get(key) != null) {
				namespaces.put(tmpNamespaces.get(key), key);
			}
		}
		xmlCursor.dispose();
	}
	
	public List<String> getNamespaceAliases() {
		return new ArrayList(namespaces.values());
	}
	
	
	
	public XmlObject getXmlObjectForNode(Node node, XmlObject root) {
		XmlObject result = null;
		XmlObject[] children = root.selectPath(getXPath(node));
		if (children.length > 0) {
			result = children[0];
		}
		return result;
	}
	
	
	
	public String getParentXPath(XmlObject obj) {
		return getParentXPath(obj.getDomNode());
	}
	public String getParentXPath(Node node) {
		return getXPath(node.getParentNode());
	}
	
	/**
	 * Constructs a XPath query to the supplied node.
	 * 
	 * @param source to retrieve XPath from 
	 * @return XPath
	 */
	public String getXPath(XmlObject obj) {
		return getXPath(obj.getDomNode());
	}
	
	
	/**
	 * Constructs a XPath query to the supplied node.
	 * 
	 * @param node to retrieve XPath from 
	 * @return XPath
	 */
	public String getXPathBACK(Node node) {
		if (null == node)
			return "";

		Node parentNode = null;
		Stack<Node> hierarchy = new Stack<Node>();
		StringBuffer result = new StringBuffer();

		// push element on stack
		hierarchy.push(node);

		parentNode = node.getParentNode();
		while (null != parentNode && parentNode.getNodeType() != Node.DOCUMENT_NODE) {
			// push on stack
			hierarchy.push(parentNode);

			// get parent of parent
			parentNode = parentNode.getParentNode();
		}

		// construct xpath
		Object obj = null;
		while (!hierarchy.isEmpty() && null != (obj = hierarchy.pop())) {
			Node currentNode = (Node) obj;

			// only consider elements
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) currentNode;

				// element - append slash and element name
				result.append("/");
				result.append(this.namespaces.get(currentNode.getNamespaceURI()));
				
				
				System.err.println("Namespace URI : " + currentNode.getNamespaceURI());
				
				result.append(":");
				result.append(currentNode.getLocalName());
				
					// no known attribute we could use - get sibling index
					int prev_siblings = 1;
					Node prevSiblingNode = currentNode.getPreviousSibling();
					while (null != prevSiblingNode) {
						if (prevSiblingNode.getNodeType() == currentNode.getNodeType()) {
							if (prevSiblingNode.getLocalName().equalsIgnoreCase(
									currentNode.getLocalName())) {
								prev_siblings++;
							}
						}
						prevSiblingNode = prevSiblingNode.getPreviousSibling();
					}
					
					// TODO: Uncomment the following when the Saxon classpath BS has been solved. 
					result.append("[" + prev_siblings + "]");
				}
			}
		return "." + result.toString();
	}
	
	
	/**
	 * Constructs a XPath query to the supplied node.
	 * 
	 * @param node to retrieve XPath from 
	 * @return XPath
	 */
	// public String getXPathWithFuntionIds(Node node) {
	public String getXPath(Node node) {
		if (null == node)
			return "";

		Node parentNode = null;
		Stack<Node> hierarchy = new Stack<Node>();
		StringBuilder result = new StringBuilder();

		// push element on stack
		hierarchy.push(node);

		parentNode = node.getParentNode();
		while (null != parentNode && parentNode.getNodeType() != Node.DOCUMENT_NODE) {
			// push on stack
			hierarchy.push(parentNode);

			// get parent of parent
			parentNode = parentNode.getParentNode();
		}

		// construct xpath
		Object obj = null;
		while (!hierarchy.isEmpty() && null != (obj = hierarchy.pop())) {
			Node currentNode = (Node) obj;

			// only consider elements
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) currentNode;

				// element - append slash and element name
				result.append("/*[local-name()='");
				result.append(currentNode.getLocalName());
				result.append("' and namespace-uri()='");
				result.append(currentNode.getNamespaceURI());
				result.append("']");

				// no known attribute we could use - get sibling index
				int prev_siblings = 1;
				Node prevSiblingNode = currentNode.getPreviousSibling();
				while (null != prevSiblingNode) {
					if (prevSiblingNode.getNodeType() == currentNode.getNodeType()) {
						
						//System.err.println("PREFIX:LOCAL_NAME: " + prevSiblingNode.getPrefix() + ":" + prevSiblingNode.getLocalName());
						if (prevSiblingNode.getPrefix().equalsIgnoreCase(currentNode.getPrefix())) {
							if (prevSiblingNode.getLocalName().equalsIgnoreCase(currentNode.getLocalName())) {
									prev_siblings++;
							}
						}
					}
					prevSiblingNode = prevSiblingNode.getPreviousSibling();
				}
				
				// TODO: Uncomment the following when the Saxon classpath BS has been solved. 
				result.append("[" + prev_siblings + "]");
			}
		}
		return "." + result.toString();
	}
	
	
	public String appendNamespaceDeclarationForXQuery(String xpath) {
		StringBuffer result = new StringBuffer();
		for (String alias : namespaces.keySet()) {
			if (alias != null && !alias.trim().equals("")) {
				result.append("declare namespace " + namespaces.get(alias) + "='" + alias + "'; ");
			}
		}
		return result.toString() + xpath;
	}
	

}
