package nl.tue.fingerpaint.client.model;

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
	 * Tests whether all variables are correctly initialised.
	 */
	@Test
	public void testConstructor() {
		init();
		assertNull("Initial geometry choice", as.getGeometryChoice());
		assertNull("Initial mixer choice", as.getMixerChoice());
		assertNull("Initial geometry object", as.getGeometry());
		assertNull("Initial distribution", as.getInitialDistribution());
		assertEquals("Size of initial protocol", 0, as.getProtocol()
				.getProgramSize());
		assertNull("Initial segregation", as.getSegregation());
		assertEquals("Initial Step size", 0.0, as.getStepSize());
		assertEquals("Initial number of Steps", 0, as.getNrSteps());
	}

	/**
	 * A test to check whether the geometry is correctly set, using the
	 * setGeometry method.
	 */
	@Test
	public void testSetGeometry() {
		init();
		final String testValue = "Rectangle";
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
		final String testValue = "Just some mixer";
		as.setMixerChoice(testValue);
		assertEquals(as.getMixerChoice(), testValue);
	}

	/**
	 * A test to check whether the {@code getNrSteps} and {@code setNrSteps}
	 * functions work correctly.
	 */
	@Test
	public void testNrSteps() {
		init();
		final int value = 5;
		as.setNrSteps(value);
		assertEquals("After setNrSteps, nrSteps should be " + value + ".",
				value, as.getNrSteps());
	}

	/**
	 * A test to check whether the {@code getInitialDistribution} and
	 * {@code setInitialDistribution} functions work correctly.
	 */
	@Test
	public void testDistribution() {
		init();
		final int[] dist = new int[] { 0, 1, 2, 3, 4, 5, 6 };
		as.setInitialDistribution(dist);
		for (int i = 0; i < dist.length; i++) {
			assertEquals("The distribution value at index " + i, dist[i],
					as.getInitialDistribution()[i]);
		}

	}

	/**
	 * A test to check whether the {@code getProtocol} and {@code setProtocol}
	 * functions work correctly.
	 */
	@Test
	public void testProtocol() {
		init();
		final MixingProtocol prot = new MixingProtocol();
		prot.addStep(new RectangleMixingStep(0.25, false, true));
		as.setProtocol(prot);
		final MixingProtocol result = as.getProtocol();
		assertEquals("The string value of the protocol", prot.toString(), result.toString());
	}

	/**
	 * A test to check whether the {@code getSegregation} and
	 * {@code setSegregation} functions work correctly.
	 */
	@Test
	public void testSegregation() {
		init();
		final double[] segr = new double[] { 0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6 };
		as.setSegregation(segr);
		for (int i = 0; i < segr.length; i++) {
			assertEquals("The segregation value at index " + i, segr[i],
					as.getSegregation()[i]);
		}
	}

	/**
	 * A test to check whether the {@code getStepSize} and {@code setStepSize}
	 * functions work correctly.
	 */
	@Test
	public void testStepsize() {
		init();
		final double value = 6.25;
		as.setStepSize(value);
		assertEquals("The current stepsize" + value + ".", value,
				as.getStepSize());
	}

	/**
	 * Returns the module name for the GWT test.
	 */
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
