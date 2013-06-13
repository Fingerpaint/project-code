package nl.tue.fingerpaint.client.gui.labels;

import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.client.ui.Label;

/**
 * Label that is used to indicate that there are no saved files found for the selected loading option.
 * 
 * @author Group Fingerpaint
 */
public class NoFilesFoundLabel extends Label {

	/**
	 * Construct the {@link SaveMessageLabel}.
	 */
	public NoFilesFoundLabel() {
		super();
		setText(FingerpaintConstants.INSTANCE.noFilesFound());
		ensureDebugId("noFilesFoundLabel");
	}
	
}
