package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that resides on an save item panel to cancel the current save.
 * 
 * @author Group Fingerpaint
 */
public class CancelSaveResultsButton extends Button implements ClickHandler {

	/**
	 * Creates a new button to cancel the current saving process.
	 */
	public CancelSaveResultsButton() {
		super(FingerpaintConstants.INSTANCE.btnCancel());
		addClickHandler(this);
		addStyleName("panelButton");
		ensureDebugId("cancelSaveResultsButton");
	}

	/**
	 * Clears the name, hides the panel and disables the Save button
	 */
	@Override
	public void onClick(ClickEvent event) {
		GuiState.saveItemPanel.hide();
		GuiState.saveNameTextBox.setText("");
		GuiState.saveItemPanelButton.setEnabled(false);
	}
}
