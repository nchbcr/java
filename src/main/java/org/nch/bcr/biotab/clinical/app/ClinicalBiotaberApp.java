package org.nch.bcr.biotab.clinical.app;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.nch.bcr.biotab.app.exceptions.BiotabInitializationException;
import org.nch.bcr.biotab.versions.XsdVersionFileException;

/**
 * The main clinical BioTab application to be run from the command line. This is a thread-safe and multi-threaded application where 
 * the generation of biotabs for a a study is executed in it's own thread.  
 * 
 * -studies acc:blca:brca:cesc:coad:dlbc:esca:gbm:hnsc:kich:kirc:kirp:lgg:lihc:luad:lusc:ov:paad:prad:read:sarc:skcm:stad:thca:ucec:ucs -propPath C:\\TDE\\RUNTIME\\Bin\\properties\\biotabs\\properties
 * 
 * @author John Deardurff 
 */
public class ClinicalBiotaberApp extends BaseBiotaberApp {
	
	protected void start(String[] args) {
		try {
			List<Thread> threads = new ArrayList<Thread>();
			
			init(args);
			
			long startTime = System.currentTimeMillis();
			log.info("Clinical Biotab Generation Started...");
			for (String study : studies) {
				try {
					ClinicalBiotaber biotaber = new ClinicalBiotaber(System.getProperties(), study.toUpperCase());
					Thread t = (new Thread(biotaber));
					t.setPriority(Thread.MAX_PRIORITY);
					// Make sure we can shut down the VM even if a worker thread is still running. 
					t.setDaemon(true);
			        threads.add(t);
			        t.start();
				} catch (BiotabInitializationException e) {
					log.error("Could not start clinical biotaber for " + study.toUpperCase(), e);
				}
			}	
				
			// Make sure the current thread waits for all of the worker threads to stop before moving on. 
			for (Thread t : threads) {
		        t.join();
		    }
			
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			log.info(MessageFormat.format("Clinical Biotab Generation Completed (RUNTIME: {0,number,#.###} ) minutes.", ((float)duration/1000.0)/60.0 ));
			
			
		} catch (BiotabInitializationException e) {
			log.fatal("Clinical Biotab Generation could not be initialized.", e);
		} catch (XsdVersionFileException e) {
			log.fatal("Clinical Biotab Generation could not read the xsd version file. No biotab files were generated.", e);
		} catch (Throwable e) {
			log.fatal("Clinical Biotab Generation failed due to an unknown error. ", e);
		}
	}

	
	public static void main(String[] args) {
		ClinicalBiotaberApp clinApp = new ClinicalBiotaberApp();
		clinApp.start(args);
	}
}
