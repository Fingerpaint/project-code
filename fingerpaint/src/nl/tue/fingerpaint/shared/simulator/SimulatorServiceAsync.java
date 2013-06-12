package nl.tue.fingerpaint.shared.simulator;


import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A service that can be used to run a simulation.
 * 
 * @author Lasse Blaauwbroek
 *
 */
public interface SimulatorServiceAsync {

	/**
	 * Simulates a {@link Simulation} and returns the result in a 
	 * {@link SimulationResult} to the callback.
	 * 
	 * @param simulation The simulation to be run
	 * @param callback The callback to which results are reported
	 */
	void simulate(Simulation simulation,
			AsyncCallback<SimulationResult> callback);

}