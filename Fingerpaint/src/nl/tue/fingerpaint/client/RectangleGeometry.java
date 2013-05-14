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

/*
 * Class that represents the rectangular geometry. Keeps the internal representation in a vector.
 * Also contains the canvas and methods to draw on it.
 * 
 * @author Tessa Belder
 */
public class RectangleGeometry {

	/*
	 * Internal representation of the geometry
	 */
	private int[] rectangleVector = new int[96000];
	
	/*
	 * The canvas and its parameters
	 */
	Canvas canvas;
	Context2d context;
	static final int canvasHeight = 240;
	static final int canvasWidth = 400;
	
	/*
	 * Reference to the current MouseMoveHandler attached to the canvas
	 */
	HandlerRegistration mouseMove;
	
	/*
	 * Parameters to handle the drawing	
	 */
	int previousX;
	int previousY;
	boolean dragging;
	private CssColor currentColor;

//----Contructor-----------------------------------------------
	public RectangleGeometry() {
		//Initialize internal representation to all white (0)
		for (int i = 0; i < 96000; i++) {
			rectangleVector[i] = 0;
		}
		
		createCanvas();
		
		//Initialize drawing color to black
		currentColor = CssColor.make("black");
	}

//----Getters and Setters---------------------------------------
	/*
	 * Returns the canvas belonging to this RectangleGeometry
	 * 
	 * @return The canvas belonging to this RectangleGeometry
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

//----Private methods for initialization and drawing------------
	/*
	 * Initialization method which creates the canvas. Also initialises
	 * and adds MouseHandlers to the canvas. If the browser doesn't support
	 * HTML5 canvas, adds a message stating this to the RootPanel and returns.
	 */
	private void createCanvas() {
		//Create the canvas
		canvas = Canvas.createIfSupported();

		//If canvas wan't created, add a label to the Rootpanel stating this and return.
		if (canvas == null) {
			RootPanel
					.get()
					.add(new Label(
							"Sorry, your browser doesn't support the HTML5 Canvas element"));
			return;
		}

		//Initialize canvas
		canvas.setStyleName("paintCanvas");
		canvas.setWidth(canvasWidth + "px");
		canvas.setCoordinateSpaceWidth(canvasWidth);
		canvas.setHeight(canvasHeight + "px");
		canvas.setCoordinateSpaceHeight(canvasHeight);

		//Get context of canvas, to use for painting
		context = canvas.getContext2d();

		//When the left mouse button is pressed, drawing is started
		canvas.addMouseDownHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {
				dragging = true;
				Element elem = event.getRelativeElement();
				previousX = event.getRelativeX(elem);
				previousY = event.getRelativeY(elem);
				drawPixel(previousX, previousY);
				mouseMove = canvas.addMouseMoveHandler(new MouseMoveHandler() {
					@Override
					public void onMouseMove(MouseMoveEvent e) {
						Element elem = e.getRelativeElement();
						drawLine(e.getRelativeX(elem), e.getRelativeY(elem));
					}
				});
			}
		});

		//When the left mouse button is released, drawing is ended
		canvas.addMouseUpHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				removeMouseMoveHandler();

			}

		});

		//When the mouse leaves the canvas area, drawing is ended
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
	 * Fills a single pixel of the canvas with the current color.
	 * Also updates internal representation vector accordingly.
	 * 
	 * @param x x-coordinate, relative to the canvas element,
	 * 		of the pixel to be filled
	 * @param y y-coordinate, relative to the canvas element,
	 * 		of the pixel to be filled
	 */
	private void drawPixel(int x, int y) {
		context.setFillStyle(currentColor);
		context.fillRect(x, y, 1, 1);
		context.fill();
		if (currentColor.value().equals("black")) {
			rectangleVector[x + 400 * (239 - y)] = 1;
		} else {
			rectangleVector[x + 400 * (240 - y)] = 0;
		}
	}

	/*
	 * Draws a line manually, filling a single pixel at a time.
	 * Can't use the standard 'drawLine()' method for this purpose,
	 * since this makes it impossible to update internal representation.
	 * 
	 * Algorithm obtained from the internet. Source:
	 * http://tech-algorithm.com/articles/drawing-line-using-bresenham-algorithm/
	 * 
	 * @param newX x-coordinate, relative to the canvas element,
	 * 		of the endpoint of the line
	 * @param newY y-coordinate, relative to the canvas element,
	 * 		of the endpoint of the line
	 */
	private void drawLine(int newX, int newY) {
		int w = newX - previousX;
		int h = newY - previousY;
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
		int x = previousX, y = previousY;
		for (int i = 0; i <= longest; i++) {
			drawPixel(x, y);
			numerator += shortest;
			if (!(numerator < longest)) {
				numerator -= longest;
				x += dx1;
				y += dy1;
			} else {
				x += dx2;
				y += dy2;
			}
		}
		previousX = newX;
		previousY = newY;
	}

}
