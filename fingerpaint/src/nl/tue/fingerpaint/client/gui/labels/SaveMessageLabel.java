package nl.tue.fingerpaint.client.gui.labels;

import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.client.ui.Label;

/**
 * Label that is used to indicate that a save was not successful, but it is possible to overwrite.
 * 
 * @author Group Fingerpaint
 */
public class SaveMessageLabel extends Label {

	/**
	 * Construct the {@link SaveMessageLabel}.
	 */
	public SaveMessageLabel() {
		super();
		setText(FingerpaintConstants.INSTANCE.nameInUse());
		ensureDebugId("saveMessageLabel");
	}
	
}
