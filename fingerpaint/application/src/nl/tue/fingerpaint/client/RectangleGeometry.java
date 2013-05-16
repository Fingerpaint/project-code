package nl.tue.fingerpaint.client;

import nl.tue.fingerpaint.client.Movement.HorizontalMovement;
import nl.tue.fingerpaint.client.Movement.VerticalMovement;
import com.google.gwt.canvas.dom.client.CssColor;

/**
 * Class that represents the rectangular geometry. Keeps the internal
 * representation in a vector. Also contains the canvas and methods to draw on
 * it.
 * 
 * @author Tessa Belder
 */
public class RectangleGeometry extends Geometry {

	/*
	 * The parameters of the canvas
	 */
	private final int rectangleHeight = 240;
	private final int rectangleWidth = 400;
	private int factor;

	/**
	 * Threshold in pixels to decide when a large enough swipe has been carried
	 * out.
	 */
	private final static int SWIPE_THRESHOLD = 20;

	/**
	 * The height of the wall in the same distance unit as the
	 * {@code rectangleHeight}.
	 */
	private final static int HEIGHT_OF_WALL = 20;

	// ----Contructor-----------------------------------------------
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
	 * Returns the total height of the drawing area
	 * 
	 * @return total height of the drawing area
	 */
	public int getHeight() {
		return factor * rectangleHeight;
	}

	/**
	 * Returns the total width of the drawing area
	 * 
	 * @return total width of the drawing area
	 */
	public int getWidth() {
		return factor * rectangleWidth;
	}

	// ----Implemented abstract methods from superclass----------------

	/**
	 * Fills a single pixel of the canvas with the current color. Also updates
	 * internal representation vector accordingly.
	 * 
	 * @pre 0 <= {@code x} < 400
	 * @pre 0 <= {@code y} < 240
	 * 
	 * @param x
	 *            x-coordinate, relative to the canvas element, of the pixel to
	 *            be filled
	 * @param y
	 *            y-coordinate, relative to the canvas element, of the pixel to
	 *            be filled
	 */
	@Override
	public void fillPixel(int x, int y) {
		if (isInside(x, y)) {

			// Substract 1 from both x and y coordinates, because
			// the drawing area starts at (1, 1) instead of (0, 0).
			// Divide by multiplying factor (int division so result is
			// rounded down) to find base coordinates.
			x = (x - 1) / factor;
			y = (y - 1) / factor;

			// Fill a rectangle with the currentColor. Multiply base coordinates
			// with the multiplying factor and add 1 to find upper left corner
			// of
			// the 'pixel'.
			// Make the 'pixel' the size of the multiplying factor.
			context.setFillStyle(getColor());
			context.fillRect(x * factor + 1, y * factor + 1, factor, factor);

			// Set internal represention of base coordinates to 1 for
			// black, and to 0 for white. 239 - y has to be used since
			// withing the canvas, coordinate (0, 0) is upper left, and in
			// the internal representation (0, 0) is bottom left.
			if (getColor().value().equals("black")) {
				representationVector[x + 400 * (239 - y)] = 1;
			} else {
				representationVector[x + 400 * (239 - y)] = 0;
			}
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
	 * Creates vector (array) of length 240 * 400. Initialises color to all
	 * white (0)
	 * 
	 * @post {@code representationVector} is initialised
	 */
	@Override
	protected void initialiseRepresentation() {
		representationVector = new double[96000];
		for (int i = 0; i < 96000; i++) {
			representationVector[i] = 0;
		}
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
	 * Checks whether a new {@code Step} should be added. If the MouseEvent's
	 * coordinates are near the top of the rectangular geometry, a {@code TOP}
	 * mixing step is generated; if it is near the bottom, a {@code BOTTOM} is
	 * generated. The direction of movement decides whether the movement is to
	 * the left or to the right.
	 */
	@Override
	protected void stopDefineMixingStep(int mouseX, int mouseY) {
		int diffX = mouseX - swipeStartX;

		Movement movement = new Movement();

		if (0 < mouseY && mouseY < HEIGHT_OF_WALL * factor) {
			movement.setVertical(VerticalMovement.UP);
		} else if ((rectangleHeight - HEIGHT_OF_WALL) * factor < mouseY
				&& mouseY < rectangleHeight * factor) {
			movement.setVertical(VerticalMovement.DOWN);
		} else { // No movement of the geometry was specified
			return;
		}

		if (diffX < -SWIPE_THRESHOLD) {
			movement.setHorizontal(HorizontalMovement.LEFT);
		} else if (diffX > SWIPE_THRESHOLD) {
			movement.setHorizontal(HorizontalMovement.RIGHT);
		}

		Step mixingStep = new Step(1, movement); // TODO: Get value from spinner

		protocol.addStep(mixingStep);
	}

}
