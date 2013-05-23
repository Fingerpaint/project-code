package nl.tue.fingerpaint.server.simulator;

import nl.tue.fingerpaint.client.Simulation;
import nl.tue.fingerpaint.client.SimulationResult;
import nl.tue.fingerpaint.client.simulator.SimulatorService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementation of the {@link SimulatorService} RPC class.
 * 
 * @author Lasse Blaauwbroek
 *
 */
public class SimulatorServiceImpl extends RemoteServiceServlet implements SimulatorService {
	
	private static final NativeCommunicator nativeCommunicator = 
			new NativeCommunicator();


	/**
	 * Random serial version uid
	 */
	private static final long serialVersionUID = 3842884820635044977L;

	@Override
	public SimulationResult simulate(Simulation request) {
		
		int numVectorResults = request.calculatesIntermediateVectors() ? 
				request.getProtocolRuns() : 1;
		double[][] vectorResults = 
				new double[numVectorResults][request.getConcentrationVector().length];
		double[] segregationPoints = new double[request.getProtocolRuns()];
		
		double segregationPoint = 0;
		double[] vector = request.getConcentrationVector().clone();
		
		for (int i = 0; i < request.getProtocolRuns(); i++) {
			for (int j = 0; j < request.getProtocol().getProgramSize(); j++) {
				segregationPoint = nativeCommunicator.simulate(
						request.getProtocol().getGeometry(), request.getMixer(), 
						vector,
						request.getProtocol().getStep(j).getStepSize(), 
						request.getProtocol().getStep(j).getName());
			}
			segregationPoints[i] = segregationPoint;
			if (request.calculatesIntermediateVectors()) {
				vectorResults[i] = vector.clone();
			}
		}
		if (!request.calculatesIntermediateVectors()) {
			vectorResults[0] = vector.clone();
		}
		
		return new SimulationResult(request, vectorResults, segregationPoints);
	}

}