package nl.tue.fingerpaint.client.simulator;

import nl.tue.fingerpaint.client.Simulation;
import nl.tue.fingerpaint.client.SimulationResult;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A service that can be used to run a simulation.
 * 
 * @author Lasse Blaauwbroek
 *
 */
@RemoteServiceRelativePath("simulatorService")
public interface SimulatorService extends RemoteService {

	/**
	 * Simulates a {@link Simulation} and returns the result in a 
	 * {@link SimulationResult}.
	 * 
	 * @param simulation The simulation to be run
	 * @return The result of the simulation
	 */
	public SimulationResult simulate(Simulation simulation);

}