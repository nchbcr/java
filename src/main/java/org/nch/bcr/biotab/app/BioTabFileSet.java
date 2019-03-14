package org.nch.bcr.biotab.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A set of all <code>BioTabFileDTO</code>. That is the same as to say, this is the set of 
 * all <code>Table</code> and <code>Header</code> objects for agiven study. 
 * 
 * @author John Deardurff
 *
 */
public class BioTabFileSet<T> implements  Iterable<BioTabFileDTO> {
	
	protected List<BioTabFileDTO> BioTabFileDTOList = new ArrayList<BioTabFileDTO>(10);
	
	public BioTabFileSet() {}
	
	@Override
    public Iterator<BioTabFileDTO> iterator() {
        Iterator<BioTabFileDTO> it = new Iterator<BioTabFileDTO>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < BioTabFileDTOList.size() && BioTabFileDTOList.get(currentIndex) != null;
            }

            @Override
            public BioTabFileDTO next() {
            	currentIndex++;
                return BioTabFileDTOList.get(currentIndex-1);
            }

            @Override
            public void remove() {
            	// Do nothing
            }
        };
        return it;
    }
	
	public void addBioTabFileDTO(BioTabFileDTO bioTabFileDTO) {
		BioTabFileDTOList.add(bioTabFileDTO);
	}

}
