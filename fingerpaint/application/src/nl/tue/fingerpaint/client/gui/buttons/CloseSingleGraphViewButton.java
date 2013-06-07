package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to close the performance pop-up of a single graph.
 * 
 * @author Group Fingerpaint
 */
public class CloseSingleGraphViewButton extends Button implements ClickHandler {

	/**
	 * Construct a new button that can be used to close the performance pop-up
	 * of a single graph.
	 */
	public CloseSingleGraphViewButton() {
		super(FingerpaintConstants.INSTANCE.btnClose());
		addClickHandler(this);
		ensureDebugId("closeSingleGraphViewButton");
	}

	@Override
	public void onClick(ClickEvent event) {
		GuiState.viewSingleGraphPopupPanel.hide();
	}

}
