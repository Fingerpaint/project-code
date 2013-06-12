package nl.tue.fingerpaint.client.gui.panels;

import com.google.gwt.user.client.ui.PopupPanel;

/**
 * PopupPanel that can be used to let a user pick two results that need to be compared.
 * 
 * @author Group Fingerpaint
 */
public class ComparePopupPanel extends PopupPanel {

	/**
	 * Construct a new ComparePopupPanel that can be used to let a user
	 * pick two results that need to be compared.
	 * 
	 * @see ComparePopupPanel
	 */
	public ComparePopupPanel() {
		super();
		setModal(true);
	}

}
