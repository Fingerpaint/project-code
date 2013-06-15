package nl.tue.fingerpaint.client.gui.labels;

import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.client.ui.Label;

/**
 * Header for the distributions submenu.
 * 
 * @author Group Fingerpaint
 */
public class DistributionsLabel extends Label {

	/**
	 * Construct the {@link DistributionsLabel}.
	 */
	public DistributionsLabel() {
		super(FingerpaintConstants.INSTANCE.lblDistributions());
		ensureDebugId("distributionsLabel");
		getElement().setClassName(FingerpaintConstants.INSTANCE.classMenuTitleLabel());
	}
	
}
