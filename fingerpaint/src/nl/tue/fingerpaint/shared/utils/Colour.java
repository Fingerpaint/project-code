package nl.tue.fingerpaint.shared.utils;

import com.google.gwt.canvas.dom.client.CssColor;

/**
 * A {@code Colour} is simply ... a colour. It can be used way easier than the
 * default GWT {@link CssColor} though. For example, colours can be compared and
 * the R, G and B values can be queried. Finally, this class provides a way to
 * get a CSS colour representation as a string, just like the {@link CssColor}.
 * 
 * @author Group Fingerpaint
 */
public class Colour {

    /** White colour. */
    public static final Colour WHITE = new Colour(255, 255, 255);
    /** Near-white gray colour. */
    public static final Colour WHITE_GRAYISH = new Colour(240, 240, 240);
    /** Light gray colour. */
    public static final Colour LIGHT_GRAY = new Colour(192, 192, 192);
    /** Gray colour. */
    public static final Colour GRAY = new Colour(128, 128, 128);
    /** Dark gray colour. */
    public static final Colour DARK_GRAY = new Colour(64, 64, 64);
    /** Black colour. */
    public static final Colour BLACK = new Colour(0, 0, 0);
    /** TUe pink colour. */
    public static final Colour TUE_PURPLE = new Colour(214, 0, 123);
    /** TUe light blue colour. */
    public static final Colour TUE_LIGHT_BLUE = new Colour(0, 162, 222);
    /** TUe blue colour. */
    public static final Colour TUE_BLUE = new Colour(0, 102, 204);
    /** TUe dark blue colour. */
    public static final Colour TUE_DARK_BLUE = new Colour(16, 16, 115);
    /** TUe support colour: aqua-ish colour. */
    public static final Colour PMS_GREEN = new Colour(0, 172, 130);
    /** TUe support colour: blue-ish colour. */
    public static final Colour PMS_3135 = new Colour(0, 146, 181);
    /** TUe support colour: green colour. */
    public static final Colour PMS_375 = new Colour(132, 210, 0);
    /** TUe support colour: light green colour. */
    public static final Colour PMS_396 = new Colour(206, 223, 0);
    /** TUe red colour. */
    public static final Colour PMS_WARM_RED = new Colour(247, 49, 49);
	
	/**
	 * The red component of this colour.
	 */
	protected int r;
	/**
	 * The green component of this colour.
	 */
	protected int g;
	/**
	 * The blue component of this colour.
	 */
	protected int b;

	/**
	 * Construct a new colour with the given RGB components.
	 * 
	 * @param r
	 *            The red component for the colour.
	 * @param g
	 *            The green component for the colour.
	 * @param b
	 *            The blue component for the colour.
	 */
	public Colour(int r, int g, int b) {
		setRed(r);
		setGreen(g);
		setBlue(b);
	}

	/**
	 * @return the red component of this colour
	 */
	public int getRed() {
		return r;
	}

	/**
	 * @param r
	 *            the red component of this colour
	 */
	public void setRed(int r) {
		this.r = r;
	}

	/**
	 * @return the green component of this colour
	 */
	public int getGreen() {
		return g;
	}

	/**
	 * @param g
	 *            the green component of this colour
	 */
	public void setGreen(int g) {
		this.g = g;
	}

	/**
	 * @return the blue component of this colour
	 */
	public int getBlue() {
		return b;
	}

	/**
	 * @param b
	 *            the blue component of this colour
	 */
	public void setBlue(int b) {
		this.b = b;
	}

	/**
	 * Return a hexadecimal representation of this colour, that can directly be
	 * used in CSS.
	 * 
	 * @return a hexadecimal string (with lower case letters)
	 */
	public String toHexString() {
		return "#" + pad(Integer.toHexString(r), 2)
				+ pad(Integer.toHexString(g), 2)
				+ pad(Integer.toHexString(b), 2);
	}
	
	/**
	 * Converts the colour to a hexadecimal code.
	 * @return the hexadecimal code for the colour.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + b;
		result = prime * result + g;
		result = prime * result + r;
		return result;
	}

	/**
	 * Compares this Colour to an other object.
	 * @return {@code true} if {@code this} is equal to {@code object},
	 * {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Colour other = (Colour) obj;
		if (b != other.b) {
			return false;
		}
		if (g != other.g) {
			return false;
		}
		if (r != other.r) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the hexadecimal representation of this colour as a String.
	 * @return the hexadecimal representation of this colour as a String.
	 */
	@Override
	public String toString() {
		return toHexString();
	}
	
	/**
	 * Pad string to given length with zeroes.
	 * 
	 * @param str
	 *            String to pad.
	 * @param len
	 *            Wanted length of string.
	 * @return Given string, left-padded with zeroes so it has requested length.
	 */
	protected static String pad(String str, int len) {
		if (str == null) {
			str = "";
		}

		while (str.length() < len) {
			str = "0" + str;
		}
		return str;
	}
}
