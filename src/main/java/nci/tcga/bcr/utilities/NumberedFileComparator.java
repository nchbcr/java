/**
 * 
 */
package nci.tcga.bcr.utilities;

import java.io.File;
import java.util.Comparator;

/**
 * @author axk058
 *
 */
public class NumberedFileComparator implements Comparator<File> {
	public int compare(File o1, File o2) {
		String f1 = o1.getName();
		String f2 = o2.getName();
		int val = f1.compareTo(f2);
		
		if (val != 0){
			int indexHyphen = f1.lastIndexOf("_");
			int indexFirstPeriod = f1.indexOf(".", indexHyphen + 1);
			int indexSecondPeriod = f1.indexOf(".", indexFirstPeriod + 1);
			int indexLastPeriod = f1.indexOf(".", indexSecondPeriod + 1);
			
			int batch1 = Integer.parseInt(f1.substring(indexFirstPeriod + 1, indexSecondPeriod));
			int revision1 = Integer.parseInt(f1.substring(indexSecondPeriod + 1, indexLastPeriod));
			int minorRev1 = Integer.parseInt(f1.substring(indexLastPeriod + 1));
			
			indexHyphen = f2.lastIndexOf("_");
			indexFirstPeriod = f2.indexOf(".", indexHyphen + 1);
			indexSecondPeriod = f2.indexOf(".", indexFirstPeriod + 1);
			indexLastPeriod = f2.indexOf(".", indexSecondPeriod + 1);
			
			int batch2 = Integer.parseInt(f2.substring(indexFirstPeriod + 1, indexSecondPeriod));
			int revision2 = Integer.parseInt(f2.substring(indexSecondPeriod + 1, indexLastPeriod));
			int minorRev2 = Integer.parseInt(f2.substring(indexLastPeriod + 1));
			
			if(batch1 != batch2) {
				val = batch1 - batch2;
			}
			else {
				if(revision1 != revision2) {
					val = revision1 - revision2;
				}
				else {
					if(minorRev1 != minorRev2) {
						val = minorRev1 - minorRev2;
					}
					else {
						val = 0;
					}
				}
			}
		}
		
		return val;
	}
}
