package nl.tue.fingerpaint.client.websocket;

import org.jsonmaker.gwt.client.JsonizerException;
import org.jsonmaker.gwt.client.JsonizerParser;

import com.google.gwt.core.shared.GWT;

import de.csenk.gwt.ws.client.WebSocket;
import de.csenk.gwt.ws.client.WebSocketCallback;
import de.csenk.gwt.ws.client.js.JavaScriptWebSocket;

/**
 * <p>
 * The {@code SimulatorServiceSocket} provides an easy-to-use way to communicate
 * with the simulator service. All communication should go through this class,
 * which is why there is only one (at most) shared instance that can be obtained
 * through a call to {@link #getInstance()}.
 * </p>
 * 
 * <p>
 * If, for instance, a simulation should be performed, you can do this as
 * follows, using this class, {@link Request} and {@link ResponseCallback}:
 * </p>
 * 
 * <pre>
 *   Request request = new Request(...);
 *   ResponseCallback callback = new ResponseCallback() {
 *     public void onError(String message) {
 *       // here, handle an error in the connection or simulation, et cetera
 *     }
 *     
 *     public void onResponse(Response result) {
 *       // here, do something with the simulation result
 *     }
 *   };
 *   SimulatorServiceSocket sss = SimulatorServiceSocket.getInstance();
 *   sss.requestSimulation(request, callback);
 * </pre>
 * 
 * @author Group Fingerpaint
 */
public class SimulatorServiceSocket {

	/**
	 * The URL at which the simulator service can be reached.
	 */
	public static final String SERVICE_URL = "ws://localhost/websocket";

	/**
	 * This is a static object because this class cannot be instantiated. The
	 * idea is that all communication to and from the simulator service goes
	 * through this class. For this, only one instance is needed that will
	 * coordinate all traffic.
	 */
	protected static SimulatorServiceSocket instance;
	/**
	 * The actual connection.
	 */
	protected JavaScriptWebSocket connection;
	/**
	 * Callback that is used to notify someone that a response has arrived or an
	 * error has occurred.
	 */
	protected ResponseCallback callback;
	/**
	 * Indicate if connection is open or not.
	 */
	protected boolean isOpen;
	/**
	 * Indicate if a transmission/simulation is taking place right now.
	 */
	protected boolean isBusy;

	/**
	 * Instantiate an instance of the {@code SimulatorServiceSocket}. This will
	 * open a connection with the service and put a callback in place.
	 * 
	 * @throws UnsupportedOperationException
	 *             When WebSockets are not supported.
	 */
	protected SimulatorServiceSocket() {
		/*
		 * Make constructor private as we do not want it to be instantiated by
		 * somebody else... There will be just one shared instance.
		 */

		// Check if WebSockets are supported
		if (!JavaScriptWebSocket.IsSupported()) {
			throw new UnsupportedOperationException(
					"WebSockets are not supported in this browser.");
		}

		// Set up a connection
		isOpen = false;
		isBusy = false;
		connection = new JavaScriptWebSocket(SERVICE_URL, initCallback());
	}

	/**
	 * Instantiate a callback that is used in the WebSocket.
	 * 
	 * @return A {@link WebSocketCallback} that can be used in a
	 *         {@link JavaScriptWebSocket}.
	 */
	protected WebSocketCallback initCallback() {
		return new WebSocketCallback() {
			@Override
			public void onOpen(WebSocket webSocket) {
				isOpen = true;
			}

			@Override
			public void onMessage(WebSocket webSocket, String message) {
				if (callback != null) {
					// Try to parse the result
					Response.ResponseJsonizer responseJsonizer = (Response.ResponseJsonizer) GWT
							.create(Response.ResponseJsonizer.class);
					try {
						Response response = (Response) JsonizerParser.parse(
								responseJsonizer, message);
						callback.onResponse(response);
					} catch (JsonizerException je) {
						callback.onError("Malformed message. ('"
								+ je.getMessage() + "')");
					}
					callback = null;
				}
			}

			@Override
			public void onError(WebSocket webSocket) {
				// Refer to
				// https://developer.mozilla.org/en-US/docs/
				// WebSockets/WebSockets_reference/WebSocket
				// to see why the readyState should be 1.
				isOpen = (webSocket.getReadyState() == 1);
				if (callback != null) {
					callback.onError(isOpen ? "Bad response."
							: "Socket closed/connection lost.");
					callback = null;
				}
			}

			@Override
			public void onClose(WebSocket webSocket) {
				isOpen = false;
			}
		};
	}

	/**
	 * Return a shared instance of the {@link SimulatorServiceSocket}. This
	 * instance can be used for communication with the simulator service.
	 * 
	 * @return A shared instance of the {@link SimulatorServiceSocket}.
	 * @throws UnsupportedOperationException
	 *             When WebSockets are not supported.
	 */
	public static SimulatorServiceSocket getInstance() {
		if (instance == null) {
			instance = new SimulatorServiceSocket();
		}

		return instance;
	}

	/**
	 * Return if the connection with the simulator service is open or not.
	 * 
	 * @return {@code true} if the connection is open now, {@code false}
	 *         otherwise.
	 */
	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * Send a message to the server, requesting to perform a simulation. The
	 * response of the server will be made available through the given callback.
	 * 
	 * @param request
	 *            The request to be sent to the server.
	 * @param responseCallback
	 *            A callback that will be notified when a response comes back.
	 *            it is guaranteed that this callback will be called exactly
	 *            once, either with an {@link ResponseCallback#onError(String)}.
	 *            or an {@link ResponseCallback#onResponse(Response)}.
	 * @return {@code true} if the request has been made successfully and a
	 *         response is expected to be handled in the callback. {@code false}
	 *         if there is a request pending right now or the connection was
	 *         closed ({@link #isOpen}{@code () == false}).
	 */
	public boolean requestSimulation(Request request,
			ResponseCallback responseCallback) {
		// Check if there is no other request running now
		if (isBusy || !isOpen) {
			return false;
		}

		// We are busy now
		isBusy = true;
		// Set callback
		callback = responseCallback;

		// Create a JSON string from the request and send it
		Request.RequestJsonizer requestJsonizer = (Request.RequestJsonizer) GWT
				.create(Request.RequestJsonizer.class);
		String requestJson = requestJsonizer.asString(request);
		connection.send(requestJson);

		// Immediately return: the response will be handled in the callback that
		// is set now. Everything went ok, so return true.
		return true;
	}
}
