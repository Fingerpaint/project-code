package nl.tue.fingerpaint.client.gui.panels;

import com.google.gwt.user.client.ui.PopupPanel;

/**
 * PopupPanel that can be used to let a user input a name for an item to save.
 * An item can be a distribution, protocol, ...
 * 
 * @author Group Fingerpaint
 */
public class SaveItemPopupPanel extends PopupPanel {

	/**
	 * Construct a new {@link SaveItemPopupPanel} that can be used to let a user save an item.
	 * 
	 * @see SaveItemPopupPanel
	 */
	public SaveItemPopupPanel() {
		super();

		setModal(true);
		ensureDebugId("saveItemPanel");
	}

}
