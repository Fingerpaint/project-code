package nl.tue.fingerpaint.client.simulator;

import nl.tue.fingerpaint.client.model.MixingProtocol;
import nl.tue.fingerpaint.client.simulator.SimulatorService;
import nl.tue.fingerpaint.client.simulator.SimulatorServiceAsync;

import org.junit.Test;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * GWT jUnit tests for the class {@link SimulatorService}.
 * 
 * @author Group Fingerpaint
 */
public class SimulatorServiceTest extends GWTTestCase {

	/**
	 * A test that runs a simulation, using the {@link SimulatorService} class.
	 */
	@Test
	public void testSimulation() {
		SimulatorServiceAsync service = GWT.create(SimulatorService.class);
		AsyncCallback<SimulationResult> callback = new AsyncCallback<SimulationResult>() {
			@Override
			public void onSuccess(SimulationResult result) {
				System.out.println(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		};

		int size = 96000;
		double[] concentrationVector = new double[size];
		for (int i = 0; i < size; i++) {
			if (i < size / 2) {
				concentrationVector[i] = 1;
			} else {
				concentrationVector[i] = 0;
			}
		}
		MixingProtocol protocol = new MixingProtocol();
		protocol.addStep(40, true, true);
		protocol.addStep(40, true, false);
		Simulation simulation = new Simulation("Default", protocol,
				concentrationVector, 5, true);

		service.simulate(simulation, callback);

	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}