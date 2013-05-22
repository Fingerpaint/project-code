package nl.tue.fingerpaint.client;

/**
 * Enum class that represents all Geometries that can be used.
 * 
 * @author Group Fingerpaint
 */
public enum GeometryNames {
	RECTANGLE, CIRCLE, SQUARE, JOURNALBEARING;// New Geometries can be added
												// here
	/**
	 * Overrides the default toString method. Result starts with capital letter,
	 * and the remainder of the string is lowerCase. 
	 */
	@Override
	public String toString() {
		String str = super.toString();
		switch(this){
		case RECTANGLE : 
			return toUpperCaseFirst(str);
		case CIRCLE:
			return toUpperCaseFirst(str);
		case JOURNALBEARING:
			return toUpperCaseFirst(str.substring(0, 7)) + " " +toUpperCaseFirst(str.substring(7, 14)); 
		case SQUARE:
			return toUpperCaseFirst(str);
		default:
			return "GeometryNames toString() Failure";
	}}
	
	
	/**
	 * Returns {@code s} where the first character starts with a capital letter, and the remainder of the string is lowerCase
	 * 
	 * <pre> s.length() >= 2
	 * @param s String to be converted
	 * @return s, where the first character starts with a capital letter, and the remainder of the string is lowerCase
	 */
	private String toUpperCaseFirst(String s){
		assert s.length() >= 2;
		return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
	}
};
