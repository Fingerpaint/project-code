package nl.tue.fingerpaint.shared;

import nl.tue.fingerpaint.client.storage.StorageManager;

/**
 * <p>
 * A class that contains the geometry names for all the different geometries
 * that are used in the Fingerpaint application.
 * </p>
 * <p>
 * The long names are displayed in the cell browser and are used to save the
 * choice for the selected geometry internally. The short version of the
 * geometry names serve as keys for the class {@link StorageManager}.
 * </p>
 * 
 * @author Group Fingerpaint
 */
public class GeometryNames {
	/** The short name for the rectangular geometry. */
	public static final String RECT_SHORT = "RECT";
	/** The long name for the rectangular geometry. */
	public static final String RECT_LONG = "Rectangle 400x240";

	/** The short name for the square geometry. */
	public static final String SQR_SHORT = "SQR";
	/** The long name for the square geometry. */
	public static final String SQR_LONG = "Square";
	
	/** The short name for the circular geometry. */
	public static final String CIRC_SHORT = "CIRC";
	/** The long name for the circular geometry. */
	public static final String CIRC_LONG = "Circle";
	
	/** The short name for the 'Journal Bearing' geometry. */
	public static final String JOBE_SHORT = "JOBE";
	/** The long name for the 'Journal Bearing' geometry. */
	public static final String JOBE_LONG = "Journal Bearing";

	/**
	 * Returns the short name for the long name of the geometry {@code longName}.
	 * 
	 * @param longName The long name of a geometry.
	 * @return The short name for the long name of the geometry {@code longName}, if the
	 * long name is known; null is returned otherwise.
	 */
	public static String getShortName(String longName) {
		if (longName.equals(RECT_LONG)) {
			return RECT_SHORT;
		} else if (longName.equals(SQR_LONG)) {
			return SQR_SHORT;
		} else if (longName.equals(CIRC_LONG)) {
			return CIRC_SHORT;
		} else if (longName.equals(JOBE_LONG)) {
			return JOBE_SHORT;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the long name for the short name of the geometry {@code shortName}.
	 * 
	 * @param shortName The short name of a geometry.
	 * @return The long name for the short name of the geometry {@code shortName}, if the
	 * short name is known; null is returned otherwise.
	 */
	public static String getLongName(String shortName) {
		if (shortName.equals(RECT_SHORT)) {
			return RECT_LONG;
		} else if (shortName.equals(SQR_SHORT)) {
			return SQR_LONG;
		} else if (shortName.equals(CIRC_SHORT)) {
			return CIRC_LONG;
		} else if (shortName.equals(JOBE_SHORT)) {
			return JOBE_LONG;
		} else {
			return null;
		}
	}
}