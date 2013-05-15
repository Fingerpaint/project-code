package nl.tue.fingerpaint.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class Geometry {

	/*
	 * Internal representation of the geometry
	 */
	protected int[] representationVector;

	/*
	 * The canvas and its parameters
	 */
	protected Canvas canvas;
	protected Context2d context;

	/*
	 * Reference to the current MouseMoveHandler attached to the canvas
	 */
	private HandlerRegistration mouseMove;

	/*
	 * Parameters to handle the drawing
	 */
	private int previousX;
	private int previousY;
	private boolean dragging;
	protected CssColor currentColor;

	// ----Contructor-----------------------------------------------
	/*
	 * Creates a new Geometry, initial color is black.
	 * 
	 * @param clientHeight The height of the window in which the application is
	 * displayed
	 * 
	 * @param clientWidth The width of the window in which the application is
	 * displayed
	 */
	public Geometry(int clientHeight, int clientWidth) {
		initialiseRepresentation();

		setFactor(Math.max(
				1,
				(Math.min(clientHeight / getBaseHeight(), clientWidth
						/ getBaseWidth()))));
		createCanvas(clientHeight, clientWidth);
		drawGeometryOutline();

		// Initialize drawing color to black
		setColor(CssColor.make("black"));
	}

	// ----Getters and Setters---------------------------------------
	/*
	 * Returns the canvas belonging to this Geometry
	 * 
	 * @return The canvas belonging to this Geometry
	 */
	public Canvas getCanvas() {
		return this.canvas;
	}

	/*
	 * Returns the current drawing color
	 * 
	 * @return The current color which is used for drawing
	 */
	public CssColor getColor() {
		return this.currentColor;
	}

	/*
	 * Sets the current drawing color
	 * 
	 * @param color The color which becomes the current drawing color
	 * 
	 * @post The current drawing color is set to @param{color}
	 */
	public void setColor(CssColor color) {
		this.currentColor = color;
	}

	/*
	 * Returns the representation vector
	 * 
	 * @return The representation vector of this geometry
	 */
	public int[] getRepresentationVector() {
		return this.representationVector;
	}

	/*
	 * Returns the base height of the geometry
	 * 
	 * @Return The minimum height of this geometry
	 */
	abstract public int getBaseHeight();

	/*
	 * Returns the base width of the geometry
	 * 
	 * @Return The minimum width of this geometry
	 */
	abstract public int getBaseWidth();

	/*
	 * Sets the factor to multiply the outline of the geometry with.
	 * 
	 * @post The multiply factor has been set to @param{factor}
	 */
	abstract public void setFactor(int factor);

	// ----Private methods for initialization and drawing------------

	/*
	 * Initialization method which creates the canvas. Also initialises and adds
	 * MouseHandlers to the canvas. If the browser doesn't support HTML5 canvas,
	 * adds a message stating this to the RootPanel and returns.
	 */
	protected void createCanvas(int height, int width) {
		// Create the canvas
		canvas = Canvas.createIfSupported();

		// If canvas wan't created, add a label to the Rootpanel stating this
		// and return.
		if (canvas == null) {
			RootPanel
					.get()
					.add(new Label(
							"Sorry, your browser doesn't support the HTML5 Canvas element"));
			return;
		}

		// Initialize canvas
		canvas.setStyleName("paintCanvas");
		canvas.setWidth(width + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.setHeight(height + "px");
		canvas.setCoordinateSpaceHeight(height);

		// Get context of canvas, to use for painting
		context = canvas.getContext2d();

		// When the left mouse button is pressed, drawing is started
		canvas.addMouseDownHandler(new MouseDownHandler() {

			/*
			 * Start drawing if the mouseEvent took place within the drawing
			 * area.
			 */
			@Override
			public void onMouseDown(MouseDownEvent event) {
				Element elem = event.getRelativeElement();
				int x = event.getRelativeX(elem);
				int y = event.getRelativeY(elem);
				if (isInside(x, y)) {
					dragging = true;
					previousX = x;
					previousY = y;
					fillPixel(previousX, previousY);
					mouseMove = canvas
							.addMouseMoveHandler(new MouseMoveHandler() {
								/*
								 * Draw a line from the previous to the next
								 * point of the mouseEvent took place within the
								 * drawing area. Otherwise remove
								 * MouseMoveHandler.
								 */
								@Override
								public void onMouseMove(MouseMoveEvent e) {
									Element elem = e.getRelativeElement();
									int x = e.getRelativeX(elem);
									int y = e.getRelativeY(elem);
									if (isInside(x, y)) {
										drawLine(previousX, previousY, x, y);
										previousX = x;
										previousY = y;
									} else {
										removeMouseMoveHandler();
									}
								}
							});
				}
			}
		});

		canvas.addMouseUpHandler(new MouseUpHandler() {

			/*
			 * When the left mouse button is released, drawing is ended
			 */
			@Override
			public void onMouseUp(MouseUpEvent event) {
				removeMouseMoveHandler();

			}

		});

		/*
		 * When the mouse leaves the canvas area, drawing is ended
		 */
		canvas.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				removeMouseMoveHandler();
				if (dragging) {
					dragging = false;
					mouseMove.removeHandler();
				}
			}
		});

	}

	/*
	 * Removes the mouseMoveHandler from the canvas, if it is present
	 */
	private void removeMouseMoveHandler() {
		if (dragging) {
			dragging = false;
			mouseMove.removeHandler();
		}
	}

	/*
	 * Initialises the internal representation of the drawing area
	 */
	abstract protected void initialiseRepresentation();

	/*
	 * Draws the border around the drawing area
	 */
	abstract protected void drawGeometryOutline();

	/*
	 * Colors the pixel(s) with coordinates (x, y). Also updates the internal
	 * represenation accordingly.
	 */
	abstract protected void fillPixel(int x, int y);

	/*
	 * @return True if coordinates (x, y) are inside the drawing area. False
	 * otherwise.
	 */
	abstract protected boolean isInside(int x, int y);

	/*
	 * Draws a line manually, filling a single pixel at a time. Can't use the
	 * standard 'drawLine()' method for this purpose, since this makes it
	 * impossible to update internal representation.
	 * 
	 * Algorithm obtained from the internet. Source:
	 * http://tech-algorithm.com/articles
	 * /drawing-line-using-bresenham-algorithm/
	 * 
	 * @param newX x-coordinate, relative to the canvas element, of the endpoint
	 * of the line
	 * 
	 * @param newY y-coordinate, relative to the canvas element, of the endpoint
	 * of the line
	 */
	private void drawLine(int x1, int y1, int x2, int y2) {
		int w = x2 - x1;
		int h = y2 - y1;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if (w < 0)
			dx1 = -1;
		else if (w > 0)
			dx1 = 1;
		if (h < 0)
			dy1 = -1;
		else if (h > 0)
			dy1 = 1;
		if (w < 0)
			dx2 = -1;
		else if (w > 0)
			dx2 = 1;
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if (!(longest > shortest)) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if (h < 0)
				dy2 = -1;
			else if (h > 0)
				dy2 = 1;
			dx2 = 0;
		}
		int numerator = longest >> 1;
		for (int i = 0; i <= longest; i++) {
			fillPixel(x1, y1);
			numerator += shortest;
			if (!(numerator < longest)) {
				numerator -= longest;
				x1 += dx1;
				y1 += dy1;
			} else {
				x1 += dx2;
				y1 += dy2;
			}
		}
	}

}
