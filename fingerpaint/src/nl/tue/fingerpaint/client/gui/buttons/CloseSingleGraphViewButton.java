package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button that can be used to close the performance pop-up of a single graph.
 * 
 * @author Group Fingerpaint
 */
public class CloseSingleGraphViewButton extends FastButton implements PressHandler {

	/**
	 * Construct a new button that can be used to close the performance pop-up
	 * of a single graph.
	 */
	public CloseSingleGraphViewButton() {
		super(FingerpaintConstants.INSTANCE.btnClose());
		addPressHandler(this);
		ensureDebugId("closeSingleGraphViewButton");
	}

	/**
	 * Hides the panel.
	 * @param event The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		GuiState.viewSingleGraphPopupPanel.hide();
	}

}
