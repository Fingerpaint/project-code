package nl.tue.fingerpaint.client.simulator;

import nl.tue.fingerpaint.shared.simulator.SimulationResult;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the class {@link SimulationResult}.
 * 
 * @author Group Fingerpaint
 */
public class SimulationResultTest extends GWTTestCase {

	private SimulationResult simRes;
	private int[][] vectors;
	private double[] segregation;

	/**
	 * Test the constructor of the Simulation class
	 */
	@Test
	public void testConstructor() {
		init();

		try {
			simRes = new SimulationResult(null, segregation);
			fail("An exceptionj should have been thrown");
		} catch (NullPointerException e) {
			assertTrue("NullpointerException was thrown, as expected", true);
		} catch (Exception e) {
			fail(e.toString()
					+ "was thrown, while a NullPointerException was expected.");
		}

		try {
			simRes = new SimulationResult(vectors, null);
			fail("An exceptionj should have been thrown");
		} catch (NullPointerException e) {
			assertTrue("NullpointerException was thrown, as expected", true);
		} catch (Exception e) {
			fail(e.toString()
					+ "was thrown, while a NullPointerException was expected.");
		}
	}

	/**
	 * A test to check whether the {@code getConcentrationVectors} function
	 * works correctly.
	 */
	@Test
	public void testGetVectors() {
		init();
		final int[][] result = simRes.getConcentrationVectors();
		for (int i = 0; i < result.length; i++) {
			int[] vector = result[i];
			for (int j = 0; j < vector.length; j++) {
				assertEquals("The value of vector " + i + " at index " + j,
						vectors[i][j], vector[j]);
			}
		}
	}

	/**
	 * A test to check whether the {@code getSegregationPoints} function
	 * works correctly.
	 */
	@Test
	public void testGetSegregation() {
		init();
		final double[] result = simRes.getSegregationPoints();
		for (int i = 0; i < result.length; i++) {
			assertEquals("The value of the segregation at index " + i,
					segregation[i], result[i]);
		}
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

	// --- PRIVATE PART --------------------------------------------------
	/**
	 * Set up method for initialising the Simulation Object.
	 */
	private void init() {
		vectors = new int[][] { new int[96000], new int[96000], new int[96000] };
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 96000; j++) {
				vectors[i][j] = i * 120;
			}
		}
		segregation = new double[] { 0.7, 0.5, 0.3 };
		simRes = new SimulationResult(vectors, segregation);
	}

}
