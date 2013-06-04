package nl.tue.fingerpaint.client.model;

import nl.tue.fingerpaint.client.model.ApplicationState;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the class {@link ApplicationState}.
 * 
 * @author Group Fingerpaint
 */
public class ApplicationStateTest extends GWTTestCase {
	/** The application state to be tested in this test case. */
	private ApplicationState as;

	/**
	 * A test to check whether the geometry is correctly set, using the
	 * setGeometry method.
	 */
	@Test
	public void testSetGeometry() {
		init();
		String testValue = "Rectangle";
		as.setGeometryChoice(testValue);
		assertEquals(as.getGeometryChoice(), testValue); 
	}

	/**
	 * A test to check whether the mixer is correctly set, using the
	 * setMixerChoice method.
	 */
	@Test
	public void testSetMixer() {
		init();
		String testValue = "Just some mixer";
		as.setMixerChoice(testValue);
		assertEquals(as.getMixerChoice(), testValue);
	}

	// ----Tests for nrSteps parameter----------
	/**
	 * A test to check whether the initialisation of nrSteps occurs correctly;
	 * after initialisation, the value for nrSteps should be 0.
	 */
	@Test
	public void testNrStepsInit() {
		init();

		assertEquals(
				"After initialisation from the ApplicationState class, the value of nrSteps should be 0.",
				0, as.getNrSteps());
	}

	/**
	 * A test to check whether the {@code getNrSteps} and {@code setNrSteps}
	 * functions work correctly.
	 */
	@Test
	public void testNrStepsSet() {
		init();

		int value = 5;
		as.setNrSteps(value);
		assertEquals("After setNrSteps, nrSteps should be " + value + ".",
				value, as.getNrSteps());
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

	// --- PRIVATE PART --------------------------------------------------
	/**
	 * Set up method for initialising the application state.
	 */
	private void init() {
		as = new ApplicationState();
	}

}
