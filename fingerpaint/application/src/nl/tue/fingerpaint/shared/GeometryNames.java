package nl.tue.fingerpaint.shared;

public class GeometryNames {
	public static final String RECT_SHORT = "RECT";
	public static final String RECT_LONG = "Rectangle 400x240";
	public static final String SQR_SHORT = "SQR";
	public static final String SQR_LONG = "Square";
	public static final String CIRC_SHORT = "CIRC";
	public static final String CIRC_LONG = "Circle";
	public static final String JOBE_SHORT = "JOBE";
	public static final String JOBE_LONG = "Journal Bearing";

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