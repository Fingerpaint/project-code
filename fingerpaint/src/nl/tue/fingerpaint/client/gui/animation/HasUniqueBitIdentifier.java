package nl.tue.fingerpaint.client.gui.animation;

/**
 * <p>
 * This interface should be implemented by enumerations that all have an
 * identifier that is unique in such a way that the bit is unique.
 * </p>
 * 
 * <p>
 * Using this unique ID thus allows for logical {@code or} and {@code and} to
 * combine members from the identifier.
 * </p>
 * 
 * <p>
 * <b>Example:</b> suppose we have an {@code enum} {@code Units} that implements this
 * interface and has members {@code UNITA}, {@code UNITB} and {@code UNITC}. We
 * could then do:
 * <pre>
 *   // Combine members
 *   int combineUnitsHere = Units.UNITA | Units.UNITC;
 *   
 *   ...
 *   
 *   // Check if a variable contains certain units
 *   if ((combineUnitsHere & Units.UNITB) > 0) {
 *     // this is not the case
 *   } else if ((combineUnitsHere & Units.UNITC) > 0) {
 *     // this is the case
 *   }
 * </pre>
 * </p>
 * 
 * @author Group Fingerpaint
 */
public interface HasUniqueBitIdentifier {

	/**
	 * Return a unique identifier.
	 * 
	 * @return an ID that is unique up to the bit
	 * @see HasUniqueBitIdentifier
	 */
	public int getId();

}
