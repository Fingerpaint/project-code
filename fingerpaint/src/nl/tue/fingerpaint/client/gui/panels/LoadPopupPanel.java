package nl.tue.fingerpaint.client.gui.panels;

import nl.tue.fingerpaint.client.gui.GuiState;

import com.google.gwt.user.client.ui.PopupPanel;

/**
 * PopupPanel that can be used to show all available "things" in the local
 * storage, that can be loaded in the application. These "things" can be
 * protocols, distributions, ...
 * 
 * @author Group Fingerpaint
 */
public class LoadPopupPanel extends PopupPanel {

	/**
	 * <p>
	 * Construct a new {@link LoadPopupPanel} that can be used to show all available "things"
	 * in the local storage.
	 * </p>
	 * 
	 * <p>
	 * <b style="color: red;">Note:</b> the {@link GuiState#loadVerticalPanel} needs to be
	 * initialised when constructing this panel.
	 * </p>
	 * 
	 * @see LoadPopupPanel
	 */
	public LoadPopupPanel() {
		super();
		setModal(true);
		add(GuiState.loadVerticalPanel);
		ensureDebugId("loadPanel");
	}

}
