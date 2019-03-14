/**
 * 
 */
package org.nch.bcr.biotab.util;

import java.util.Comparator;

import org.nch.bcr.biotab.domain.table.Row;
/**
 * @author anasu
 * @param <T>
 *
 */
@SuppressWarnings("rawtypes")
public class BarcodeComparator implements Comparator<Row> {

	@Override
	public int compare(Row r1, Row r2) {
		return r1.getTcgaBarcode().compareTo(r2.getTcgaBarcode());
	}
}
