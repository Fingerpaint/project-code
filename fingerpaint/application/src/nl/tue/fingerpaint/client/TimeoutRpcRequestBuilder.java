package nl.tue.fingerpaint.client;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;

/**
 * Class that creates a {@link ServicedDefTarget} class class of your choice
 * that fails after a given amount of time before the request returns.
 * 
 * @author Group Fingerpaint
 * 
 */
public class TimeoutRpcRequestBuilder extends RpcRequestBuilder {

	/** The number of milliseconds after which the class fails */
	private int timeoutMillis;

	/**
	 * Constructs a TimeOutRpcRequestBuilder object.
	 * 
	 * @param timeoutMillis
	 *            The number of milliseconds after which the class fails
	 */
	public TimeoutRpcRequestBuilder(int timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}

	/**
	 * Returns the number of milliseconds after which the class fails.
	 * 
	 * @return The number of milliseconds after which the class fails
	 */
	public int getTimeoutMillis() {
		return timeoutMillis;
	}

	@Override
	protected RequestBuilder doCreate(String serviceEntryPoint) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				serviceEntryPoint);
		builder.setTimeoutMillis(timeoutMillis);
		return builder;
	}
}