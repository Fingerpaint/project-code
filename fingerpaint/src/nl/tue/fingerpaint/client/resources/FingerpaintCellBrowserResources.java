package nl.tue.fingerpaint.client.resources;

import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.cellview.client.CellBrowser.Style;

/**
 * The {@code FingerpaintCellBrowserResources} can be used when constructing
 * {@link CellBrowser CellBrowsers} to obtain the custom Fingerpaint style.
 * 
 * @author Group Fingerpaint
 */
public interface FingerpaintCellBrowserResources extends CellBrowser.Resources {
	
	/**
	 * @return Resource that provides the custom Fingerpaint styling for
	 *         {@link CellBrowser CellBrowsers}.
	 */	
	@Source({"defines.css", "fingerpaintCellBrowsers.css"})
	Style cellBrowserStyle();
}
