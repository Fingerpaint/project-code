package nl.tue.fingerpaint.client.simulator;

import nl.tue.fingerpaint.client.websocket.Request;
import nl.tue.fingerpaint.client.websocket.Response;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SimulatorServiceAsync {

	void simulate(Request request, AsyncCallback<Response> callback);

}
