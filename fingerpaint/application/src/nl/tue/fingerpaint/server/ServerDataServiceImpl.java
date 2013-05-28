package nl.tue.fingerpaint.server;

import nl.tue.fingerpaint.client.serverdata.ServerDataService;
import nl.tue.fingerpaint.shared.GeometryNames;
import nl.tue.fingerpaint.shared.ServerDataResult;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementation of the {@link ServerDataService}.
 * 
 * @author Group Fingerpaint
 */
public class ServerDataServiceImpl extends RemoteServiceServlet implements
		ServerDataService {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 8671226026204731779L;

	@Override
	public ServerDataResult getServerData() {
		return new ServerDataResult(
				new String[] { GeometryNames.CIRC_LONG, GeometryNames.RECT_LONG, GeometryNames.JOBE_LONG, GeometryNames.SQR_LONG },
				new String[] { "Mixer 1", "Mixer 2", "Mixer 3", "Mixer 4", "Mixer 5", "Mixer 6", "Mixer 7", "Mixer 8", "Mixer 9", "Mixer 10", "Mixer 11",
				"Mixer 12", "Default" },
				new int[] { 0, 0, 3, 0, 2, 1, 1, 3, 3, 2, 2, 1 , 1});
	}

}
