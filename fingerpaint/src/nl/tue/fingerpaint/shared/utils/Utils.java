package nl.tue.fingerpaint.shared.utils;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

/**
 * This class contains a number of static utility functions.
 * 
 * @author Group Fingerpaint
 */
public class Utils {

	/**
	 * Parse an integer from a 'Xpx' string, where 'X' is a number.
	 * 
	 * @param css
	 *            The above-described string.
	 * @return The integer from above string, or '-1' on error.
	 */
	public static int parseIntFromCss(String css) {
		RegExp pattern = RegExp.compile("([0-9]+)px");
		MatchResult matcher = pattern.exec(css);
		if (matcher != null) {
			return Integer.parseInt(matcher.getGroup(1));
		}
		return -1;
	}
	
}
