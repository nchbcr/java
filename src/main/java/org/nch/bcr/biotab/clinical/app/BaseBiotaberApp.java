package org.nch.bcr.biotab.clinical.app;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.nch.bcr.biotab.app.ApplicationContextProvider;
import org.nch.bcr.biotab.app.SpringBootstrapper;
import org.nch.bcr.biotab.app.exceptions.BiotabInitializationException;
import org.nch.bcr.biotab.dao.IBcrIbatisDAO;
import org.nch.bcr.biotab.domain.cache.DisplayMetadataCache;
import org.nch.bcr.biotab.versions.Build;
import org.nch.bcr.biotab.versions.XsdVersions;
import org.nch.bcr.biotab.versions.XsdVersionsFactory;
import org.springframework.beans.factory.BeanFactory;
import org.xml.sax.InputSource;

import com.beust.jcommander.JCommander;

public class BaseBiotaberApp {
	protected static Logger log = Logger.getLogger(BaseBiotaberApp.class);
	
	public enum XmlFileType {
		CLINICAL, OMF, SSF, PPS;

		public String toString() {
			switch (this) {
				case CLINICAL: return "clinical";
				case OMF: return "omf";
				case SSF: return "ssf";
				case PPS: return "pps";
			}
			throw new AssertionError("Unknown xml type: " + this);
		}
	}
	
	public static XsdVersions xsdVersions;
	public static Build build = null;
	public static DisplayMetadataCache displayMetadataCache = null;
	protected static IBcrIbatisDAO bcrIbatisDAO = null;
	protected static List<String> studies = null;
	
	protected static void init(String args[]) throws BiotabInitializationException {
		try {
			loadProperties(args);
	        initCache(args);
		} catch (Throwable e) {
			throw new BiotabInitializationException("biotab app could not be initialized", e);
		}
	}
	
	public static void initCache(String[] args) throws BiotabInitializationException {
		try {
			SpringBootstrapper.bootstrapString();
			BeanFactory factory = ApplicationContextProvider.getApplicationContext();
			bcrIbatisDAO = (IBcrIbatisDAO)factory.getBean("bcrIbatisDAO");
			displayMetadataCache = bcrIbatisDAO.getDisplayMetadataCache();
		} catch (Throwable e) {
			throw new BiotabInitializationException("biotab app could not be initialized -- cache not initialized.", e);
		}
	}
	
	public static void loadProperties(String[] args) throws BiotabInitializationException {
		try {
			CommandLineArgs argsDef = new CommandLineArgs();
			new JCommander(argsDef, args);
		
	        FileInputStream propFile = new FileInputStream(argsDef.propPath);
	        Properties p = new Properties(System.getProperties());
	        p.load(propFile);
	        System.setProperties(p);
	        
	        if(argsDef.studies == null) {
	        	StringTokenizer st = new StringTokenizer(p.getProperty("disease.code"), ":");
	        	studies = new ArrayList<String>();
	        	while(st.hasMoreElements()) {
	        		studies.add(st.nextToken());
	        	}
	        }
	        else {
	        	studies = argsDef.studies;
	        }
	        
	        xsdVersions = XsdVersionsFactory.createInstance(new InputSource(new FileInputStream(p.getProperty("configuration.path") + File.separator + "xsdVersions.xml")));
	        build = xsdVersions.getProduction().getBuild();
		} catch (Throwable e) {
			throw new BiotabInitializationException("biotab app could not be initialized -- properties could not be loaded.", e);
		}
	}
	

}
