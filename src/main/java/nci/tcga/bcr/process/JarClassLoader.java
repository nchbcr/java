/**
 * 
 */
package nci.tcga.bcr.process;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

/**
 * @author axk058
 *
 * @deprecated
 */
public class JarClassLoader extends ClassLoader {
	private Hashtable<String, Class<?>> classes = new Hashtable<String, Class<?>>();
	
	public JarClassLoader() {
		super(JarClassLoader.class.getClassLoader());
	}
	
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		return findClass(className);
	}
	
	public final ProtectionDomain getDomain() {
		CodeSource code = new CodeSource(null, (Certificate[]) null);
		return new ProtectionDomain(code, getPermissions());
	}
	
	public final Permissions getPermissions() {
		Permissions permissions = new Permissions();
		permissions.add(new AllPermission());
		return permissions;
	}
	
	public Class<?> findClass(String className) {
		Class<?> result = classes.get(className);
		
		if(result != null) {
			return result;
		}
		
		try {
			try {
				result = findSystemClass(className);
			} 
			catch(ClassNotFoundException e) {}
			if(result == null) {
				result = loadClassesInJar(className);
			}
		} 
		catch(Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private Class<?> loadClassesInJar(String className) {
		try {
			FileInputStream x_input = new FileInputStream("./tcga_bcr_biotab-1.0.jar");
			JarInputStream theWholeJar = new JarInputStream(x_input);
			JarEntry aJarEntry = theWholeJar.getNextJarEntry();
			while (aJarEntry != null) {
				if (aJarEntry.getName().contains("dependency") && aJarEntry.getName().endsWith(".jar")) {
					InputStream input = getClass().getResourceAsStream("/" + aJarEntry.getName());
					//System.err.println(aJarEntry.getName());
					JarInputStream aJar = new JarInputStream(input);
					JarEntry aClass = aJar.getNextJarEntry();
					while (aClass != null) {
						System.err.println("JAR ENTRY: " + aClass == null ? "null" : aClass.getName());
						 
						 if (aClass.getName().equals(className)) {
							 
							 ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
					         int nextValue = aJar.read();
					         while(-1 != nextValue) {
								byteStream.write(nextValue);
								nextValue = aJar.read();
					         }
					         
					         byte[] _xyzFile = byteStream.toByteArray();
					     
						     Class<?> result = defineClass(className, _xyzFile, 0, _xyzFile.length, null);
						     classes.put(className, result);
						     
						     //System.err.println(result.getName());
		
						     return result;    
						 }
						 
						 aClass = aJar.getNextJarEntry();
					}
					aJar.close();
				}
				aJarEntry = theWholeJar.getNextJarEntry();
			}
			theWholeJar.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unused")
	private void loadClassesInJar() {
		try {
			File[] files = new File(".").listFiles();
			
			for(File file : files) {
				if(file.isFile() && file.getName().endsWith(".jar") && file.getName().contains("biotab")) {
					System.out.println("JAR name: " + file.getName());
					
					JarFile appJar = new JarFile(file);
					Enumeration<JarEntry> appEntries = appJar.entries();
					
					while(appEntries.hasMoreElements()) {
						JarEntry appElement = appEntries.nextElement();
						System.out.println("APP ENTRY name: " + appElement.getName());
						
						if(appElement.getName().matches("dependency/.*.jar")) {
							JarFile depJar = new JarFile("/" + appElement.getName());
							Enumeration<JarEntry> depEntries = depJar.entries();
							
							while(depEntries.hasMoreElements()) {
								JarEntry depElement = depEntries.nextElement();
								System.out.println("DEP ENTRY name: " + depElement.getName());
								
								if(depElement.getName().endsWith(".class")) {
									InputStream in = depJar.getInputStream(depElement);
									ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
									int nextValue = in.read();
									while(-1 != nextValue) {
										byteStream.write(nextValue);
										nextValue = in.read();
									}
									
									String modClassName = depElement.getName();
									
									if(modClassName.indexOf("/") > -1) {
										modClassName = depElement.getName().substring(0, depElement.getName().indexOf(".class")).replace('/', '.');
									}
									
									byte[] classBytes = byteStream.toByteArray();
									Class<?> result = defineClass(modClassName, classBytes, 0, classBytes.length, null);
									
									classes.put(modClassName, result);
								}
							}
							depJar.close();
						}
					}
					appJar.close();
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
