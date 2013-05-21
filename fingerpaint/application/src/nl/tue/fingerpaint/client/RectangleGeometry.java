package nl.tue.fingerpaint.client;

import com.google.gwt.canvas.dom.client.CssColor;

import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.touch.client.Point;

/**
 * Class that represents the rectangular geometry. Keeps the internal
 * representation in a vector. Also contains the canvas and methods to draw on
 * it.
 * 
 * @author Project Fingerpaint
 */
public class RectangleGeometry extends Geometry {

	/**
	 * The parameters of the canvas
	 */
	private final int rectangleHeight = 240;
	private final int rectangleWidth = 400;

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
		ImageData img = context.getImageData(X_OFFSET + 1, TOP_OFFSET + 1,
				getWidth(), getHeight());
		this.distribution.setDistribution(img, factor);

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
		if (isInsideDrawingArea(x, y)) {
			// Fill a rectangle with the currentColor. Change to valid
			// coordinates to find upper left corner of the 'pixel'.
			// Make the 'pixel' the size of the multiplying factor.
			context.setFillStyle(colour);
			context.fillRect(getValidCoord(x) + X_OFFSET, getValidCoord(y)
					+ TOP_OFFSET, factor, factor);
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
	public boolean isInsideDrawingArea(int x, int y) {
		return x > 0 && y > 0 && x < getWidth() + 1 && y < getHeight() + 1;
	}

	/**
	 * Returns whether the position ({@code x}, {@code y}) is inside the top
	 * wall on the {@code canvas}.
	 * 
	 * @param x
	 *            The x-coordinate relative to the top-left corner of the
	 *            {@code canvas}
	 * @param y
	 *            The y-coordinate relative to the top-left corner of the
	 *            {@code canvas}
	 * 
	 * @return {@code true} if coordinates (x, y) are inside the top wall.
	 *         {@code false} otherwise.
	 */
	@Override
	protected boolean isInsideTopWall(int x, int y) {
		return (x > X_OFFSET && x < X_OFFSET + getWidth()
				&& y > TOP_OFFSET - HEIGHT_OF_WALL && y < TOP_OFFSET);
	}

	/**
	 * Returns whether the position ({@code x}, {@code y}) is inside the bottom
	 * wall on the {@code canvas}.
	 * 
	 * @param x
	 *            The x-coordinate relative to the top-left corner of the
	 *            {@code canvas}
	 * @param y
	 *            The y-coordinate relative to the top-left corner of the
	 *            {@code canvas}
	 * 
	 * @return {@code true} if coordinates (x, y) are inside the bottom wall.
	 *         {@code false} otherwise.
	 */
	@Override
	protected boolean isInsideBottomWall(int x, int y) {
		return (x > X_OFFSET && x < X_OFFSET + getWidth()
				&& y > TOP_OFFSET + getHeight() && y < TOP_OFFSET + getHeight()
				+ HEIGHT_OF_WALL);
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
	 * Draws the outline around the walls
	 * 
	 * @post The outline of the walls has been drawn on the {@code canvas}
	 */
	@Override
	protected void drawWalls() {
		context.setLineWidth(1);
		context.setStrokeStyle(CssColor.make("black"));

		context.beginPath();
		context.moveTo(X_OFFSET + 0.5, TOP_OFFSET + 0.5 - HEIGHT_OF_WALL);
		context.lineTo(X_OFFSET + getWidth() + 1.5, TOP_OFFSET + 0.5
				- HEIGHT_OF_WALL);
		context.lineTo(X_OFFSET + getWidth() + 1.5, TOP_OFFSET + 0.5);
		context.lineTo(X_OFFSET + 0.5, TOP_OFFSET + 0.5);
		context.closePath();
		context.stroke();

		context.beginPath();
		context.moveTo(X_OFFSET + 0.5, TOP_OFFSET + getHeight() + 1.5);
		context.lineTo(X_OFFSET + getWidth() + 1.5, TOP_OFFSET + getHeight()
				+ 1.5);
		context.lineTo(X_OFFSET + getWidth() + 1.5, TOP_OFFSET + getHeight()
				+ 1.5 + HEIGHT_OF_WALL);
		context.lineTo(X_OFFSET + 0.5, TOP_OFFSET + getHeight() + 1.5
				+ HEIGHT_OF_WALL);
		context.closePath();
		context.stroke();
	}

	/**
	 * Draws a rectangle on the canvas, starting at the upper left corner, with
	 * the total height and width of the drawing area.
	 * 
	 * Note: The 0.5 and 1.5 coordinates are necessary to get solid black lines.
	 * If you just use 0 and 1, the lines appear gray instead of black, and the
	 * right and bottom lines are two pixels wide instead of 1.
	 * 
	 * @post The outline of the rectangle has been drawn on the {@code canvas}
	 */
	@Override
	protected void drawGeometryOutline() {
		context.setLineWidth(1);
		context.setStrokeStyle(CssColor.make("black"));

		context.beginPath();
		context.moveTo(X_OFFSET + 0.5, TOP_OFFSET + 0.5);
		context.lineTo(X_OFFSET + getWidth() + 1.5, TOP_OFFSET + 0.5);
		context.lineTo(X_OFFSET + getWidth() + 1.5, TOP_OFFSET + getHeight()
				+ 1.5);
		context.lineTo(X_OFFSET + 0.5, TOP_OFFSET + getHeight() + 1.5);
		context.closePath();
		context.stroke();

		context.setFillStyle(CssColor.make("white"));
		context.fillRect(X_OFFSET + 1, TOP_OFFSET + 1, getWidth(), getHeight());
	}

	/**
	 * Clips the drawing area, so that drawing outside of the drawing area is
	 * not possible
	 * 
	 * @post The outline of the geometry has been clipped
	 */
	protected void clipGeometryOutline() {
		context.beginPath();
		context.moveTo(X_OFFSET + 0, TOP_OFFSET + 0);
		context.lineTo(X_OFFSET + getWidth() + 1, TOP_OFFSET + 0);
		context.lineTo(X_OFFSET + getWidth() + 1, TOP_OFFSET + getHeight() + 1);
		context.lineTo(X_OFFSET + 0, TOP_OFFSET + getHeight() + 1);
		context.closePath();
		context.clip();
	}

	/**
	 * Checks whether a new {@code Step} should be added. If the MouseEvent's
	 * coordinates are near the top of the rectangular geometry, a {@code TOP}
	 * mixing step is generated; if it is near the bottom, a {@code BOTTOM} is
	 * generated. The direction of movement decides whether the movement is to
	 * the left or to the right.
	 */
	@Override
	protected void stopDefineMixingStep(int mouseX, int mouseY) {
		MixingStep movement = determineSwipe(mouseX, mouseY);

		int stepSize = 1; // TODO: Get value from spinner

		if (movement != null) {
			for (StepAddedListener l : stepAddedListeners) {
				l.onStepAdded(movement);
			}
		}
	}

	/**
	 * Returns the direction and wall of the current swiping movement, returns
	 * null if the swipe is not a valid swipe.
	 * 
	 * Additionally draws an arrow to indicate the direction of the current
	 * swipe
	 * 
	 * @param mouseX
	 *            The x-coordinate of the current mouse position.
	 * @param mouseY
	 *            The y-coordinate of the current mouse position.
	 */
	@Override
	protected MixingStep determineSwipe(int mouseX, int mouseY) {
		int diffX = mouseX - swipeStartX;
		boolean topWall = false;
		boolean toTheLeft = false;

		// used to be: 0 < mouseY && mouseY < HEIGHT_OF_WALL * factor
		if (isInsideTopWall(mouseX, mouseY)) { // Top wall
			topWall = true;
			// used to be: (rectangleHeight - HEIGHT_OF_WALL) * factor < mouseY
			// && mouseY < rectangleHeight * factor
		} else if (isInsideBottomWall(mouseX, mouseY)) { // Bottom wall
			topWall = false;
		} else { // No movement of the geometry was specified
			return null;
		}

		if (diffX < -SWIPE_THRESHOLD) { // To the left
			toTheLeft = true;
		} else if (diffX > SWIPE_THRESHOLD) { // To the right
			toTheLeft = false;
		} else { // No movement of the geometry was specified
			return null;
		}

		// draw an arrow corresponding to the swipe
		if (diffX > 0) {
			// the left side of the image should be at the starting location
			int imageLeft = swipeStartX;
			// the top is moved upward to center the picture around the starting
			// location, picture size is 100
			int imageTop = swipeStartY - 50;
			drawImage("rightarrow", imageLeft, imageTop);
		} else {
			// the right side of the image should be at the starting location,
			// picture size is 100
			int imageLeft = swipeStartX - 100;
			// the top is moved upward to center the picture around the starting
			// location, picture size is 100
			int imageTop = swipeStartY - 50;
			drawImage("leftarrow", imageLeft, imageTop);
		}

		// converting the toTheLeft boolean to clockwise representation
		boolean clockwise;
		if (topWall) {
			clockwise = !toTheLeft;
		} else {
			clockwise = toTheLeft;
		}
		MixingStep movement = new MixingStep(1, clockwise, topWall);
		return movement;
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
