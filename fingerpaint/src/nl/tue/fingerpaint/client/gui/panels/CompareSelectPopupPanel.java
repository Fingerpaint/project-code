package nl.tue.fingerpaint.client.gui.panels;

import com.google.gwt.user.client.ui.PopupPanel;

/**
 * PopupPanel that can be used to let a user pick two results that need to be compared.
 * 
 * @author Group Fingerpaint
 */
public class CompareSelectPopupPanel extends PopupPanel {

	/**
	 * Construct a new CompareSelectPopupPanel that can be used to let a user
	 * pick two results that need to be compared.
	 * 
	 * @see CompareSelectPopupPanel
	 */
	public CompareSelectPopupPanel() {
		super();
		setModal(true);
		setGlassEnabled(true);
	}

}
