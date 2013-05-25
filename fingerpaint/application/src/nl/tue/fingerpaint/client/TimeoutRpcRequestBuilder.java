package nl.tue.fingerpaint.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Class that creates a {@link ServicedDefTarget} class class of your choice
 * that fails after
 * a given amount of time before the request returns.
 * 
 * @author Lasse Blaauwbroek
 *
 */
public class TimeoutRpcRequestBuilder extends RpcRequestBuilder {
	
	private int timeoutMillis;
	
	public TimeoutRpcRequestBuilder(int timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}
	
	public int getTimeoutMillis() {
		return timeoutMillis;
	}
	
    @Override
    protected RequestBuilder doCreate(String serviceEntryPoint) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, serviceEntryPoint);
        builder.setTimeoutMillis(timeoutMillis);
        return builder;
    }
} 