package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that can be used to close the remove results pop-up panel.
 * 
 * @author Group Fingerpaint
 */
public class CloseResultsButton extends FastButton implements PressHandler {
	/**
	 * Construct a new button that can be used to close the remove results
	 * pop-up panel.
	 */
	public CloseResultsButton() {
		super(FingerpaintConstants.INSTANCE.btnClose());
		addPressHandler(this);
		ensureDebugId("closeResultsButton");
	}

	/**
	 * Removes the loading panel from the screen
	 * @param event The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		GuiState.removeResultsPanel.hide();
	}

}
