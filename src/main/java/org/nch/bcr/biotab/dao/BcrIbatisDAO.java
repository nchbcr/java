package org.nch.bcr.biotab.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.nch.bcr.biotab.domain.cache.DisplayMetadataCache;
import org.nch.bcr.biotab.domain.cache.DisplayMetadataItem;

/**
 * The data access layer to the DB.  
 * 
 * @author John Deardurff
 *
 */
public class BcrIbatisDAO extends SqlSessionDaoSupport implements IBcrIbatisDAO {
	
	public DisplayMetadataCache getDisplayMetadataCache() {
		DisplayMetadataCache cache = new DisplayMetadataCache();
		List<DisplayMetadataItem> cacheItems = getSqlSession().selectList("getDisplayMetadataItems");
		for (DisplayMetadataItem item : cacheItems) {
			cache.addDisplayMetadataItem(item);
		}
		return cache;
	}

}
