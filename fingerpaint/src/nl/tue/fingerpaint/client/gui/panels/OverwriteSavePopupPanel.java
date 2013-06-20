package nl.tue.fingerpaint.client.gui.panels;

import nl.tue.fingerpaint.client.gui.buttons.OverwriteSaveButton;

import com.google.gwt.user.client.ui.PopupPanel;

/**
 * PopupPanel that can be used to let a user indicate he/she wants to overwrite
 * an already existing item.
 * 
 * @author Group Fingerpaint
 * @see OverwriteSaveButton
 */
public class OverwriteSavePopupPanel extends PopupPanel {

	/**
	 * Construct a new {@link OverwriteSavePopupPanel} that can be used to let a user
	 * overwrite an already saved item.
	 * 
	 * @see OverwriteSavePopupPanel
	 */
	public OverwriteSavePopupPanel() {
		super();
		setModal(true);
		setGlassEnabled(true);
	}

}
