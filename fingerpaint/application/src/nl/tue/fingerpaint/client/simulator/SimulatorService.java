package nl.tue.fingerpaint.client.simulator;

import nl.tue.fingerpaint.client.websocket.Request;
import nl.tue.fingerpaint.client.websocket.Response;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("simulatorService")
public interface SimulatorService extends RemoteService {
	
	public Response simulate(Request request);

}