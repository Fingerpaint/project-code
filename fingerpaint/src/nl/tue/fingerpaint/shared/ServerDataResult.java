package nl.tue.fingerpaint.shared;

import java.io.Serializable;

/**
 * A {@code ServerDataResult} object represents data that is retrieved from the
 * server. Currently, this is a list of available geometries, a list of mixers
 * and a mapping of to which geometry a mixer belongs.
 * 
 * @author Group Fingerpaint
 */
public class ServerDataResult implements Serializable {

	/** Generated serial version UID. */
	protected static final long serialVersionUID = -2040708625316261983L;
	/** A list of all available geometries. */
	protected String[] geometries;
	/** A list of all available mixers. */
	protected String[] mixers;
	/**
	 * <p>
	 * A mapping that indicates to which geometry a mixer belongs.
	 * </p>
	 * 
	 * <p>
	 * It means that when {@link #mixerGeometryMapping}{@code [i] == j}, then
	 * the mixer at index {@code i} in {@link #mixers} belongs to the geometry
	 * at index {@code j} in {@link #geometries}.
	 * </p>
	 */
	protected int[] mixerGeometryMapping;

	/**
	 * Construct a new {@link ServerDataResult} with no geometries and/or mixers
	 * available.
	 */
	public ServerDataResult() {
		this(new String[] {}, new String[] {}, new int[] {});
	}

	/**
	 * Construct a new {@link ServerDataResult}.
	 * 
	 * @param geometries
	 *            List of available geometries.
	 * @param mixers
	 *            List of available mixers.
	 * @param mixerGeometryMapping
	 *            Mapping from mixers to geometries.
	 */
	public ServerDataResult(String[] geometries, String[] mixers,
			int[] mixerGeometryMapping) {
		setGeometries(geometries);
		setMixers(mixers);
		setMixerGeometryMapping(mixerGeometryMapping);
	}

	/**
	 * Get the list of available geometries.
	 * 
	 * @return list of available geometries
	 */
	public String[] getGeometries() {
		return geometries;
	}

	/**
	 * Get the list of available mixers.
	 * 
	 * @return list of available mixers
	 */
	public String[] getMixers() {
		return mixers;
	}

	/**
	 * Get the mapping from mixers to geometries.
	 * 
	 * @return mapping from mixers to geometries
	 */
	public int[] getMixerGeometryMapping() {
		return mixerGeometryMapping;
	}

	/**
	 * <p>
	 * Change the available geometries.
	 * </p>
	 * 
	 * <p>
	 * <b>Note: this should be compatible with the mixers and mapping in this
	 * class!</b>
	 * </p>
	 * 
	 * @param geometries
	 *            New list of available geometries.
	 */
	public void setGeometries(String[] geometries) {
		this.geometries = geometries;
	}

	/**
	 * <p>
	 * Change the available mixers.
	 * </p>
	 * 
	 * <p>
	 * <b>Note: this should be compatible with the geometries and mapping in
	 * this class!</b>
	 * </p>
	 * 
	 * @param mixers
	 *            New list of available mixers.
	 */
	public void setMixers(String[] mixers) {
		this.mixers = mixers;
	}

	/**
	 * <p>
	 * Change the mapping from mixers to geometries.
	 * </p>
	 * 
	 * <p>
	 * <b>Note: this should be compatible with the geometries and mixers in this
	 * class!</b>
	 * </p>
	 * 
	 * @param mixerGeometryMapping
	 *            New mapping from mixers to geometries.
	 */
	public void setMixerGeometryMapping(int[] mixerGeometryMapping) {
		this.mixerGeometryMapping = mixerGeometryMapping;
	}
}
