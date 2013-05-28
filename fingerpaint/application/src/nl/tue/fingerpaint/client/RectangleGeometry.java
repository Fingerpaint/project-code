package nl.tue.fingerpaint.client;

import com.google.gwt.canvas.dom.client.CanvasPixelArray;
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
	private static final int VERTICAL_CELLS = 240;
	private static final int HORIZONTAL_CELLS = 400;

	
	// ----Constructors-----------------------------------------
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
		return VERTICAL_CELLS;
	}

	/**
	 * Returns the base width of the geometry
	 * 
	 * @return The minimum width of this geometry
	 */
	@Override
	public int getBaseWidth() {
		return HORIZONTAL_CELLS;
	}

	/**
	 * Get the current distribution from the canvas, and puts it in the
	 * distribution.
	 * 
	 * @return The current concentration distribution
	 * 
	 */
	@Override
	public double[] getDistribution() {
		double[] dist = new double[HORIZONTAL_CELLS * VERTICAL_CELLS];
		ImageData img = context.getImageData(X_OFFSET + 1, TOP_OFFSET + 1,
				getWidth(), getHeight());
		CanvasPixelArray data = img.getData();
		int width = img.getWidth();
		int height = img.getHeight();
		int index;
		for (int y = height - factor; y >= 0; y -= factor) {
			for (int x = 0; x < width; x += factor) {
				index = (y * width + x) * 4;
				dist[x / factor + HORIZONTAL_CELLS
						* (VERTICAL_CELLS - 1 - y / factor)] = (double) data
						.get(index) / 255;
			}
		}
		return dist;
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
	 * white (1)
	 * 
	 * @post {@code representationVector} is initialised
	 */
	@Override
	protected void initialiseDistribution() {
		distribution = new double[HORIZONTAL_CELLS * VERTICAL_CELLS];
		for (int i = 0; i < distribution.length; i++) {
			distribution[i] = 1;
		}
	}

	/**
	 * Draws the outline around the walls
	 * 
	 * @post The outline of the walls has been drawn on the {@code canvas}
	 */
	@Override
	protected void drawWalls() {
		// Set the lineWidth to 1 and colour to black
		context.setLineWidth(1);
		context.setStrokeStyle(CssColor.make("black"));

		// Draw the outline of the top wall
		context.beginPath();
		context.moveTo(X_OFFSET + 0.5, TOP_OFFSET + 0.5 - HEIGHT_OF_WALL);
		context.lineTo(X_OFFSET + getWidth() + 1.5, TOP_OFFSET + 0.5
				- HEIGHT_OF_WALL);
		context.lineTo(X_OFFSET + getWidth() + 1.5, TOP_OFFSET + 0.5);
		context.lineTo(X_OFFSET + 0.5, TOP_OFFSET + 0.5);
		context.closePath();
		context.stroke();
		
		// Draw the outline of the bottom wall
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
		
		// Fill the top and bottom walls
		fillWall(0, true);
		fillWall(0, false);
	}

	/**
	 * Colours the inside of one of the walls of the geometry.
	 * 
	 * @param xOffset
	 *            The x-distance to the initial position
	 * @param topWal
	 *            {@code true} if the top wall has to be filled, {@code false}
	 *            otherwise
	 */
	@Override
	protected void fillWall(int xOffset, boolean topWal) {
		// Set the height of the upper border of the wall
		double y = topWal ? TOP_OFFSET + 1 - HEIGHT_OF_WALL : TOP_OFFSET
				+ getHeight() + 2;
		
		// Clip the area inside the wall
		context.beginPath();
		context.moveTo(X_OFFSET + 1, y);
		context.lineTo(X_OFFSET + getWidth() + 1, y);
		context.lineTo(X_OFFSET + getWidth() + 1, y + HEIGHT_OF_WALL - 1);
		context.lineTo(X_OFFSET + 1, y + HEIGHT_OF_WALL - 1);
		context.closePath();
		context.clip();

		// Fill the area inside the wall
		context.setFillStyle(wallColor);
		context.fillRect(X_OFFSET + 1, y, getWidth() + 1, HEIGHT_OF_WALL - 1);

		// Set the stroke style for the arrows (stripes)
		context.setStrokeStyle(wallStripeColor);
		context.setLineWidth(STRIPE_WIDTH);
		
		// Set the initial x and y values for the arrows to the left
		double x = X_OFFSET + 1 + getWidth() / 2.0 - STRIPE_INTERVAL / 2.0
				+ xOffset;
		y = topWal ? TOP_OFFSET + 0.5 - HEIGHT_OF_WALL / 2.0 : TOP_OFFSET
				+ getHeight() + 1.5 + HEIGHT_OF_WALL / 2.0;
		
		// Draw all the arrows to the left
		while (x > X_OFFSET + 0.5) {
			context.beginPath();
			context.moveTo(x, y - HEIGHT_OF_WALL / 2.0);
			context.lineTo(x - STRIPE_SLOPE, y);
			context.lineTo(x, y + HEIGHT_OF_WALL / 2.0);
			context.stroke();
			x -= STRIPE_INTERVAL;
		}

		// Set the initial x value for the arrows to the right (y stays the same)
		x = X_OFFSET + 1 + getWidth() / 2.0 + STRIPE_INTERVAL / 2.0 + xOffset;
		
		// Draw all the arrows to the right
		while (x < X_OFFSET + getWidth()) {
			context.beginPath();
			context.moveTo(x, y - HEIGHT_OF_WALL / 2.0);
			context.lineTo(x + STRIPE_SLOPE, y);
			context.lineTo(x, y + HEIGHT_OF_WALL / 2.0);
			context.stroke();
			x += STRIPE_INTERVAL;
		}
		
		// Restore clipping area to full canvas
		removeClippingArea();
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
	@Override
	protected void clipGeometryOutline() {
		context.beginPath();
		context.moveTo(X_OFFSET + 1, TOP_OFFSET + 1);
		context.lineTo(X_OFFSET + getWidth() + 1, TOP_OFFSET + 1);
		context.lineTo(X_OFFSET + getWidth() + 1, TOP_OFFSET + getHeight() + 1);
		context.lineTo(X_OFFSET + 1, TOP_OFFSET + getHeight() + 1);
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
		boolean topWall = topWallStep;
		boolean toTheLeft = false;

		if (diffX < -SWIPE_THRESHOLD) { // To the left
			toTheLeft = true;
		} else if (diffX > SWIPE_THRESHOLD) { // To the right
			toTheLeft = false;
		} else { // No movement of the geometry was specified
			return null;
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
//		for (int i = 0; i < dist.length; i++) {
//			Point coords = new Point(i % 400, 239 - i / 400);
//			fillPixel(changeToAbsoluteCoords((int) coords.getX()),
//					changeToAbsoluteCoords((int) coords.getY()),
//					getColour(dist[i]));
//		}
		ImageData img = context.getImageData(X_OFFSET + 1, TOP_OFFSET + 1,
				getWidth(), getHeight());
		CanvasPixelArray data = img.getData();
		int width = getWidth();
		int l = dist.length;
		int x, y, col, index, sw, sh, w2, h2;
		
		for (int i = 0; i < l; i++) {
			x = i % 400;
			y = 239 - i / 400;
			col = (int) (dist[i] * 255);
			sw = x * factor;
			sh = y * factor;
			w2 = (x + 1) * factor;
			h2 = (y + 1) * factor;
			for (int w = sw; w < w2; w++) {
				for (int h = sh; h < h2; h++) {
					index = (h * width + w) * 4;
					data.set(index, col);
					data.set(++index, col);
					data.set(++index, col);
				}
			}
		}
		context.putImageData(img, X_OFFSET + 1, TOP_OFFSET + 1);
	}

	/**
	 * Resets the current distribution to all white. Equivalent to calling
	 * drawDistribution with a dist parameter containing '1' at all indices (but
	 * faster)
	 */
	@Override
	public void resetDistribution() {
		context.setFillStyle(CssColor.make("white"));
		context.fillRect(X_OFFSET + 1, TOP_OFFSET + 1, getWidth(), getHeight());
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
//>>>>>>> ffff3624f9ce63f36e29b5879d9a44fee78de850
