package nl.tue.fingerpaint.client.gui.labels;

import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.client.ui.Label;

/**
 * Header for the results submenu.
 * 
 * @author Group Fingerpaint
 */
public class ResultsLabel extends Label {

	/**
	 * Construct the {@link ResultsLabel}.
	 */
	public ResultsLabel() {
		super(FingerpaintConstants.INSTANCE.lblResults());
		ensureDebugId("resultsLabel");
		getElement().setClassName(FingerpaintConstants.INSTANCE.classMenuTitleLabel());
	}
	
}
