package nl.tue.fingerpaint.client.websocket;

/**
 * <p>
 * This interface is used to notify the arrival of a response. It is a
 * listener-like way of doing things and allows for asynchronous communication.
 * </p>
 * 
 * <p>
 * An implementation of this interface can be passed to
 * {@link SimulatorServiceSocket#requestSimulation(Request, ResponseCallback)}
 * and will be notified when a response comes back or an error occurs.
 * </p>
 * 
 * @author Group Fingerpaint
 */
public interface ResponseCallback {

	/**
	 * This method will be called when either there goes something wrong with
	 * the connection or the response from the server is malformed. The exact
	 * reason is given as a parameter.
	 * 
	 * @param message A message that explains (a bit) better what went wrong.
	 */
	public void onError(String message);

	/**
	 * This method will be called when a result comes back from the server.
	 * 
	 * @param result
	 *            The result that is returned from the server.
	 */
	public void onResponse(Response result);

}
