package org.nch.bcr.biotab.clinical.app;

import org.nch.bcr.biotab.app.exceptions.BiotabInitializationException;
import org.nch.bcr.biotab.versions.XsdVersionFileException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The main clinical BioTab application to be run from the command line. This is a thread-safe and multi-threaded application where 
 * the generation of biotabs for a a study is executed in it's own thread.  
 * 
 * -studies acc:blca:brca:cesc:coad:dlbc:esca:gbm:hnsc:kich:kirc:kirp:lgg:lihc:luad:lusc:ov:paad:prad:read:sarc:skcm:stad:thca:ucec:ucs -propPath C:\\TDE\\RUNTIME\\Bin\\properties\\biotabs\\properties
 * 
 * @author John Deardurff 
 */
public class SsfBiotaberApp extends BaseBiotaberApp {

	protected void start(String[] args) {
		try {
			List<Thread> threads = new ArrayList<Thread>();
			
			init(args);
			
			long startTime = System.currentTimeMillis();
			log.info("SSF Biotab Generation Started...");
			for (String study : studies) {
				try {
					SsfBiotaber biotaber = new SsfBiotaber(System.getProperties(), study.toUpperCase());
					Thread t = (new Thread(biotaber));
					t.setPriority(Thread.MAX_PRIORITY);
					// Make sure we can shut down the VM even if a worker thread is still running. 
					t.setDaemon(true);
			        threads.add(t);
			        t.start();
				} catch (BiotabInitializationException e) {
					log.error("Could not start SSF biotabber for " + study.toUpperCase(), e);
				}
			}	
				
			// Make sure the current thread waits for all of the worker threads to stop before moving on. 
			for (Thread t : threads) {
		        t.join();
		    }
			
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			log.info(MessageFormat.format("SSF Biotab Generation Completed (RUNTIME: {0,number,#.###} ) minutes.", ((float)duration/1000.0)/60.0 ));
			
			
		} catch (BiotabInitializationException e) {
			log.fatal("SSF Biotab Generation could not be initialized.", e);
		} catch (XsdVersionFileException e) {
			log.fatal("SSF Biotab Generation could not read the xsd version file. No biotab files were generated.", e);
		} catch (Throwable e) {
			log.fatal("SSF Biotab Generation failed due to an unknown error. ", e);
		}
	}
	
	public static void main(String[] args) {
		SsfBiotaberApp app = new SsfBiotaberApp();
		app.start(args);
	}
}