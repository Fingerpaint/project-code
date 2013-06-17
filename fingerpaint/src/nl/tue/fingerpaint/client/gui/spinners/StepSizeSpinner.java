package nl.tue.fingerpaint.client.gui.spinners;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.shared.model.MixingStep;

/**
 * The {@link NumberSpinner} that is used to specify the size of a step.
 * 
 * @author Group Fingerpaint
 */
public class StepSizeSpinner extends NumberSpinner implements
		NumberSpinnerListener {

	/** Reference to model. */
	private ApplicationState as;

	/**
	 * Construct the {@link StepSizeSpinner}.
	 * 
	 * @param appState
	 *            A reference to the model of the application. This is needed to
	 *            update the step size there.
	 */
	public StepSizeSpinner(ApplicationState appState) {
		this(appState, "sizeSpinner");
	}
	
	/**
	 * Construct the {@link StepSizeSpinner}.
	 * 
	 * @param appState
	 *            A reference to the model of the application. This is needed to
	 *            update the step size there.
	 * @param debugId
	 *            The wanted debug ID for the element.
	 */
	public StepSizeSpinner(ApplicationState appState, String debugId) {
		super(MixingStep.STEP_DEFAULT, MixingStep.STEP_UNIT,
				MixingStep.STEP_MIN, MixingStep.STEP_MAX, true);
		this.as = appState;
		addStyleName("sizeSpinnerInput");
		as.setStepSize(MixingStep.STEP_DEFAULT);
		setSpinnerListener(this);
		ensureDebugId(debugId);
	}

	/**
	 * Update the step size in the {@link ApplicationState}.
	 * 
	 * Also makes sure that both {@link StepSizeSpinner StepSizeSpinners} in
	 * the application get the same value.
	 */
	@Override
	public void onValueChange(double value, double roundedValue) {
		as.setStepSize(roundedValue);
		
		// Because there are two spinners of this type that need to show the
		// same value, update the other spinner as well.
		// Do not round however, to prevent the spinner from correcting the
		// user while he/she is still typing...
		if (GuiState.sizeSpinner.getValue() != value) {
			GuiState.sizeSpinner.setValue(value, false);
		}
		
		if (GuiState.sizeProtocolMenuSpinner.getValue() != value) {
			GuiState.sizeProtocolMenuSpinner.setValue(value, false);
		}
	}

}
