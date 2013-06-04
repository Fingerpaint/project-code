package nl.tue.fingerpaint.client.gui.drawingtool;

import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.ImageData;

/**
 * A class containing a drawing tool
 * 
 * @author Group Fingerpaint
 */
public abstract class DrawingTool {

	/** The radius of the drawing tool */
	protected int radius;

	// --Constructors-------------------------------------------
	/**
	 * Constructs a drawing tool with radius r
	 * 
	 * @param radius
	 *            The radius of the drawing tool
	 */
	public DrawingTool(int radius) {
		this.radius = radius;
	}

	// --Getters and Setters----------------------------------------

	/**
	 * Gives the radius of this drawing tool
	 * 
	 * @return The radius of the drawing tool
	 */
	public int getRadius() {
		return this.radius;
	}

	/**
	 * Constructs a circle drawing tool with radius r
	 * 
	 * @param r
	 *            The radius of the drawing tool
	 */
	public void setRadius(int r) {
		radius = r;
	}

	// --Private and protected methods for internal use---------------
	/**
	 * Fills all the indices corresponding to a pixel in the canvaspixelarray
	 * 
	 * @param data
	 *            The array to put the values in
	 * @param index
	 *            The first index to change in the array
	 * @param col
	 *            The colour code to put in the array
	 * @param alpha
	 *            The alpha value to put in the array
	 */
	protected void fillPixel(CanvasPixelArray data, int index, int col,
			int alpha) {
		data.set(index, col);
		data.set(++index, col);
		data.set(++index, col);
		data.set(++index, alpha);
	}

	// --Public methods for general use----------------------------------
	/**
	 * Returns an ImageData object representing the drawing tool
	 * 
	 * @param img
	 *            The ImageData object to draw the drawing tool on
	 * @param colour
	 *            The colour to create the drawing tool with
	 * 
	 * @return An ImageData object representing this drawing tool, with colour
	 *         {@code colour}
	 */
	abstract public ImageData getTool(ImageData img, CssColor colour);

}
