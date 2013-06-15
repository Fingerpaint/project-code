package nl.tue.fingerpaint.client.gui.spinners;

/**
 * This listener can be attached to a {@link NumberSpinner}. It is notified when
 * the value of the spinner changes and can thus be used to update some internal
 * variables or so.
 * 
 * @author Group Fingerpaint
 */
public interface NumberSpinnerListener {

	/**
	 * This function is called whenever the value of the {@link NumberSpinner}
	 * is updated.
	 * 
	 * @param value
	 *            The (new) current value of the spinner. This is the value as
	 *            it is typed in by the user.
	 * @param roundedValue
	 *            The (new) current value of the spinner, rounded to a value
	 *            that is valid for it.
	 */
	public void onValueChange(double value, double roundedValue);

}
