package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that can be used to close the save results or overwrite pop-up panel.
 * 
 * @author Group Fingerpaint
 */
public class CloseSaveButton extends FastButton implements PressHandler {
	/**
	 * Construct a new button that can be used to close the save results or
	 * overwrite pop-up panel.
	 */
	public CloseSaveButton() {
		super(FingerpaintConstants.INSTANCE.btnCancel());
		addPressHandler(this);
		ensureDebugId("closeSaveButton");
	}

	/**
	 * Closes the overwrite panel, shows the save panel and selects the
	 * previously entered name.
	 * @param event The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		GuiState.overwriteSavePanel.hide();
		GuiState.overwriteSavePanel.remove(GuiState.overwriteSaveButton);
		GuiState.saveItemPanel.show();
		GuiState.saveNameTextBox.setSelectionRange(0,
				GuiState.saveNameTextBox.getText().length());
		GuiState.saveNameTextBox.setFocus(true);
	}

}
