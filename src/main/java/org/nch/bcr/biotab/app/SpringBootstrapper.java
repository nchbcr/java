package org.nch.bcr.biotab.app;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author John Deardurff 
 */
public class SpringBootstrapper {
	
private static Logger log = Logger.getLogger(SpringBootstrapper.class);
	
	public static void bootstrapString() throws Exception {
		initSpringContainer();
	}
	
	protected static void initSpringContainer() throws Exception {
        log.info("Initializing Spring application context");
        new ClassPathXmlApplicationContext("classpath:spring.xml");
    }

}
