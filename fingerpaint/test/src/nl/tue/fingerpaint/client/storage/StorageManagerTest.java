package nl.tue.fingerpaint.client.storage;

import java.util.ArrayList;

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

	/**
	 * A test for saving to and retrieving an initial concentration distribution
	 * from the local storage.
	 */
	@Test
	public void testSaveInitialDistribution() {
		// Start and end-indices for the non-default values (1.0) in the initial
		// distribution.
		final int startIndex = 532;
		final int endIndex = 9428;

		final String key = "testdist1";
		final String geometry = GeometryNames.RECT_SHORT;

		// Create an initial distribution and set some values in the
		// distribution to 1.
		int[] distribution = new int[96000];
		for (int i = startIndex; i < endIndex; i++) {
			distribution[i] = 255;
		}

		// Save the distribution in the local storage.
		StorageManager.INSTANCE.putDistribution(geometry, key, distribution);

		// Receive the initial distribution from the local storage and test it.
		int[] receivedDist = StorageManager.INSTANCE.getDistribution(geometry,
				key);

		for (int i = 0; i < receivedDist.length; i++) {
			int expectedValue = (i > (startIndex - 1) && i < endIndex) ? 255
					: 0;
			assertEquals("Value on index " + i, expectedValue, receivedDist[i]);
		}
	}

	/**
	 * A test for saving to and retrieving a mixing protocol from the local
	 * storage.
	 */
	@Test
	public void testSaveProtocol() {
		// Create and save a protocol
		final String key = "testprot1";
		final String geometry = GeometryNames.RECT_SHORT;
		initProtocol();
		StorageManager.INSTANCE.putProtocol(geometry, key, protocol);

		// Receive and test the protocol
		MixingProtocol receivedProtocol = StorageManager.INSTANCE.getProtocol(
				geometry, key);

		for (int i = 0; i < program.size(); i++) {
			RectangleMixingStep currStep = (RectangleMixingStep) receivedProtocol.getStep(i);
			RectangleMixingStep checkStep = (RectangleMixingStep) program.get(i);
			assertEquals("Step size of step " + i,
					checkStep.getStepSize(), currStep
							.getStepSize(), 0.000001);
			assertEquals("Top wall of step " + i, checkStep.isTopWall(),
					currStep.isTopWall());
			assertEquals("Direction of step " + i,
					checkStep.isClockwise(), currStep
							.isClockwise());
		}
	}

	/**
	 * A test for saving to and retrieving a mixing result from the local
	 * storage.
	 */
	@Test
	public void testSaveResult() {
		final int startIndex = 532;
		final int endIndex = 9428;

		final String geom = GeometryNames.RECT_LONG;
		final String mix = "Default";
		final int steps = 20;
		final double[] segr = new double[] { 0.1, 0.2, 0.3, 0.7, 0.6, 0.8, 0.8,
				0.4, 0.9, 0.4, 0.8, 0.2, 0.6, 0.3, 0.7, 0.5, 0.9, 0.4, 0.8, 0.1 };

		final String key = "result1";
		final ResultStorage rs = new ResultStorage();
		int[] distribution = new int[96000];
		for (int i = startIndex; i < endIndex; i++) {
			distribution[i] = 255;
		}
		rs.setDistribution(distribution);
		rs.setGeometry(geom);
		rs.setMixer(mix);
		rs.setNrSteps(steps);
		initProtocol();
		rs.setMixingProtocol(protocol);
		rs.setSegregation(segr);
		
		StorageManager.INSTANCE.putResult(key, rs);

		// Receive and test the protocol
		ResultStorage receivedResult = StorageManager.INSTANCE.getResult(key);

		int[] receivedDist = receivedResult.getDistribution();
		for (int i = 0; i < receivedDist.length; i++) {
			int expectedValue = (i > (startIndex - 1) && i < endIndex) ? 255
					: 0;
			assertEquals("Value on index " + i, expectedValue, receivedDist[i]);
		}

		assertEquals("The geometry of the result", geom,
				receivedResult.getGeometry());
		assertEquals("The mixer of the result", mix, receivedResult.getMixer());
		assertEquals("Nr of steps of the result", steps,
				receivedResult.getNrSteps());

		MixingProtocol receivedProtocol = receivedResult.getMixingProtocol();
		for (int i = 0; i < program.size(); i++) {
			RectangleMixingStep currStep = (RectangleMixingStep) receivedProtocol.getStep(i);
			RectangleMixingStep checkStep = (RectangleMixingStep) program.get(i);
			assertEquals("Step size of step " + i,
					checkStep.getStepSize(), currStep
							.getStepSize(), 0.000001);
			assertEquals("Top wall of step " + i, checkStep.isTopWall(),
					currStep.isTopWall());
			assertEquals("Direction of step " + i,
					checkStep.isClockwise(), currStep
							.isClockwise());
		}

		double[] receivedSegregation = receivedResult.getSegregation();
		assertEquals("Length of the segregation", segr.length,
				receivedSegregation.length);
		for (int i = 0; i < segr.length; i++) {
			assertEquals("Segregation value at index " + i, segr[i],
					receivedSegregation[i]);
		}
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

}
