package nl.tue.fingerpaint.client.gui.panels;

import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Panel that contains all widgets that are controls connected to defining a
 * protocol. These are grouped in this container to easily hide and show them.
 * 
 * @author Group Fingerpaint
 */
public class ProtocolPanelContainer extends PopupPanel {

	/**
	 * Construct a {@link PopupPanel} that is initially hidden, has an ID set
	 * and animations enabled.
	 */
	public ProtocolPanelContainer() {
		super();

		getElement().setId("protPanel");
		setAnimationEnabled(true);
		setVisible(false);
	}

}
