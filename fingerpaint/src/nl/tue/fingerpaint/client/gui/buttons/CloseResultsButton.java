package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to close the remove results pop-up panel.
 * 
 * @author Group Fingerpaint
 */
public class CloseResultsButton extends Button implements ClickHandler {
	/**
	 * Construct a new button that can be used to close the remove results
	 * pop-up panel.
	 */
	public CloseResultsButton() {
		super(FingerpaintConstants.INSTANCE.btnClose());
		addClickHandler(this);
		ensureDebugId("closeResultsButton");
	}

	/**
	 * Removes the loading panel from the screen
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		GuiState.removeResultsPanel.hide();
	}

}
