package nl.tue.fingerpaint.client.simulator;

import nl.tue.fingerpaint.shared.model.MixingProtocol;
import nl.tue.fingerpaint.shared.model.RectangleMixingStep;
import nl.tue.fingerpaint.client.storage.FingerpaintJsonizer;
import nl.tue.fingerpaint.client.storage.FingerpaintZipper;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * GWT jUnit tests for the class {@link SimulatorService}.
 * 
 * @author Group Fingerpaint
 */
public class SimulatorServiceTest extends GWTTestCase {

	private int[] concentrationVector;
	private MixingProtocol protocol;
	private Simulation simulation;
	private String mixer;
	private int nrSteps;
	final int size = 96000;
	private boolean allSteps;
	private AsyncCallback<SimulationResult> callback;
	private SimulatorServiceAsync service;

	/**
	 * A test that runs a simulation, using the {@link SimulatorService} class.
	 */
	@Test
	public void testSimulation() {

		callback = new AsyncCallback<SimulationResult>() {
			@Override
			public void onSuccess(SimulationResult result) {
				int[][] resultVectors = result.getConcentrationVectors();
				double[] resultSegregation = result.getSegregationPoints();
				int expectedResults = allSteps ? nrSteps : 1;

				assertEquals("Number of concentration vectors",
						expectedResults, resultVectors.length);
				for (int i = 0; i < resultVectors.length; i++) {
					int[] vector = resultVectors[i];
					assertEquals("Length of result vector " + i, size,
							vector.length);
					for (int j = 0; j < vector.length; j++) {
						assertTrue("Value " + j + " of vector " + i
								+ " is a valid value", vector[j] >= 0
								&& vector[j] < 256);
					}
				}

				assertEquals("Number of segregation points", expectedResults,
						resultSegregation.length);
				for (int i = 0; i < resultSegregation.length; i++) {
					assertTrue("Value " + i
							+ " of the segration is a valid value",
							resultSegregation[i] >= 0.0
									&& resultSegregation[i] <= 1.0);
				}
				finishTest();
			}

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				fail("The simulation failed");
				finishTest();
			}
		};

		init(true);
		service.simulate(simulation, callback);

		init(false);
		service.simulate(simulation, callback);
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

	// --- PRIVATE PART --------------------------------------------------
	/**
	 * Set up method for initialising the Simulation Object.
	 * 
	 * @param allSteps
	 *            {@code true} if all intermediate vectors have to be returned,
	 *            {@code false} otherwise.
	 */
	private void init(boolean allSteps) {
		mixer = "Default";
		concentrationVector = new int[size];
		for (int i = 0; i < size; i++) {
			if (i < size / 2) {
				concentrationVector[i] = 255;
			} else {
				concentrationVector[i] = 0;
			}
		}
		protocol = new MixingProtocol();
		protocol.addStep(new RectangleMixingStep(40, true, true));
		protocol.addStep(new RectangleMixingStep(40, true, false));
		nrSteps = 5;
		this.allSteps = allSteps;

		simulation = new Simulation(mixer, protocol,
				FingerpaintZipper.zip(
						FingerpaintJsonizer.toString(concentrationVector))
						.substring(1), nrSteps, allSteps);

		service = GWT.create(SimulatorService.class);
	}
}