package nl.tue.fingerpaint.shared.simulator;

import nl.tue.fingerpaint.shared.GeometryNames;
import nl.tue.fingerpaint.shared.model.MixingProtocol;
import nl.tue.fingerpaint.shared.simulator.Simulation;
import nl.tue.fingerpaint.client.storage.FingerpaintJsonizer;
import nl.tue.fingerpaint.client.storage.FingerpaintZipper;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the class {@link Simulation}.
 * 
 * @author Group Fingerpaint
 */
public class SimulationTest extends GWTTestCase {

	private final String geom = GeometryNames.RECT;
	private final String mixer = "mixer";
	private final String prot = "&nbsp;T[0.25] &nbsp;B[6.25] -B[8] -T[5.5] ";
	private final int[] dist = new int[96000];
	private final int runs = 10;
	private boolean vectors = false;

	private MixingProtocol protocol;
	private String distribution;
	private Simulation sim;

	/**
	 * Test the constructor of the Simulation class
	 */
	@Test
	public void testConstructor() {
		init();
		
		try {
			sim = new Simulation(geom, null, protocol, distribution, runs, vectors);
			fail("An exception should have been thrown");
		} catch (NullPointerException e) {
			assertTrue("NullpointerException was thrown, as expected", true);
		} catch (Exception e) {
			fail(e.toString()
					+ "was thrown, while a NullPointerException was expected.");
		}

		try {
			sim = new Simulation(geom, mixer, null, distribution, runs, vectors);
			fail("An exception should have been thrown");
		} catch (NullPointerException e) {
			assertTrue("NullpointerException was thrown, as expected", true);
		} catch (Exception e) {
			fail(e.toString()
					+ "was thrown, while a NullPointerException was expected.");
		}

		try {
			sim = new Simulation(geom, mixer, protocol, null, runs, vectors);
			fail("An exception should have been thrown");
		} catch (NullPointerException e) {
			assertTrue("NullpointerException was thrown, as expected", true);
		} catch (Exception e) {
			fail(e.toString()
					+ "was thrown, while a NullPointerException was expected.");
		}
		
		try {
			sim = new Simulation(GeometryNames.SQR, mixer, protocol, distribution, runs, vectors);
			fail("An exception should have been thrown");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			fail(e.toString()
					+ " was thrown, while an UnsupportedOperationException was expected");
		}
	}

	/**
	 * A test to check whether the {@code getMixer} function works correctly.
	 */
	@Test
	public void testGetMixer() {
		init();
		final String result = sim.getMixer();
		assertEquals("The mixer of the simulation", mixer, result);
	}

	/**
	 * A test to check whether the {@code getProtocol} function works correctly.
	 */
	@Test
	public void testGetProtocol() {
		init();
		final String result = sim.getProtocol().toString();
		assertEquals("String representation of the protocol", prot, result);
	}

	/**
	 * A test to check whether the {@code getConcentrationVector} function works correctly.
	 */
	@Test
	public void testGetConcentrationVector() {
		init();
		final int[] result = FingerpaintJsonizer
				.intArrayFromString(FingerpaintZipper.unzip(sim
						.getConcentrationVector()));
		for (int i = 0; i < 96000; i++) {
		assertEquals("The distribution of the simulation at index " +i, dist[i],
				result[i]);
		}
	}

	/**
	 * A test to check whether the {@code getProtocolRuns} function works correctly.
	 */
	@Test
	public void testGetRuns() {
		init();
		final int result = sim.getProtocolRuns();
		assertEquals("The number of runs", runs, result);
	}

	/**
	 * A test to check whether the {@code calculatesIntermediateVectors} function works correctly.
	 */
	@Test
	public void testIntermediateVectors() {
		init();
		final boolean result = sim.calculatesIntermediateVectors();
		assertEquals("Calculate intermediate vectors", vectors, result);
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
		protocol = MixingProtocol.fromString(prot);
		distribution = FingerpaintZipper.zip(FingerpaintJsonizer
				.toString(dist));
		sim = new Simulation(geom, mixer, protocol, distribution, runs, vectors);
	}

}
