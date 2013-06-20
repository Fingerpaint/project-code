package nl.tue.fingerpaint.server.serverdata;

import nl.tue.fingerpaint.shared.GeometryNames;
import nl.tue.fingerpaint.shared.MixerNames;
import nl.tue.fingerpaint.shared.ServerDataResult;
import nl.tue.fingerpaint.shared.serverdata.ServerDataService;

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

	/**
	 * Returns server data, i.e. which geometries and mixers are available.
	 * 
	 * @return server data, i.e. which geometries and mixers are available.
	 */
	@Override
	public ServerDataResult getServerData() {
		return new ServerDataResult(
				new String[] { GeometryNames.RECT, GeometryNames.SQR },
				new String[] { MixerNames.RectMixers.DEFAULT, MixerNames.SqrMixers.DEFAULT },
				new int[] { 0, 1 }
			);
	}

}
