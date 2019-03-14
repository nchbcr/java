/**
 * 
 */
package nci.tcga.bcr.process;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author axk058
 *
 */
@SuppressWarnings("unused")
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		JarClassLoader cl = new JarClassLoader();
		Class<?> someClass;
		Object instance;
		
		
		try {  
			someClass = cl.loadClass("nci.tcga.bcr.process.Process");
			//someClass = cl.loadClass("nu.xom.ParsingException");
			instance = someClass.newInstance();
			someClass.getMethod("start").invoke(instance);
		}
		catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
		catch (InstantiationException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		List<String> folders = null;
		BiotabProcess process = new BiotabProcess(folders);
        process.process(args);
	
	}

}
