package nl.tue.fingerpaint.client.gui.labels;

import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.user.client.ui.Label;

/**
 * Header for the drawing tool submenu.
 * 
 * @author Group Fingerpaint
 */
public class DrawingToolLabel extends Label {

	/**
	 * Construct the {@link DrawingToolLabel}.
	 */
	public DrawingToolLabel() {
		super(FingerpaintConstants.INSTANCE.lblTool());
		ensureDebugId("drawingToolLabel");
		getElement().setClassName(FingerpaintConstants.INSTANCE.classMenuTitleLabel());
	}
	
}
