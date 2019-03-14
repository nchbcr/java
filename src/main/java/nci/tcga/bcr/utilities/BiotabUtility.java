/**
 * 
 */
package nci.tcga.bcr.utilities;

import com.beust.jcommander.JCommander;
import nci.tcga.bcr.beans.DateObject;
import nci.tcga.bcr.beans.StringDate;
import nci.tcga.bcr.beans.TextToDate;
import nci.tcga.bcr.beans.ValidDate;
import nci.tcga.bcr.errors.BiotabInitializationException;
import nci.tcga.bcr.errors.DateFatalException;
import nci.tcga.bcr.process.CommandLineArgs;
import nu.xom.XPathContext;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author axk058
 *
 */
public class BiotabUtility {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
    public static Properties prop;
    public static String prop_loc;
    private static Logger logger;
    
    /**
     * 
     */
    public static void init(String[] args) {
    	try {
			prop = BiotabUtility.readPropetiesFile(BiotabUtility.loadProperties(args));
		} catch (BiotabInitializationException e) {
			e.printStackTrace();
		}
        prop_loc = (String)getPropValueFromProperties(prop, "log4j.properties");
        logger = initLogger(BiotabUtility.class.getName(), prop_loc);
    }
	
	/**
	 * Initiating the Log4j logger.
	 * 
	 * @param className Name of the class the logger is working on.
	 * @return Logger object.
	 */
	public static Logger initLogger(String className, String propertiesLocation) {
		logger = Logger.getLogger(className);
		DOMConfigurator.configure(propertiesLocation);
		
		return logger;
	}
	
	/**
	 * This method is responsible for reading properties files.
	 * 
	 * @param propertiesLocation Location of the properties file.
	 * @return Properties object
	 */
	public static Properties readPropetiesFile(String propertiesLocation) {
		Properties properties = null;
		 
    	try {
    		properties = new Properties();
    		FileInputStream propFile = new FileInputStream(propertiesLocation);
    		properties.load(propFile);
    		
    	} 
    	catch(IOException ioe) {
    		logger.fatal(ioe.getMessage());
        }
    	
    	return properties;
	}
	
	/**
	 * This method is for retrieving the property values by property key.
	 * 
	 * @param prop Properties object.
	 * @param propKey Property key.
	 * @return Either a Boolean object or a list of string.
	 */
	public static Object getPropValueFromProperties(Properties prop, String propKey) {
		String propValue = (String)prop.get(propKey);
		if(propValue != null) {
			if(propValue.indexOf(",") > -1) {
				List<String> strList = new ArrayList<String>();
				StringTokenizer st = new StringTokenizer(propValue, ",");
				while(st.hasMoreTokens()) {
					strList.add(st.nextToken());
				}
				return strList;
			}
			else if(propValue.indexOf(";") > -1) {
				List<String> strList = new ArrayList<String>();
				StringTokenizer st = new StringTokenizer(propValue, ";");
				while(st.hasMoreTokens()) {
					strList.add(st.nextToken());
				}
				return strList;
			}
			else if(propValue.equals("true") || propValue.equals("false")) {
				return Boolean.valueOf(propValue);
			}
			else {
				return propValue;
			}
		}
		
		return null;
	}
	
	/**
	 * This method is for generating the XPath context used for XOM queries.
	 * 
	 * @param context XPathContext object.
	 * @param targetNamespaces List of namespaces.
	 * @return XPathContext object.
	 */
	public static XPathContext generateXPathContext(XPathContext context, List<String> targetNamespaces) {		
		for(String ns : targetNamespaces) {
			int numOfSeparators = countUrlSeparator(ns);
			int indexThirdLast = -1;
			for(int i = 0; i < numOfSeparators - 2; i++) {
				indexThirdLast = ns.indexOf("/", indexThirdLast + 1);
			}
			int indexSecondLast = ns.indexOf("/", indexThirdLast + 1);
			int indexLast = ns.lastIndexOf("/");
			
			String strSection = ns.substring(indexSecondLast + 1, indexLast);
			String prefix = strSection;
			
			if(strSection.equals("administration")) {
				prefix = "admin";
			}
			else if(strSection.equals("biospecimen")) {
				prefix = "bio";
			}
			else if(strSection.equals("shared")) {
				String strSub = ns.substring(indexThirdLast + 1, indexSecondLast);
				
				if(strSub.equals("biospecimen")) {
					prefix = "bio_" + strSection;
				}
			}
			else if(strSection.matches("[\\d]{4}")) {
				prefix = "xs";
			}
			else if(strSection.equals("auxdata")) {
				prefix = "auxiliary";
			}
 			
			context.addNamespace(prefix, ns);
		}
		
		return context;
	}
	
	/**
	 * This method is nothing but calculating difference between two times. 
	 * 
	 * Meant for looking into processing times in here.
	 * 
	 * @param start Started time.
	 * @param end Finished time.
	 * @return Time difference in hours, minutes, and seconds.
	 */
	public static String determineProcessedTime(long start, long end) {
		int millis = (int)(end - start);
		int hours = millis / 1000 / 3600;
		int minutes = ((millis / 1000) % 3600) / 60;
		int seconds = ((millis / 1000) % 3600) % 60;
		
		return "Total Processed Time = " + hours + " hours " + minutes + " minutes " + seconds + " seconds ";
	}
	
	/**
	 * Find out how many file separators there are in a path
	 * 
	 * @param path Absolute path of a file.
	 * @return Number of file separators in the given path.
	 */
	public static int countFileSeparator(String path) {
		String pattern = Pattern.quote(FILE_SEPARATOR);
		String[] chunks = path.split(pattern);
		return chunks.length - 1;
	}
	
	/**
	 * Find out how many URL separators there are in a path
	 * 
	 * @param path Absolute path of a file.
	 * @return Number of file separators in the given path.
	 */
	public static int countUrlSeparator(String path) {
		String pattern = Pattern.quote("/");
		String[] chunks = path.split(pattern);
		return chunks.length - 1;
	}
	
	/**
	 * Specific method to grab the folder names which represents the top of stack
	 * for each batch. File name in the pattern of... nationwidechildrens.org_STAD.bio.Level_1.48.12.0,
	 * for example.
	 * 
	 * @param files File array.
	 * @return List of names of top of stack folders.
	 */
	public static List<String> selectTopOfStack(File[] files) {
		List<String> folders = new ArrayList<String>();
		String directoryName = null;
		int indexFirstPeriod = 0;
		int batch;
		int revision;
		int minorRev;
		int prevBatch = 0;
		int prevRev = 0;
		int prevMinor = 0;
		
		Arrays.sort(files, new NumberedFileComparator());
		
		for(File file : files) {
			if(file.isDirectory()) {
				directoryName = file.getName().trim();
				int indexHyphen = directoryName.lastIndexOf("_");
				indexFirstPeriod = directoryName.indexOf(".", indexHyphen + 1);
				int indexSecondPeriod = directoryName.indexOf(".", indexFirstPeriod + 1);
				int indexLastPeriod = directoryName.indexOf(".", indexSecondPeriod + 1);
				
				batch = Integer.parseInt(directoryName.substring(indexFirstPeriod + 1, indexSecondPeriod));
				revision = Integer.parseInt(directoryName.substring(indexSecondPeriod + 1, indexLastPeriod));
				minorRev = Integer.parseInt(directoryName.substring(indexLastPeriod + 1));
				
				if(prevBatch == 0) {
					prevBatch = batch;
				}
				
				if(prevRev == 0) {
					prevRev = revision;
				}
				
				if(prevMinor == 0) {
					prevMinor = minorRev;
				}
				
				if(batch == prevBatch) {
					if(revision == prevRev) {
						if(minorRev > prevMinor) {
							prevMinor = minorRev;
						}
					}
					else if(revision > prevRev) {
						prevRev = revision;
					}
				}
				else if(batch > prevBatch) {
					folders.add(directoryName.substring(0, indexFirstPeriod) + "." + prevBatch + "." + prevRev + "." + prevMinor);
					prevBatch = batch;
					prevRev = revision;
					prevMinor = minorRev;
				}
			}
		}
		
        if(directoryName != null) {
            folders.add(directoryName.substring(0, indexFirstPeriod) + "." + prevBatch + "." + prevRev + "." + prevMinor);
        }
		
		return folders;		
	}
	
	/**
	 * 
	 * @return
	 */
	public static Connection getDatabaseConnection() {
		Connection conn = null;
		
		String driverClassname = (String) getPropValueFromProperties(prop, "data_warehouse_data_source.driver_name");
		String driverUrl = (String) getPropValueFromProperties(prop, "data_warehouse_data_source.url");
		String username = (String) getPropValueFromProperties(prop, "data_warehouse_data_source.username");
		String password = (String) getPropValueFromProperties(prop, "data_warehouse_data_source.password");
		
		try {
			Class.forName(driverClassname);
			conn = DriverManager.getConnection(driverUrl, username, password);
		}
		catch (ClassNotFoundException cnfe) {
			logger.fatal("Cannot find driver's class\n" + cnfe.getMessage());
		}
		catch(SQLException sqle) {
			logger.fatal("Problems connecting to database\n" + sqle.getMessage());
		}
		
		return conn;
	}
	
	public static StringDate createStringDate(String year, String month, String day, String elementName) throws DateFatalException {
		StringDate sd = new StringDate();
		DateObject obj = new DateObject();
		String warnMsg = "";
		boolean isMissing = false;
		
		if((day == null || day.trim().equals("")) && (month == null || month.trim().equals("")) && 
				(year == null || year.trim().equals(""))) {
			obj.setError(true);
			obj.setFatalMessage("Invalid " + elementName + ". Date cannot be empty or null!");
			sd.setDate(null);
			obj.setProcurement("[Not Available]");
		}
		else {
			if(year == null || year.trim().equals("")) {
				obj.setError(true);
				obj.setFatalMessage("Invalid " + elementName + ". " + "Year cannot be empty!");
				sd.setDate(null);
				obj.setProcurement("[Not Available]");
				return sd;
			}
			else {
				year = year.trim();
			}
			
			if(day == null || day.trim().equals("")) {
				day = "1";
				warnMsg += ("Missing day from " + elementName + ". Default day was used!");
				isMissing = true;
			}
			else {
				day = day.trim();
			}
			
			if(month == null || month.trim().equals("")) {
				month = "1";
				warnMsg += ("Missing month from " + elementName + ". " + (warnMsg.equals("") ? "Default month was used!" : ", Default month was used!"));
				isMissing = true;
			}
			else {
				month = month.trim();
			}
			
			obj.setWarningMessage(warnMsg);
		
			String strDate = year + "-" + month + "-" + day;
			
			// This is a business rule validation since we have done with the 
			// syntax validation earlier.
			ValidDate vd = isValidDate(strDate, elementName, obj);
			boolean isValid = vd.isValid();
			obj = vd.getObj();
			
			if(isValid) {
				obj.setWarningMessage(warnMsg);
				sd.setDate(strDate);
				sd.setObj(obj);
			}
			else {
				sd.setDate(null);
				sd.setObj(obj);
				obj.setProcurement(null);
				throw new DateFatalException(obj.getFatalMessage());
			}
			
			if(isMissing) {
				if(isValid) {
					obj.setProcurement("[Not Available]");
				}
			}
		}
		
		return sd;
	}
	
	private static ValidDate isValidDate(String strDate, String elementName, DateObject obj) throws DateFatalException {
		TextToDate ttd = textToDate(strDate, obj);
		Date dteDate = ttd.getDate();
		obj = ttd.getObj();
		ValidDate vd = new ValidDate();
		
		if(dteDate == null) {
			obj.setFatalMessage("Invalid " + elementName + ". " + strDate + " has failed date validation!");
			obj.setProcurement(null);
			vd.setValid(false);
			vd.setObj(obj);
			return vd;
		}
		
		Date dteToday = new Date();
		
		if(dteDate.after(dteToday)) {
			obj.setFatalMessage("Invalid " + elementName + ". " + strDate + " cannot be after today's date!");
			obj.setProcurement(null);
			vd.setValid(false);
			vd.setObj(obj);
			return vd;
		}
		
		// Not allowing dates before 1900 to be used
		ttd = textToDate("1900-1-1", obj);
		Date dteOldDate = ttd.getDate();
		obj = ttd.getObj();
		
		if(dteDate.before(dteOldDate)) {
			obj.setWarningMessage("Invalid " + elementName + ". " + strDate + " cannot be before the year 1900!");
			obj.setProcurement(null);
			throw new DateFatalException(obj.getFatalMessage());
		}
		
		vd.setValid(true);
		vd.setObj(obj);
		return vd;
	}
	
	private static TextToDate textToDate(String date, DateObject obj) throws DateFatalException {
		SimpleDateFormat sdf = null;
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date dteDate = null;
		TextToDate textToDate = new TextToDate();

		try {
			// This is very important to set lenient to false on the formatter; otherwise, 
			// the formatter is going to accept some bogus dates like 12/32/2000
			sdf.setLenient(false);
			dteDate = sdf.parse(date);
		} 
		catch (ParseException pe) {
			obj.setFatalMessage(pe.getMessage() + "!");
			obj.setProcurement(null);
			textToDate.setObj(obj);
			throw new DateFatalException(obj.getFatalMessage());
		}
		
		textToDate.setDate(dteDate);
		
		if(textToDate.getObj() == null) {
			textToDate.setObj(obj);
		}
		
		return textToDate;
	}
	
	private static String loadProperties(String[] args) throws BiotabInitializationException {
		CommandLineArgs argsDef = new CommandLineArgs();
		new JCommander(argsDef, args);
		return argsDef.propPath;
	}

    public static InputStream streamResourceFromClasspath(String rootDirectoryName, String resourceName) throws BiotabInitializationException{
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try{
            Resource[] resources = resolver.getResources("classpath*:" + rootDirectoryName + "/**/" + resourceName);
            if(resources.length > 0) {
                return resources[0].getInputStream();
            }
        } catch (IOException e){
            throw new BiotabInitializationException("Unable to access " + resourceName + " in classpath root directory " + rootDirectoryName);
        }

        throw new BiotabInitializationException(
                "Unable to locate resource in classpath root directory " + rootDirectoryName +
                        " that matches the name [" +  resourceName + "]");
    }
}
