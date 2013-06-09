package nl.tue.fingerpaint.client.gui.panels;

import com.google.gwt.user.client.ui.PopupPanel;

/**
 * PopupPanel that can be used to view a graph with the performance of a result.
 * 
 * @author Group Fingerpaint
 */
public class ViewSingleGraphPopupPanel extends PopupPanel {

	/**
	 * Construct a new {@link ViewSingleGraphPopupPanel} that can be used to let a
	 * user view a graph with the performance of a result.
	 * 
	 * @see ViewSingleGraphPopupPanel
	 */
	public ViewSingleGraphPopupPanel() {
		super();
		setModal(true);
		ensureDebugId("viewSingleGraphPopupPanel");
	}

}
