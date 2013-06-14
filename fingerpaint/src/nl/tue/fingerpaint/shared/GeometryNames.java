package nl.tue.fingerpaint.shared;

/**
 * <p>
 * A class that contains the geometry names for all the different geometries
 * that are used in the Fingerpaint application.
 * </p>
 * <p>
 * The long names are displayed in the cell browser and are used to save the
 * choice for the selected geometry internally. The short version of the
 * geometry names serve as keys for the class {@code StorageManager}.
 * </p>
 * 
 * @author Group Fingerpaint
 */
public class GeometryNames {
	/** The long name for the rectangular geometry. */
	public static final String RECT = "Rectangle 400x240";

	/** The long name for the square geometry. */
	public static final String SQR = "Square";
	
	/** The long name for the circular geometry. */
	public static final String CIRC = "Circle";
	
	/** The long name for the 'Journal Bearing' geometry. */
	public static final String JOBE = "Journal Bearing";
}