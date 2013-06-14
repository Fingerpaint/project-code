package nl.tue.fingerpaint.client.resources;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Style;

/**
 * The {@code FingerpaintCellListResources} can be used when constructing
 * {@link CellList CellLists} to obtain the custom Fingerpaint style.
 * 
 * @author Group Fingerpaint
 */
public interface FingerpaintCellListResources extends CellList.Resources {
	
	/**
	 * @return Resource that provides the custom Fingerpaint styling for
	 *         {@link CellList CellLists}.
	 */	
	@Source({"defines.css", "fingerpaintCellLists.css"})
	Style cellListStyle();
}
