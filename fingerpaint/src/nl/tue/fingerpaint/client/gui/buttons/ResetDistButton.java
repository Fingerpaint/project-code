package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;
import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;

/**
 * Button to reset the current distribution to completely white.
 * 
 * @author Group Fingerpaint
 */
public class ResetDistButton extends FastButton implements PressHandler {

	/** Reference to the model. Used to reset the current distribution. */
	protected ApplicationState as;

	/**
	 * Construct a new button that can be used to reset the current 
	 * concentration distribution to a completely white distribution.
	 * 
	 * @param appState
	 *            Reference to the model, used to reset the current
	 *            distribution to completely white.
	 */
	public ResetDistButton(ApplicationState appState) {
		super(FingerpaintConstants.INSTANCE.btnResetDist());
		this.as = appState;
		addPressHandler(this);
		ensureDebugId("resetDistButton");
	}

	/**
	 * Resets the distribution to a completely white distribution.
	 * @param event The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		as.getGeometry().resetDistribution();
		GuiState.viewSingleGraphButton.setEnabled(false);
	}
}
