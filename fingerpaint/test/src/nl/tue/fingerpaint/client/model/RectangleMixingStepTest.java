package nl.tue.fingerpaint.client.model;

import nl.tue.fingerpaint.shared.model.RectangleMixingStep;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the class {@link MixingStep}.
 * 
 * @author Group Fingerpaint
 */
public class RectangleMixingStepTest extends GWTTestCase {
	/** The mixing step to be tested in this class. */
	RectangleMixingStep stepClass;

	/**
	 * A test to check whether the getter returns the step size it was
	 * initialised with.
	 */
	@Test
	public void testGetStepSize() {
		init();
		assertEquals(1.0, stepClass.getStepSize()); // initialised on 1.0,
													// convenient
	}

	/**
	 * A test to check whether the method setStepSize sets the step size
	 * correctly.
	 */
	@Test
	public void testSetStepSize() {
		init();
		stepClass.setStepSize(42.0);
		assertEquals(42.0, stepClass.getStepSize());
	}

	/**
	 * A test whether the implemented rounding function rounds up and down
	 * correctly to produce a step size that is a multiple of
	 * {@code MixingStep.STEP_UNIT}.
	 */
	@Test
	public void testRounding() {
		init();
		// for rounding down
		stepClass.setStepSize(1.1);
		assertEquals(1.0, stepClass.getStepSize());
		// for rounding up
		stepClass.setStepSize(0.9);
		assertEquals(1.0, stepClass.getStepSize());
	}
	
	/**
	 * Returns the module name for the GWT test.
	 */
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

	// --- PRIVATE PART---------------------------------------------------
	/**
	 * Setup method for the mixing step in this test class.
	 */
	private void init() {
		stepClass = new RectangleMixingStep(1, true, true);
	}

}
