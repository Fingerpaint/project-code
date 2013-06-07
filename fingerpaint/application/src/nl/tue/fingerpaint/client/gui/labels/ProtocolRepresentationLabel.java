package nl.tue.fingerpaint.client.gui.labels;

import com.google.gwt.user.client.ui.Label;

/**
 * Label in which the protocol is displayed.
 * 
 * @author Group Fingerpaint
 */
public class ProtocolRepresentationLabel extends Label {

	/**
	 * Construct the {@link ProtocolRepresentationLabel}.
	 * 
	 * Initially, this label is not visible and gets a special ID.
	 */
	public ProtocolRepresentationLabel() {
		setVisible(false);
		getElement().setId("protLabel");
	}
	
}
