package org.nch.bcr.biotab.domain.cache;

import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Cache of tblClinicalCrfDataDictionary
 * 
 * @author John Deardurff 
 */
@XmlRootElement(name = "DisplayMetadataCache")
@XmlAccessorType(XmlAccessType.FIELD)
public class DisplayMetadataCache {
	
	@XmlElementWrapper(name="DisplayMetadataCacheMap")
	@XmlElement(name = "DisplayMetadataItem")
	protected HashMap<DisplayMetadataKey, DisplayMetadataItem> cache = new HashMap<DisplayMetadataKey, DisplayMetadataItem>();
	
	public DisplayMetadataCache() {}
	
	public void addDisplayMetadataItem(DisplayMetadataItem item) {
		cache.put(item.getDisplayMetadataKey(), item);
	}
	
	public boolean contains(DisplayMetadataKey key) {
		return cache.containsKey(key);
	}
	
	public DisplayMetadataItem getDisplayMetadataItem(DisplayMetadataKey key) {
		return cache.get(key);
	}
	
	@Override
	public String toString() {
		StringWriter writer;
		Marshaller marshaller;
		try {
			writer = new StringWriter();
			// Make sure that JAXBContext is configured for this class. 
			marshaller = JAXBContext.newInstance(DisplayMetadataCache.class).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(this, writer);

			return writer.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return "(toXmlString ERROR: " + e.getMessage() + ")";
		}
	}
	
	
}
