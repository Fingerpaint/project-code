package nl.tue.fingerpaint.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.touch.client.Point;

/**
 * Class that represents the rectangular geometry. Keeps the internal
 * representation in a vector. Also contains the canvas and methods to draw on
 * it.
 * 
 * @author Project Fingerpaint
 */
public class RectangleGeometry extends Geometry {

	/*
	 * The parameters of the canvas
	 */
	private final int rectangleHeight = 240;
	private final int rectangleWidth = 400;

	// ----Constructor-----------------------------------------------
	/**
	 * Uses constructor from super class (Geometry.java)
	 * 
	 * @param clientHeight
	 *            The height of the window in which the application is displayed
	 * @param clientWidth
	 *            The width of the window in which the application is displayed
	 */
	public RectangleGeometry(int clientHeight, int clientWidth) {
		super(clientHeight, clientWidth);
	}

	// ----Getters and Setters-----------------------------------------
	/**
	 * Returns the base height of the geometry
	 * 
	 * @return The minimum height of this geometry
	 */
	@Override
	public int getBaseHeight() {
		return this.rectangleHeight;
	}

	/**
	 * Returns the base width of the geometry
	 * 
	 * @return The minimum width of this geometry
	 */
	@Override
	public int getBaseWidth() {
		return this.rectangleWidth;
	}

	/**
	 * Sets the factor to multiply the outline of the geometry with.
	 * 
	 * @post The multiply factor has been set to @param{factor}
	 */
	@Override
	public void setFactor(int factor) {
		this.factor = factor;
	}

	/**
	 * Sets the distribution belonging to this geometry to the dist parameter
	 * 
	 * @param dist
	 *            The distribution the set
	 */
	public void setDistribution(double[] dist) {
		setDistribution(new RectangleDistribution(dist));
	}

	/**
	 * Get the current distribution from the canvas, and puts it in the
	 * distribution.
	 * 
	 * @return The current concentration distribution
	 * 
	 */
	@Override
	public Distribution getDistribution() {
		this.distribution.setDistribution(
				context.getImageData(1, 1, getWidth(), getHeight()),
				getBaseWidth());
		return this.distribution;
	}

	// ----Implemented abstract methods from superclass----------------

	/**
	 * Fills a single pixel of the canvas with the current colour. Also updates
	 * internal representation vector accordingly.
	 * 
	 * <pre>
	 * 0 <=
	 * {@code x} < 400
	 * 
	 * <pre>
	 * 0 <= {@code y} < 240
	 * 
	 * @param x
	 *            x-coordinate, relative to the canvas element, of the pixel to
	 *            be filled
	 * @param y
	 *            y-coordinate, relative to the canvas element, of the pixel to
	 * be filled
	 * @param colour The colour to fill the pixel with
	 */
	@Override
	public void fillPixel(int x, int y, CssColor colour) {
		if (isInside(x, y)) {
			// Fill a rectangle with the currentColor. Change to valid
			// coordinates to find upper left corner of the 'pixel'.
			// Make the 'pixel' the size of the multiplying factor.
			context.setFillStyle(colour);
			context.fillRect(getValidCoord(x), getValidCoord(y), factor, factor);
		}
	}

	/**
	 * Returns whether the position ({@code x}, {@code y}) is inside the drawing
	 * area on the {@code canvas}. Note: The border around the drawing area
	 * counts as 'outside'.
	 * 
	 * @return True if coordinates (x, y) are inside the rectangular shaped
	 *         drawing area. False otherwise.
	 */
	@Override
	public boolean isInside(int x, int y) {
		return x > 0 && y > 0 && x < getWidth() + 1 && y < getHeight() + 1;
	}

	/**
	 * Creates vector (array) of length 240 * 400. Initialises colour to all
	 * white (0)
	 * 
	 * @post {@code representationVector} is initialised
	 */
	@Override
	protected void initialiseDistribution() {
		distribution = new RectangleDistribution();
	}

	/**
	 * Draws a rectangle on the canvas, starting at the upper left corner, with
	 * the total height and width of the drawing area.
	 * 
	 * Note: getWidth() + 2 and getHeight() + 2 are used because for some reason
	 * the right and bottom borders of the rectangle are drawn two pixels wide
	 * instead of just one.
	 * 
	 * @post The outline of the rectangle has been drawn on the {@code canvas}
	 */
	@Override
	protected void drawGeometryOutline() {
		context.setLineWidth(1);
		context.setStrokeStyle(CssColor.make("black"));

		context.beginPath();
		context.moveTo(0, 0);
		context.lineTo(getWidth() + 2, 0);
		context.lineTo(getWidth() + 2, getHeight() + 2);
		context.lineTo(0, getHeight() + 2);
		context.closePath();
		context.stroke();
	}

	/**
	 * Sets the given distribution as the current distribution, and draws it on
	 * the canvas
	 * 
	 * <pre>
	 * {@code dist.length} == 96000
	 * 
	 * @param dist
	 * The distribution to be set and drawn
	 */
	@Override
	public void drawDistribution(double[] dist) {
		for (int i = 0; i < dist.length; i++) {
			Point coords = distribution.getCoordinates(i);
			fillPixel(changeToAbsoluteCoords((int) coords.getX()),
					changeToAbsoluteCoords((int) coords.getY()),
					getColour(dist[i]));
		}
	}

	/**
	 * Converts the input value to an absolute coordinate on the canvas
	 * 
	 * @param x
	 *            The value to be converted
	 * @return The location relative to the top-left corner of the canvas,
	 *         corresponding to this value
	 */
	private int changeToAbsoluteCoords(int x) {
		return x * factor + 1;
	}

}
