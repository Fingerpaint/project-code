package nl.tue.fingerpaint.client.model.drawingtool;

import nl.tue.fingerpaint.shared.utils.Colour;

import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.ImageData;

/**
 * A {@code DrawingTool} is a shape that a user can use to draw on the canvas.
 * When clicking on the canvas, the shape represented by a {@code DrawingTool}
 * is put on the canvas where the user clicked. In the same manner, shapes are
 * put on the canvas while the user drags the mouse or finger over the canvas.
 * 
 * @author Group Fingerpaint
 */
public abstract class DrawingTool {

	/** The radius of the drawing tool. */
	protected int radius;

	// --Constructors-------------------------------------------
	/**
	 * Constructs a drawing tool with radius {@code r}.
	 * 
	 * @param radius
	 *            The radius of the drawing tool
	 */
	public DrawingTool(int radius) {
		this.radius = radius;
	}

	// --Getters and Setters----------------------------------------

	/**
	 * Gives the radius of this drawing tool.
	 * 
	 * @return The radius of the drawing tool
	 */
	public int getRadius() {
		return this.radius;
	}

	/**
	 * Change the radius of this drawing tool.
	 * 
	 * @param r
	 *            The new radius of the drawing tool
	 */
	public void setRadius(int r) {
		radius = r;
	}

	// --Private and protected methods for internal use---------------
	/**
	 * Fills all the indices corresponding to a pixel in the canvaspixelarray.
	 * This is a convenience method to write grey values to the canvas. Normally,
	 * you have to write the {@code RGB} values separately to the array, but for
	 * grey values {@code R == G == B} of course. This method handles that.
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
	abstract public ImageData getTool(ImageData img, Colour colour);

}
