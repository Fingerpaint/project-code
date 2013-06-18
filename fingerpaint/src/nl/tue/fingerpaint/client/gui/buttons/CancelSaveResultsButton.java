package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that resides on an save item panel to cancel the current save.
 * 
 * @author Group Fingerpaint
 */
public class CancelSaveResultsButton extends FastButton implements PressHandler {

	/**
	 * Creates a new button to cancel the current saving process.
	 */
	public CancelSaveResultsButton() {
		super(FingerpaintConstants.INSTANCE.btnCancel());
		addPressHandler(this);
		addStyleName("panelButton");
		ensureDebugId("cancelSaveResultsButton");
	}

	/**
	 * Clears the name, hides the panel and disables the Save button
	 */
	@Override
	public void onPress(PressEvent event) {
		GuiState.saveItemPanel.hide();
		GuiState.saveNameTextBox.setText("");
		GuiState.saveItemPanelButton.setEnabled(false);
	}
}
