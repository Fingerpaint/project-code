package nl.tue.fingerpaint.client.storage;

import nl.tue.fingerpaint.shared.model.MixingProtocol;
import nl.tue.fingerpaint.shared.model.RectangleMixingStep;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the class {@link ResultStorage}.
 * 
 * @author Group Fingerpaint
 */
public class ResultStorageTest extends GWTTestCase {

	private ResultStorage storage;
	private final int[] dist = new int[96000];
	private final String geometry = "Rectangle";
	private final String mixer = "Default";
	private final int nrSteps = 10;
	private final double[] segregation = new double[10];
	private MixingProtocol protocol;

	/**
	 * A test to check whether the {@code getDistribution} function
	 * works correctly.
	 */
	@Test
	public void testGetDistribution() {
		init();
		final int[] result = storage.getDistribution();
		for (int i = 0; i < result.length; i++) {
			assertEquals("The distribution value at index " + i, dist[i],
					result[i]);
		}
	}

	/**
	 * A test to check whether the {@code getGeometry} function
	 * works correctly.
	 */
	@Test
	public void testGetGeometry() {
		init();
		final String result = storage.getGeometry();
		assertEquals("The geometry", geometry, result);
	}

	/**
	 * A test to check whether the {@code getMixer} function
	 * works correctly.
	 */
	@Test
	public void testGetMixer() {
		init();
		final String result = storage.getMixer();
		assertEquals("The mixer", mixer, result);
	}

	/**
	 * A test to check whether the {@code getNrSteps} function
	 * works correctly.
	 */
	@Test
	public void testGetNrSteps() {
		init();
		final int result = storage.getNrSteps();
		assertEquals("The nr steps", nrSteps, result);
	}

	/**
	 * A test to check whether the {@code getSegregation} function
	 * works correctly.
	 */
	@Test
	public void testGetSegregation() {
		init();
		final double[] result = storage.getSegregation();
		for (int i = 0; i < result.length; i++) {
			assertEquals("The segregation value at index " + i, segregation[i],
					result[i]);
		}
	}

	/**
	 * A test to check whether the {@code getMixingProtocol} function
	 * works correctly.
	 */
	@Test
	public void testGetProtocol() {
		init();
		final MixingProtocol result = storage.getMixingProtocol();
		assertEquals("The mixing protocol", protocol.toString(),
				result.toString());
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
		storage = new ResultStorage();
		storage.setDistribution(dist);
		storage.setGeometry(geometry);
		storage.setMixer(mixer);
		storage.setNrSteps(nrSteps);
		storage.setSegregation(segregation);

		protocol = new MixingProtocol();
		RectangleMixingStep step0 = new RectangleMixingStep(0.5, true, false);
		RectangleMixingStep step1 = new RectangleMixingStep(1.75, true, true);
		RectangleMixingStep step2 = new RectangleMixingStep(1.0, false, false);
		protocol.addStep(step0);
		protocol.addStep(step1);
		protocol.addStep(step2);
		
		storage.setMixingProtocol(protocol);

	}

}
