package nl.tue.fingerpaint.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;

/**
 * NumberSpinner Custom Control
 * 
 * Code of this class was obtained from:
 * http://pavanandhukuri.wordpress.com/2012/01/28/gwt-number-spinner-control/
 * 
 * @author Pavan Andhukuri
 * @author Group Fingerpaint
 */
public class NumberSpinner extends Composite {

	private DoubleBox numberBox;
	private double RATE;
	private double MAX;
	private double MIN;
	private boolean hasLimits;
	private NumberSpinnerListener spinnerListener;

	// ----Constructors--------------------------------------------

	/**
	 * Initialises a number spinner with default value 1, increase-rate 1 and
	 * without limits
	 */
	public NumberSpinner() {
		this(1, 1, 0, 0, false);
	}

	/**
	 * Initialises a number spinner with default value {@code defaultValue},
	 * increase-rate 1 and without limits
	 * 
	 * @param defaultValue
	 *            The value that is initially displayed on this number spinner
	 */
	public NumberSpinner(double defaultValue) {
		this(defaultValue, 1, 0, 0, false);
	}

	/**
	 * Initialises a number spinner with default value {@code min},
	 * increase-rate 1 and limits {@code min} and {@code max}
	 * 
	 * @param min
	 *            The minimum value this spinner can take
	 * @param max
	 *            The maximum value this spinner can take
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code min} > {@code max}
	 */
	public NumberSpinner(double min, double max)
			throws IllegalArgumentException {
		this(min, 1, min, max, true);
	}

	/**
	 * Initialises a number spinner with default value {@code defaultValue},
	 * increase-rate {@code rate} and limits {@code min} and {@code max}
	 * 
	 * @param defaultValue
	 *            The default value of this spinner
	 * @param rate
	 *            The amount with which the value if the spinner is
	 *            increased/decreased when using the up/down buttons
	 * @param min
	 *            The minimum value this spinner can take
	 * @param max
	 *            The maximum value this spinner can take
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code defaultvalue} < {@code min} || {@code defaultValue}
	 *             > {@code max} || {@code min} > {@code max}
	 * 
	 */
	public NumberSpinner(double defaultValue, double rate, double min,
			double max) throws IllegalArgumentException {
		this(defaultValue, rate, min, max, true);
	}

	/**
	 * Initialises a number spinner with default value {@code defaultValue},
	 * increase-rate {@code rate} and limits {@code min} and {@code max} if
	 * {@code limits} == {@code true}
	 * 
	 * @param defaultValue
	 *            The default value of this spinner
	 * @param rate
	 *            The amount with which the value if the spinner is
	 *            increased/decreased when using the up/down buttons
	 * @param min
	 *            The minimum value this spinner can take
	 * @param max
	 *            The maximum value this spinner can take
	 * @param limits
	 *            {@code true} if this NumberSpinner has limits, {@code false}
	 *            otherwise
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code limits} == {@code true} && ( {@code defaultvalue} <
	 *             {@code min} || {@code defaultvalue} > {@code max} ||
	 *             {@code min} > {@code max})
	 * 
	 */
	public NumberSpinner(double defaultValue, double rate, double min,
			double max, boolean limits) throws IllegalArgumentException {
		if (limits) {
			if (defaultValue < min) {
				throw new IllegalArgumentException(
						"The default value is smaller than the minimum value");
			}
			if (defaultValue > max) {
				throw new IllegalArgumentException(
						"The default value is larger than the maximum value");
			}
			if (min > max) {
				throw new IllegalArgumentException(
						"The minimum value must be smaller than the maximum value");
			}
			MAX = max;
			MIN = min;
		}
		this.RATE = rate;
		this.hasLimits = limits;

		AbsolutePanel absolutePanel = new AbsolutePanel();
		initWidget(absolutePanel);
		absolutePanel.setSize("55px", "23px");

		numberBox = new DoubleBox();
		absolutePanel.add(numberBox, 0, 0);
		numberBox.setSize("30px", "16px");
		numberBox.setValue(defaultValue);
		numberBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				roundValue();
			}

		});

		Button upButton = new Button();
		upButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!hasLimits || getValue() <= MAX - RATE) {
					setValue(getValue() + RATE);
				}
			}
		});
		upButton.setStyleName("dp-spinner-upbutton");

		absolutePanel.add(upButton, 34, 1);
		upButton.setSize("12px", "10px");

		Button downButton = new Button();
		downButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!hasLimits || getValue() >= MIN + RATE) {
					setValue(getValue() - RATE);
				}
			}
		});

		downButton.setStyleName("dp-spinner-downbutton");
		absolutePanel.add(downButton, 34, 11);
		downButton.setSize("12px", "10px");
	}

	// ----Getters and Setters--------------------------------------------

	/**
	 * Returns the value being held.
	 * 
	 * @return {@code integerBox.getValue()}
	 */
	public double getValue() {
		return numberBox.getValue() == null ? 0 : numberBox.getValue();
	}

	/**
	 * Sets the value to the control
	 * 
	 * @param d
	 *            Value to be set
	 */
	public void setValue(double d) {
		setValue(d, false);
	}

	/**
	 * Sets the value to the control and possibly performs rounding of the given
	 * 
	 * @param d
	 *            .
	 * 
	 * @param d
	 *            Value to be set
	 * @param round
	 *            Boolean to indicate whether rounding should be performed.
	 */
	public void setValue(double d, boolean round) {
		numberBox.setValue(d);
		if (spinnerListener != null) {
			spinnerListener.onValueChange(d);
		}

		if (round) {
			roundValue();
		}
	}

	/**
	 * Sets the rate at which increment or decrement is done.
	 * 
	 * @param rate
	 *            Increase rate to be set
	 */
	public void setRate(double rate) {
		this.RATE = rate;
	}

	/**
	 * Change or set the listener attached to this NumberSpinner.
	 * 
	 * @param spinnerListener
	 *            The (new) listener that will be attached to this spinner.
	 */
	public void setSpinnerListener(NumberSpinnerListener spinnerListener) {
		this.spinnerListener = spinnerListener;
	}

	/**
	 * Rounds the current value of this numberspinner.
	 */
	private void roundValue() {
		if (hasLimits) {
			if (getValue() < MIN) {
				setValue(MIN, false);
			} else if (getValue() > MAX) {
				setValue(MAX, false);
			}
		}
		setValue(Math.round(getValue() / RATE) * RATE, false);
	}
}
