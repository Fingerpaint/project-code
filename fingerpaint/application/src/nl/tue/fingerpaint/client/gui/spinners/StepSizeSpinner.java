package nl.tue.fingerpaint.client.gui.spinners;

import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.model.MixingStep;

/**
 * The {@link NumberSpinner} that is used to specify the size of a step.
 * 
 * @author Group Fingerpaint
 */
public class StepSizeSpinner extends NumberSpinner {

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
		super(MixingStep.STEP_DEFAULT, MixingStep.STEP_UNIT,
				MixingStep.STEP_MIN, MixingStep.STEP_MAX, true);

		this.as = appState;
		
		addStyleName("sizeSpinnerInput");
		appState.setStepSize(MixingStep.STEP_DEFAULT);
		
		setSpinnerListener(new NumberSpinnerListener() {
			@Override
			public void onValueChange(double value) {
				// change the current mixing step
				as.setStepSize(value);
			}
		});
		
		ensureDebugId("sizeSpinner");
	}

}
