package nl.tue.fingerpaint.client.gui.panels;

import com.google.gwt.user.client.ui.PopupPanel;

/**
 * PopupPanel that can be used to let a user remove saved results from the local
 * storage.
 * 
 * @author Group Fingerpaint
 */
public class RemoveResultsPopupPanel extends PopupPanel {

	/**
	 * Construct a new {@link RemoveResultsPopupPanel} that can be used to let a
	 * user remove saved results.
	 * 
	 * @see RemoveResultsPopupPanel
	 */
	public RemoveResultsPopupPanel() {
		super();
		setModal(true);
		setGlassEnabled(true);
		ensureDebugId("removeResultsPanel");
	}

}
