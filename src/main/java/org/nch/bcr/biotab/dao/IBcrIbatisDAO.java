package org.nch.bcr.biotab.dao;

import org.nch.bcr.biotab.domain.cache.DisplayMetadataCache;

/**
 * The data access layer to the DB.  
 * 
 * @author John Deardurff
 *
 */
public interface IBcrIbatisDAO {
	
	public DisplayMetadataCache getDisplayMetadataCache();

}
