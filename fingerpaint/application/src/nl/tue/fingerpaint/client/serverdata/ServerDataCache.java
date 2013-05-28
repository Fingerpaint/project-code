package nl.tue.fingerpaint.client.serverdata;

import java.util.ArrayList;

import nl.tue.fingerpaint.client.TimeoutRpcRequestBuilder;
import nl.tue.fingerpaint.client.simulator.SimulatorService;
import nl.tue.fingerpaint.client.simulator.SimulatorServiceAsync;
import nl.tue.fingerpaint.shared.ServerDataResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * A {@code ServerDataCache} can obtain data from the server, namely the
 * geometries and mixers that are available. This data will be retrieved only
 * the first time, after that, data is served from a cache that is managed by
 * this class. This class contains only static methods, because retrieving data
 * should go to one central point, which makes instances unnecessary.
 * 
 * @author Group Fingerpaint
 */
public class ServerDataCache {

	/** State indicating that the cache is not initialised. */
	protected static final int NOT_INITIALISED = 0;
	/**
	 * State indicating that the cache is now initialising, that is, retrieving
	 * data from the server.
	 */
	protected static final int LOADING = 1;
	/**
	 * State indicating that the cache has been initialised with data from the
	 * server and can be queried now.
	 */
	protected static final int INITIALISED = 2;

	/**
	 * Data in cache.
	 */
	protected static ServerDataResult cache;
	/**
	 * <p>
	 * State of this cache. Can be:
	 * </p>
	 * <ul>
	 * <li>{@link #NOT_INITIALISED}</li>
	 * <li>{@link #LOADING}</li>
	 * <li>{@link #INITIALISED}</li>
	 * </ul>
	 */
	protected static int cacheState = NOT_INITIALISED;

	private ServerDataCache() {
		// make constructor private, because this class should not be
		// instantiated
	}

	/**
	 * Return a list of the available geometries, or {@code null} if this cache
	 * has not been initialised yet.
	 * 
	 * @return a list of available geometries (by name), or {@code null} if this
	 *         cache has not been initialised yet
	 */
	public static String[] getGeometries() {
		if (!isLoaded()) {
			return null;
		}
		return cache.getGeometries();
	}

	/**
	 * Return a list of the available mixers, or {@code null} if this cache has
	 * not been initialised yet.
	 * 
	 * @return a list of available mixers (by name), or {@code null} if this
	 *         cache has not been initialised yet
	 */
	public static String[] getMixers() {
		if (!isLoaded()) {
			return null;
		}
		return cache.getMixers();
	}
	
	/**
	 * Return a list of all matrices that belong to the given geometry. If this
	 * cache has not been initialised yet or the given geometry is not in the
	 * list of geometries in this cache, {@code null} is returned.
	 * 
	 * @param geometry
	 *            The geometry to which all returned mixers should belong.
	 * @return a list of mixers that belong to the given geometry, or
	 *         {@code null} if the cache has not been initialised or the given
	 *         geometry name is unknown (not in the cache)
	 */
	public static String[] getMixersForGeometry(String geometry) {
		if (!isLoaded()) {
			return null;
		}

		// Find index of geometry
		int geometryIndex = -1;
		String[] geoms = cache.getGeometries();
		for (int i = 0; i < geoms.length; i++) {
			if (geoms[i].equals(geometry)) {
				geometryIndex = i;
				break;
			}
		}
		if (geometryIndex < 0) {
			return null;
		}

		// Build list of all matrices for found geometry
		ArrayList<String> mixers = new ArrayList<String>(geoms.length);
		int[] mapping = cache.getMixerGeometryMapping();
		for (int i = 0; i < mapping.length; i++) {
			if (mapping[i] == geometryIndex) {
				mixers.add(cache.getMixers()[i]);
			}
		}
		return mixers.toArray(new String[] {});
	}

	/**
	 * Initialise the cache. When done, fire the given callback.
	 * 
	 * @param onInitialised
	 *            The callback to fire when the cache is initialised. When
	 *            {@code null}, nothing happens when the cache is initialised
	 *            (but it will be initialised anyway!).
	 */
	public static void initialise(final AsyncCallback<String> onInitialised) {
		TimeoutRpcRequestBuilder timeoutRpcRequestBuilder = new TimeoutRpcRequestBuilder(5000);
		ServerDataServiceAsync service = (ServerDataServiceAsync) GWT
				.create(ServerDataService.class);
    	((ServiceDefTarget) service).setRpcRequestBuilder(timeoutRpcRequestBuilder);
		AsyncCallback<ServerDataResult> callback = new AsyncCallback<ServerDataResult>() {
			@Override
			public void onSuccess(ServerDataResult result) {
				cache = result;
				cacheState = INITIALISED;
				if (onInitialised != null) {
					onInitialised.onSuccess("Cache initialised succesfully.");
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				cacheState = NOT_INITIALISED;
				if (onInitialised != null) {
					onInitialised.onFailure(caught);
				}
			}
		};
		cacheState = LOADING;
		service.getServerData(callback);
	}

	/**
	 * Return if data has been loaded from the server already.
	 * 
	 * @return if data has been loaded from the server or not
	 */
	public static boolean isLoaded() {
		return cache != null
				&& cache.getGeometries() != null
				&& cache.getMixers() != null
				&& cache.getMixerGeometryMapping() != null
				&& cache.getMixers().length == cache.getMixerGeometryMapping().length;
	}
}
