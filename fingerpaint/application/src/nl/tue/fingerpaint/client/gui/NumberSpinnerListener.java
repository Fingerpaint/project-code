package nl.tue.fingerpaint.client.gui;

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
	 *            The (new) current value of the spinner.
	 */
	public void onValueChange(double value);

}
