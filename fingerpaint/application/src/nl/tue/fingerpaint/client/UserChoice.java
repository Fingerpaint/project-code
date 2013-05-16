package nl.tue.fingerpaint.client;

/**
 * Class that keeps track of the Geometry and Mixer the user has selected. Used
 * by the cellBrowser widget in Fingerpaint.java to store chosen variables.
 * 
 * @author Group Fingerpaint
 */
public class UserChoice {

	private GeometryNames geoChoice = null;
	private Mixer mixChoice = null;

	/**
	 * Sets {@code geoChoice} to the given geometry {@code g}
	 * 
	 * @param g
	 *            The value to be set
	 */
	public void setGeometry(GeometryNames g) {
		geoChoice = g;
	}

	/**
	 * Sets {@code mixChoice} to the given Mixer {@code m}
	 * 
	 * @param m
	 *            The value to be set
	 */
	public void setMixer(Mixer m) {
		mixChoice = m;
	}

	/**
	 * Returns the value of the chosen Geometry
	 * 
	 * @return The value of {@code geoChoice} 
	 */
	public GeometryNames getGeometryChoice() {
		return geoChoice;
	}

	/**
	 * Returns the value of the chosen Mixer
	 * 
	 * @return The value of {@code mixChoice} 
	 */
	public Mixer getMixerChoice() {
		return mixChoice;
	}
}
