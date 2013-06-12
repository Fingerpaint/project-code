package nl.tue.fingerpaint.shared.serverdata;

import nl.tue.fingerpaint.shared.ServerDataResult;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Service that is used to obtain data from the server.
 * 
 * @author Group Fingerpaint
 */
@RemoteServiceRelativePath("serverDataService")
public interface ServerDataService extends RemoteService {

	/**
	 * Function to obtain data (geometries, mixers) from the server.
	 * 
	 * @return Result, an object that contains all data from the server.
	 */
	public ServerDataResult getServerData();

}