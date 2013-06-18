package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that can be used to close a panel in which "things" from the local
 * storage can be selected. These "things" may be protocols, distributions, ...
 * 
 * @author Group Fingerpaint
 */
public class CloseLoadButton extends FastButton implements PressHandler {

	/**
	 * Construct a new button that can be used to close a popup panel.
	 */
	public CloseLoadButton() {
		super(FingerpaintConstants.INSTANCE.btnClose());
		addPressHandler(this);
		ensureDebugId("closeLoadButton");
	}

	/**
	 * Removes the loading panel from the screen
	 * @param event The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		GuiState.loadPanel.removeFromParent();
	}

}
