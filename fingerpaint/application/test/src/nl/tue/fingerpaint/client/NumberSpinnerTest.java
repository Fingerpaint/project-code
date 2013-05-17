package nl.tue.fingerpaint.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * A test class to test the NumberSpinner.
 * 
 * @author Group Fingerpaint
 */
public class NumberSpinnerTest extends GWTTestCase {

	// Settings for the NumberSpinner (and testing).
	private double defaultValue = 1;
	private double RATE = 1;
	private double MIN = 1;
	private double MAX = 50;
	
	// The NumberSpinner to be tested in this test class.
	private NumberSpinner numberSpinner;
	
	// The fired variable is used in the testListener test case.
	private boolean fired;
	
	// ----Tests for exceptions that might be thrown in the constructor------------------
	/*
	 * A test for the case that the default value is lower than the specified minimum value.
	 */
	@Test
	public void testDefaultBelowMin(){
		try{
			numberSpinner = new NumberSpinner(MIN-1, RATE, MIN, MAX, true);
		}
		catch(IllegalArgumentException e){
			assertTrue("IllegalArgumentException expected, because default is lower than minimum.", 
					true);
		}
	}
	
	/*
	 * A test for the case that the default value is bigger than the specified maximum value.
	 */
	@Test
	public void testDefaultAboveMax(){
		try{
			numberSpinner = new NumberSpinner(MAX+1, RATE, MIN, MAX, true);
		}
		catch(IllegalArgumentException e){
			assertTrue("IllegalArgumentException expected, because default is bigger than maximum.", 
					true);
		}
	}
	
	/*
	 * A test for the case that the minimum value is greater than the specified maximum.
	 */
	@Test
	public void testMinBiggerMax(){
		try{
			numberSpinner = new NumberSpinner(defaultValue, RATE, MAX+1, MAX, true);
		}
		catch(IllegalArgumentException e){
			assertTrue("IllegalArgumentException expected, because minimum is bigger than maximum.", 
					true);
		}
	}
	
	// ---- Tests for rounding and listeners ------------------------------------------
	/*
	 * Initialise the NumberSpinner with the values as described in the variables above.
	 */
	private void init() {
		numberSpinner = new NumberSpinner(defaultValue, RATE, MIN, MAX, true);
	}

	/*
	 * A test to check that the default value is correctly set.
	 */
	@Test
	public void testInitialisation() {
		init();

		assertEquals("Incorrect initialisation, the value should be " + defaultValue + ".", defaultValue, numberSpinner.getValue());
	}

	/*
	 * A test to check that setting a value below the minimum should
	 * set the value to the minimum.
	 */
	@Test
	public void testUnderMin() {
		init();

		numberSpinner.setValue(MIN - 1, true);
		assertEquals("The value was not rounded off to the minimum value.", MIN, numberSpinner.getValue());
	}

	/*
	 * A test to check that setting a value above the maximum should
	 * set the value to the maximum.
	 */
	@Test
	public void testOverMax() {
		init();

		numberSpinner.setValue(MAX + 1, true);
		assertEquals("The value was not rounded off to the maximum value.", MAX, numberSpinner.getValue());
	}

	/*
	 * A test to check that values in the spinner are correctly rounded off.
	 */
	@Test
	public void testRound() {
		init();

		// Rounding down.
		numberSpinner.setValue(5.4, true);
		assertEquals("The value was not rounded down.", 5.0, numberSpinner.getValue());
		
		// Rounding up.
		numberSpinner.setValue(5.5, true);
		assertEquals("The value was not rounded up.", 6.0, numberSpinner.getValue());
	}
	
	/*
	 * A test to check that values in the spinner can also not be rounded.
	 */
	@Test
	public void testNotRound() {
		init();

		numberSpinner.setValue(5.4);
		assertEquals("The value should not have been rounded.", 5.4, numberSpinner.getValue());
	}
	
	/*
	 * A test to check whether a numberspinnerlistener can be attached to the
	 * numberspinner and fires correctly.
	 */
	@Test
	public void testListener(){
		init();
		
		fired = false;
		numberSpinner.setSpinnerListener(new NumberSpinnerListener() {
			
			@Override
			public void onValueChange(double value) {
				assertEquals("The actual value is not equal to 42.0.", 42.0, value);
				setFired(true);
			}
		});
		numberSpinner.setValue(42.0);
		assertTrue("The listener was not fired.", fired);
	}
	
	/*
	 * Private method to support the test in testListener.
	 */
	private void setFired(boolean fired){
		this.fired = fired;
	}
	
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
