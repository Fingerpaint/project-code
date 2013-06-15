package nl.tue.fingerpaint.client.gui.labels;

import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.client.ui.Label;

/**
 * Header for the protocols submenu.
 * 
 * @author Group Fingerpaint
 */
public class ProtocolsLabel extends Label {

	/**
	 * Construct the {@link ProtocolsLabel}.
	 */
	public ProtocolsLabel() {
		super(FingerpaintConstants.INSTANCE.lblProtocols());
		ensureDebugId("protocolsLabel");
		getElement().setClassName(FingerpaintConstants.INSTANCE.classMenuTitleLabel());
	}
	
}
