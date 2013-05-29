package nl.tue.fingerpaint.client.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.tue.fingerpaint.client.MixingProtocol;
import nl.tue.fingerpaint.client.json.FingerpaintJsonizer;
import nl.tue.fingerpaint.shared.GeometryNames;

import com.google.gwt.core.client.GWT;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageMap;

/**
 * <p>
 * A {@code StorageManager} manages the local storage in the browser. The is
 * only one instance of the storage manager, that must be used for all
 * interaction with the storage.
 * </p>
 * 
 * <p>
 * The local storage is structured as follows:
 * </p>
 * <ul>
 * <li>{@link #KEY_INITDIST}
 * <ul>
 * <li>{@link GeometryNames#RECT_SHORT}
 * <ul>
 * <li>Name of some saved distribution for a rectangular geometry</li>
 * <li>Other name</li>
 * <li>...</li>
 * </ul>
 * </li>
 * <li>{@link GeometryNames#SQR_SHORT}
 * <ul>
 * <li>Name of saved distribution for a square geometry</li>
 * <li>Other name</li>
 * <li>...</li>
 * </ul>
 * </li>
 * <li>{@link GeometryNames#CIRC_SHORT}
 * <ul>
 * <li>Name of saved distribution for a circle geometry</li>
 * <li>Other name</li>
 * <li>...</li>
 * </ul>
 * </li>
 * <li>{@link GeometryNames#JOBE_SHORT}
 * <ul>
 * <li>Name of saved distribution for a journal bearing geometry</li>
 * <li>Other name</li>
 * <li>...</li>
 * </ul>
 * </li>
 * </ul>
 * </li>
 * <li>{@link #KEY_PROTOCOLS}
 * <ul>
 * <li>Same structure as for previous key, only with saved protocols instead of
 * saved distributions.</li>
 * </ul>
 * </li>
 * <li>{@link #KEY_RESULTS}
 * <ul>
 * <li>Name of saved result</li>
 * <li>Other name</li>
 * <li>...</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Group Fingerpaint
 */
public class StorageManager {

	/** Used to indicate that the local storage cannot be used. */
	public static final int ERROR = -1;
	/** Used to indicate that the local storage has not yet been initialised. */
	public static final int NOT_INITIALISED = 0;
	/** Used to indicate that the local storage is ready for use. */
	public static final int INITIALISED = 0;

	/**
	 * Key on the highest level of the local storage that can be used to
	 * obtain/store a list of saved (initial) distributions.
	 */
	public static final String KEY_INITDIST = "INIT";
	/**
	 * Key on the highest level of the local storage that can be used to
	 * obtain/store a list of saved protocols.
	 */
	public static final String KEY_PROTOCOLS = "PROT";
	/**
	 * Key on the highest level of the local storage that can be used to
	 * obtain/store a list of saved results. A result is a complete application
	 * state.
	 */
	public static final String KEY_RESULTS = "RES";

	/**
	 * Instance that must be used for interaction with local storage.
	 */
	public static StorageManager INSTANCE = new StorageManager();

	/** GWT object that is the actual interface with the local storage. */
	protected Storage localStorage;
	/** State of the storage. */
	protected int state = NOT_INITIALISED;

	/** Keys that are used on the first level. */
	protected String[] firstLevelKeys = new String[] { KEY_INITDIST,
			KEY_PROTOCOLS, KEY_RESULTS };
	/** Keys that are used on the second level. */
	protected String[] secondLevelKeys = new String[] {
			GeometryNames.CIRC_SHORT, GeometryNames.JOBE_SHORT,
			GeometryNames.RECT_SHORT, GeometryNames.SQR_SHORT };

	/**
	 * Make constructor protected to prevent instantiations of this class.
	 */
	protected StorageManager() {
		localStorage = Storage.getLocalStorageIfSupported();

		// Make sure that all keys are set
		StorageMap sm = new StorageMap(localStorage);
		for (String firstLevelKey : firstLevelKeys) {
			if (!sm.containsKey(firstLevelKey)) {
				HashMap<String, Object> secondLevel = new HashMap<String, Object>();
				for (String secondLevelKey : secondLevelKeys) {
					secondLevel.put(secondLevelKey, "{}");
				}
				localStorage.setItem(firstLevelKey,
						FingerpaintJsonizer.toString(secondLevel));
			} else {
				HashMap<String, Object> secondLevel = FingerpaintJsonizer
						.hashMapFromString(sm.get(firstLevelKey));
				boolean changed = false;
				for (String secondLevelKey : secondLevelKeys) {
					if (!secondLevel.containsKey(secondLevelKey)) {
						secondLevel.put(secondLevelKey, "{}");
						changed = true;
					}
				}
				if (changed) {
					localStorage.setItem(firstLevelKey,
							FingerpaintJsonizer.toString(secondLevel));
				}
			}
		}

		if (localStorage == null) {
			state = ERROR;
		} else {
			state = INITIALISED;
		}
	}

	/**
	 * Return the distribution that is stored with given name, or {@code null}
	 * if such a distribution does not exist.
	 * 
	 * @param geometry
	 *            The geometry in which the distribution is stored.
	 * @param key
	 *            The name of the saved distribution.
	 * @return The saved distribution, or {@code null} if no distribution with
	 *         the given name was saved. This function will also return
	 *         {@code null} if the storage cannot be used.
	 */
	public double[] getDistribution(String geometry, String key) {
		if (state != INITIALISED) {
			return null;
		}

		HashMap<String, Object> firstLevel = FingerpaintJsonizer
				.hashMapFromString(localStorage.getItem(KEY_INITDIST));
		if (firstLevel.containsKey(geometry)) {
			HashMap<String, Object> secondLevel = FingerpaintJsonizer
					.hashMapFromString((String) firstLevel.get(geometry));
			for (String secondLevelKey : secondLevel.keySet()) {
				if (secondLevelKey.equals(key)) {
					return FingerpaintJsonizer
							.doubleArrayFromString((String) secondLevel
									.get(secondLevelKey));
				}
			}
		}
		return null;
	}

	/**
	 * Return a list of all distributions stored under the given geometry.
	 * 
	 * @param geometry
	 *            The geometry under which saved distributions need to be found.
	 * @return A list of all saved distributions (may be an empty list), or
	 *         {@code null} if the given geometry is not at all present in the
	 *         storage. When the latter happens, you probably asked for a
	 *         non-existent geometry, because all default geometries are loaded
	 *         in the storage on initialisation. This function will also return
	 *         {@code null} if the storage cannot be used.
	 */
	public List<String> getDistributions(String geometry) {
		if (state != INITIALISED) {
			return null;
		}

		GWT.log("getDistributions");
		HashMap<String, Object> firstLevel = FingerpaintJsonizer
				.hashMapFromString(localStorage.getItem(KEY_INITDIST));
		if (firstLevel.containsKey(geometry)) {
			for (String bla : firstLevel.keySet()) {
				GWT.log("KEY: " + bla + ", VAL: " + firstLevel.get(bla));
			}
			HashMap<String, Object> secondLevel = FingerpaintJsonizer
					.hashMapFromString((String) firstLevel.get(geometry));
			ArrayList<String> result = new ArrayList<String>();
			for (String secondLevelKey : secondLevel.keySet()) {
				result.add(secondLevelKey);
			}
			return result;
		}
		return null;
	}

	/**
	 * Save an initial distribution to the local storage. If the name already
	 * exists, do not attempt to overwrite.
	 * 
	 * @param geometry
	 *            The geometry to store the distribution under.
	 * @param key
	 *            The name of the distribution.
	 * @param value
	 *            The distribution to be saved.
	 * @return True if the value has been saved, false if the name is already in
	 *         use (no attempt to overwrite will be made). Will also return
	 *         false when the storage cannot be used.
	 */
	public boolean putDistribution(String geometry, String key, double[] value) {
		return putDistribution(geometry, key, value, false);
	}

	/**
	 * Save an initial distribution to the local storage. If the name already
	 * exists, does overwrite when asked.
	 * 
	 * @param geometry
	 *            The geometry to store the distribution under.
	 * @param key
	 *            The name of the distribution.
	 * @param value
	 *            The distribution to be saved.
	 * @param overwrite
	 *            If the value should be overwritten if the name is already in
	 *            use.
	 * @return True if the value has been saved, false if the name is already in
	 *         use (no attempt to overwrite will be made). Will also return
	 *         false when the storage cannot be used.
	 */
	public boolean putDistribution(String geometry, String key, double[] value,
			boolean overwrite) {
		if (state != INITIALISED) {
			return false;
		}

		if (isNameInUse(KEY_INITDIST, geometry, key) && !overwrite) {
			return false;
		}

		HashMap<String, Object> firstLevel = FingerpaintJsonizer
				.hashMapFromString(localStorage.getItem(KEY_INITDIST));

		if (firstLevel.containsKey(geometry)) {
			HashMap<String, Object> secondLevel = FingerpaintJsonizer
					.hashMapFromString((String) firstLevel.get(geometry));
			secondLevel.put(key, FingerpaintJsonizer.toString(value));
			firstLevel.put(geometry, FingerpaintJsonizer.toString(secondLevel));
			localStorage.setItem(KEY_INITDIST,
					FingerpaintJsonizer.toString(firstLevel));
			return true;
		}

		return false;
	}
	
	/**
	 * Save an initial distribution to the local storage. If the name already
	 * exists, do not attempt to overwrite.
	 * 
	 * @param geometry
	 *            The geometry to store the distribution under.
	 * @param key
	 *            The name of the distribution.
	 * @param value
	 *            The distribution to be saved.
	 * @return True if the value has been saved, false if the name is already in
	 *         use (no attempt to overwrite will be made).
	 */
	public boolean putProtocol(String geometry, String key, MixingProtocol protocol) {
		return putProtocol(geometry, key, protocol, false);
	}

	/**
	 * Save an initial distribution to the local storage. If the name already
	 * exists, does overwrite when asked.
	 * 
	 * @param geometry
	 *            The geometry to store the distribution under.
	 * @param key
	 *            The name of the distribution.
	 * @param value
	 *            The distribution to be saved.
	 * @param overwrite
	 *            If the value should be overwritten if the name is already in
	 *            use.
	 * @return True if the value has been saved, false if the name is already in
	 *         use (no attempt to overwrite will be made).
	 */
	public boolean putProtocol(String geometry, String key, MixingProtocol protocol,
			boolean overwrite) {
		if (isNameInUse(KEY_PROTOCOLS, geometry, key) && !overwrite) {
			return false;
		}

		HashMap<String, Object> firstLevel = FingerpaintJsonizer
				.hashMapFromString(localStorage.getItem(KEY_PROTOCOLS));

		if (firstLevel.containsKey(geometry)) {
			HashMap<String, Object> secondLevel = FingerpaintJsonizer
					.hashMapFromString((String) firstLevel.get(geometry));
			secondLevel.put(key, FingerpaintJsonizer.toString(protocol));
			firstLevel.put(geometry, FingerpaintJsonizer.toString(secondLevel));
			localStorage.setItem(KEY_PROTOCOLS,
					FingerpaintJsonizer.toString(firstLevel));
			return true;
		}

		return false;
	}

	/**
	 * Returns if there is a key at the specified level with given name. When
	 * you want to test a key on the second level, leave the value for the third
	 * level {@code null}.
	 * 
	 * @param firstLevelKey
	 *            The key on the highest level. Needs to be set, needs to be one
	 *            of {@link #KEY_INITDIST}, {@link #KEY_PROTOCOLS} or
	 *            {@link #KEY_RESULTS}.
	 * @param secondLevelKey
	 *            The key on the second level. Needs to be set and either some
	 *            name, to test if that name exists among all saved results, or
	 *            a geometry name. In the latter case, it should be one of the
	 *            short names from the {@link GeometryNames}.
	 * @param thirdLevelKey
	 *            The key on the third level. Leave this {@code null} when
	 *            looking for a result name, or provide the name to check here
	 *            otherwise.
	 * @return If the key exists or not. When {@code firstLevelKey} or
	 *         {@code secondLevelKey} are not set, then this function returns
	 *         {@code true}.
	 */
	protected boolean isNameInUse(String firstLevelKey, String secondLevelKey,
			String thirdLevelKey) {
		// Check required keys
		if (firstLevelKey == null || secondLevelKey == null) {
			return true;
		}
		// Check if looking for second or third level
		if (thirdLevelKey == null) {
			// Looking for a result here
			HashMap<String, Object> results = FingerpaintJsonizer
					.hashMapFromString(localStorage.getItem(firstLevelKey));
			for (String key : results.keySet()) {
				if (key.equals(secondLevelKey)) {
					return true;
				}
			}
			return false;
		} else {
			// Looking for a distribution or protocol here
			HashMap<String, Object> firstLevel = FingerpaintJsonizer
					.hashMapFromString(localStorage.getItem(firstLevelKey));
			if (firstLevel.containsKey(secondLevelKey)) {
				HashMap<String, Object> secondLevel = FingerpaintJsonizer
						.hashMapFromString((String) firstLevel
								.get(secondLevelKey));
				for (String key : secondLevel.keySet()) {
					if (key.equals(thirdLevelKey)) {
						return true;
					}
				}
				return false;
			}
			return false;
		}
	}
}