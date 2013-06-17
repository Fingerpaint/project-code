package nl.tue.fingerpaint.client.storage;

import java.util.ArrayList;
import java.util.List;

import nl.tue.fingerpaint.shared.GeometryNames;
import nl.tue.fingerpaint.shared.model.MixingProtocol;
import nl.tue.fingerpaint.shared.model.MixingStep;
import nl.tue.fingerpaint.shared.model.RectangleMixingStep;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the class {@link StorageManager}. In this test class,
 * saving and retrieving of an initial distribution, a mixing protocol and a
 * mixing result are tested.
 * 
 * @author Group Fingerpaint
 */
public class StorageManagerTest extends GWTTestCase {
	/**
	 * A mixing protocol for the test cases {@code testSaveProtocol} and
	 * {@code testSaveResult}.
	 */
	private MixingProtocol protocol;

	/** A program (list of mixing steps) for the above mixing protocol. */
	private ArrayList<MixingStep> program;

	private int[] distribution = new int[96000];

	private ResultStorage storage;

	private final String resKey = "result1";
	private final String protKey = "testprot1";
	private final String distKey = "testdist1";
	private final String geometry = GeometryNames.RECT;

	private final int startIndex = 532;
	private final int endIndex = 9428;

	/**
	 * A test to check whether the {@code putDistribution} and
	 * {@code getDistribution} functions work correctly.
	 */
	@Test
	public void testSaveInitialDistribution() {
		init();

		// Save the distribution in the local storage.
		StorageManager.INSTANCE
				.putDistribution(geometry, distKey, distribution);

		// Receive the initial distribution from the local storage and test it.
		int[] receivedDist = StorageManager.INSTANCE.getDistribution(geometry,
				distKey);

		for (int i = 0; i < receivedDist.length; i++) {
			int expectedValue = (i > (startIndex - 1) && i < endIndex) ? 255
					: 0;
			assertEquals("Value on index " + i, expectedValue, receivedDist[i]);
		}
	}

	/**
	 * A test to check whether the {@code putProtocol} and {@code getProtocol}
	 * functions work correctly.
	 */
	@Test
	public void testSaveProtocol() {
		// Create and save a protocol
		init();
		StorageManager.INSTANCE.putProtocol(geometry, protKey, protocol);

		// Receive and test the protocol
		MixingProtocol receivedProtocol = StorageManager.INSTANCE.getProtocol(
				geometry, protKey);

		for (int i = 0; i < program.size(); i++) {
			RectangleMixingStep currStep = (RectangleMixingStep) receivedProtocol
					.getStep(i);
			RectangleMixingStep checkStep = (RectangleMixingStep) program
					.get(i);
			assertEquals("Step size of step " + i, checkStep.getStepSize(),
					currStep.getStepSize(), 0.000001);
			assertEquals("Top wall of step " + i, checkStep.isTopWall(),
					currStep.isTopWall());
			assertEquals("Direction of step " + i, checkStep.isClockwise(),
					currStep.isClockwise());
		}
	}

	/**
	 * A test to check whether the {@code putResult} and {@code getResult}
	 * functions work correctly.
	 */
	@Test
	public void testSaveResult() {
		init();
		StorageManager.INSTANCE.putResult(resKey, storage);

		// Receive and test the protocol
		ResultStorage receivedResult = StorageManager.INSTANCE
				.getResult(resKey);

		int[] receivedDist = receivedResult.getDistribution();
		for (int i = 0; i < receivedDist.length; i++) {
			int expectedValue = (i > (startIndex - 1) && i < endIndex) ? 255
					: 0;
			assertEquals("Value on index " + i, expectedValue, receivedDist[i]);
		}

		assertEquals("The geometry of the result", storage.getGeometry(),
				receivedResult.getGeometry());
		assertEquals("The mixer of the result", storage.getMixer(),
				receivedResult.getMixer());
		assertEquals("Nr of steps of the result", storage.getNrSteps(),
				receivedResult.getNrSteps());

		MixingProtocol receivedProtocol = receivedResult.getMixingProtocol();
		for (int i = 0; i < program.size(); i++) {
			RectangleMixingStep currStep = (RectangleMixingStep) receivedProtocol
					.getStep(i);
			RectangleMixingStep checkStep = (RectangleMixingStep) program
					.get(i);
			assertEquals("Step size of step " + i, checkStep.getStepSize(),
					currStep.getStepSize(), 0.000001);
			assertEquals("Top wall of step " + i, checkStep.isTopWall(),
					currStep.isTopWall());
			assertEquals("Direction of step " + i, checkStep.isClockwise(),
					currStep.isClockwise());
		}

		double[] receivedSegregation = receivedResult.getSegregation();
		assertEquals("Length of the segregation",
				storage.getSegregation().length, receivedSegregation.length);
		for (int i = 0; i < storage.getSegregation().length; i++) {
			assertEquals("Segregation value at index " + i,
					storage.getSegregation()[i], receivedSegregation[i]);
		}
	}

	/**
	 * A test to check whether the {@code removeDistribution} and function works
	 * correctly.
	 */
	@Test
	public void testRemoveDistribution() {
		init();
		StorageManager.INSTANCE
				.putDistribution(geometry, distKey, distribution);
		StorageManager.INSTANCE.removeDistribution(distKey, geometry);
		final List<String> results = StorageManager.INSTANCE
				.getDistributions(geometry);
		assertFalse("Key is in storage", results.contains(distKey));
	}

	/**
	 * A test to check whether the {@code removeProtocol} and function works
	 * correctly.
	 */
	@Test
	public void testRemoveProtocol() {
		init();
		StorageManager.INSTANCE.putProtocol(geometry, protKey, protocol);
		StorageManager.INSTANCE.removeProtocol(protKey, geometry);
		final List<String> results = StorageManager.INSTANCE
				.getProtocols(geometry);
		assertFalse("Key is in storage", results.contains(protKey));
	}

	/**
	 * A test to check whether the {@code removeResult} and function works
	 * correctly.
	 */
	@Test
	public void testRemoveResult() {
		init();
		StorageManager.INSTANCE.putResult(resKey, storage);
		StorageManager.INSTANCE.removeResult(resKey);
		final List<String> results = StorageManager.INSTANCE.getResults();
		assertFalse("Key is in storage", results.contains(resKey));
	}

	/**
	 * Returns the module name for the GWT test.
	 */
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

	// --- PRIVATE PART ---------------------------------------------------
	/**
	 * Set up method for initialising all needed objects.
	 */
	private void init() {
		initProtocol();
		initResultStorage();
	}

	/**
	 * Private method to initialise a mixing protocol, used in several test
	 * cases of this test class.
	 */
	private void initProtocol() {
		protocol = new MixingProtocol();

		RectangleMixingStep step1 = new RectangleMixingStep(1.25, true, true);
		RectangleMixingStep step2 = new RectangleMixingStep(2.50, true, false);
		RectangleMixingStep step3 = new RectangleMixingStep(3.75, false, true);
		RectangleMixingStep step4 = new RectangleMixingStep(4.0, false, false);

		program = new ArrayList<MixingStep>();
		program.add(step1);
		program.add(step2);
		program.add(step3);
		program.add(step4);

		protocol.setProgram(program);
	}

	/**
	 * Private method to initialise a Result Storage Object, used in several
	 * test cases of this test class.
	 */
	private void initResultStorage() {
		storage = new ResultStorage();
		
		final String geom = GeometryNames.RECT;
		final String mix = "Default";
		final int steps = 20;
		final double[] segr = new double[] { 0.1, 0.2, 0.3, 0.7, 0.6, 0.8, 0.8,
				0.4, 0.9, 0.4, 0.8, 0.2, 0.6, 0.3, 0.7, 0.5, 0.9, 0.4, 0.8, 0.1 };

		for (int i = startIndex; i < endIndex; i++) {
			distribution[i] = 255;
		}
		
		storage.setDistribution(distribution);
		storage.setGeometry(geom);
		storage.setMixer(mix);
		storage.setNrSteps(steps);
		storage.setMixingProtocol(protocol);
		storage.setSegregation(segr);
	}

}
