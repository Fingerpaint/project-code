package nl.tue.fingerpaint.server.simulator;

import nl.tue.fingerpaint.client.simulator.SimulatorService;
import nl.tue.fingerpaint.client.websocket.PerformancePoint;
import nl.tue.fingerpaint.client.websocket.Request;
import nl.tue.fingerpaint.client.websocket.Response;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Lasse Blaauwbroek
 *
 */
public class SimulatorServiceImpl extends RemoteServiceServlet implements SimulatorService {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3842884820635044977L;

	public Response simulate(Request request) {
		double[][] dists = {request.getDistribution()};
		PerformancePoint[] perf = {new PerformancePoint(0, 0.75)};
		
		return new Response(perf, dists);
	}

}