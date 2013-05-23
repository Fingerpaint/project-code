package nl.tue.fingerpaint.client.serverdata;

import nl.tue.fingerpaint.shared.ServerDataResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous counterpart of the {@link ServerDataService}.
 * 
 * @author Group Fingerpaint
 */
public interface ServerDataServiceAsync {

	/**
	 * Asynchronous counterpart of {@link ServerDataService#getServerData()}.
	 * 
	 * @param callback
	 *            Callback that is notified when data comes back from the
	 *            server.
	 */
	public void getServerData(AsyncCallback<ServerDataResult> callback);
}
