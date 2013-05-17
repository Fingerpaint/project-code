package nl.tue.fingerpaint.client;

import java.util.ArrayList;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Abstract class representing a geometry
 * 
 * @author Project Fingerpaint
 */
public abstract class Geometry {

	/*
	 * Internal representation of the geometry
	 */
	protected Distribution distribution;

	/*
	 * The canvas and its parameters
	 */
	protected Canvas canvas;
	protected Context2d context;
	protected int factor;

	/*
	 * dummy context to clip the painting tool when painting close to the
	 * borders of the geometry
	 */
	protected Context2d dummycontext;

	/*
	 * The drawing tool;
	 */
	protected DrawingTool tool;

	/*
	 * The image to draw with
	 */
	protected ImageData toolImage;

	/*
	 * The extra width of the drawing tool. Equal to factor * tool.radius
	 */
	protected int displacement;

	/**
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

	/**
	 * Stores the x-coordinate of the mouse event that initiates swiping.
	 */
	protected int swipeStartX;
	/**
	 * Stores the y-coordinate of the mouse event that initiates swiping.
	 */
	protected int swipeStartY;

	// ----Constructor-----------------------------------------------
	/**
	 * Creates a new Geometry, initial colour is black.
	 * 
	 * @param clientHeight
	 *            The height of the window in which the application is displayed
	 * 
	 * @param clientWidth
	 *            The width of the window in which the application is displayed
	 */
	public Geometry(int clientHeight, int clientWidth) {
		initialiseDistribution();

		setFactor(Math.max(
				1,
				(Math.min(clientHeight / getBaseHeight(), clientWidth
						/ getBaseWidth()))));
		createCanvas(clientHeight, clientWidth);
		drawGeometryOutline();

		// Initialise drawing colour to black
		setColor(CssColor.make("black"));

		// Initialise drawing tool to a square with radius 3
		setDrawingTool(new SquareDrawingTool(3));
	}

	// ----Getters and Setters---------------------------------------
	/**
	 * Returns the canvas belonging to this Geometry
	 * 
	 * @return The canvas belonging to this Geometry
	 */
	public Canvas getCanvas() {
		return this.canvas;
	}

	/**
	 * Returns the current drawing colour
	 * 
	 * @return The current colour which is used for drawing
	 */
	public CssColor getColor() {
		return this.currentColor;
	}

	/**
	 * Sets the current drawing colour
	 * 
	 * @param color
	 *            The colour which becomes the current drawing colour
	 * 
	 * @post The current drawing colour is set to @param{color}
	 */
	public void setColor(CssColor color) {
		this.currentColor = color;
		if (this.tool != null) {
			setDrawingTool(this.tool);
		}
	}

//	/**
//	 * Returns the distribution
//	 * 
//	 * @return The distribution of this geometry
//	 */
//	public Distribution getDistribution() {
//		return this.distribution;
//	}
	
	/**
	 * Returns the distribution
	 * 
	 * @return The distribution of this geometry
	 */
	abstract public Distribution getDistribution();

	/**
	 * Sets the distribution to {@code dist}
	 * 
	 * @param dist
	 *            The distribution to set
	 */
	public void setDistribution(Distribution dist) {
		this.distribution = dist;
	}

	/**
	 * Sets the distribution to {@code dist}
	 * 
	 * @param dist
	 *            The distribution to set
	 */
	abstract public void setDistribution(double[] dist);

	/**
	 * Returns the base height of the geometry
	 * 
	 * @return The minimum height of this geometry
	 */
	abstract public int getBaseHeight();

	/**
	 * Returns the base width of the geometry
	 * 
	 * @return The minimum width of this geometry
	 */
	abstract public int getBaseWidth();

	/**
	 * Returns the total height of the drawing area
	 * 
	 * @return total height of the drawing area
	 */
	public int getHeight() {
		return factor * getBaseHeight();
	}

	/**
	 * Returns the total width of the drawing area
	 * 
	 * @return total width of the drawing area
	 */
	public int getWidth() {
		return factor * getBaseWidth();
	}

	/**
	 * Sets the factor to multiply the outline of the geometry with.
	 * 
	 * @post The multiply factor has been set to @param{factor}
	 */
	abstract public void setFactor(int factor);

	/**
	 * Sets the drawing tool
	 * 
	 * @param tool
	 *            The tool to set as the drawing tool
	 * 
	 * @post {@code this.tool} has been set to {@code tool}
	 */
	public void setDrawingTool(DrawingTool tool) {
		this.tool = tool;
		int rad = tool.getRadius();
		int size = (rad * 2 + 1) * factor;
		this.displacement = rad * factor;
		this.toolImage = tool.getTool(context.getImageData(1, 1, size, size),
				currentColor);
		this.dummycontext = getDummyContext(size, size, toolImage);
	}

	/**
	 * 
	 * Returns the ImageData to paint on this position on the canvas, meaning it
	 * is clipped when the coordinates are close to the border of the geometry.
	 * 
	 * @param x
	 *            A valid x-coordinate, meaning the top-left pixel of a
	 *            'grid-pixel'
	 * @param y
	 *            A valid y-coordinate, meaning the top-left pixel of a
	 *            'grid-pixel'
	 * @return The imageData to paint on this position on the canvas
	 */
	protected ImageData getTool(int x, int y, ImageData img) {

		if (x > displacement && y > displacement
				&& x < getWidth() - displacement
				&& y < getHeight() - displacement) {
			return img;
		} else {
			int rad = tool.getRadius();
			int left = 0;
			int top = 0;
			int right = (rad * 2 + 1) * factor - 1;
			int bottom = (rad * 2 + 1) * factor - 1;
			if (x <= displacement) {
				left = displacement + 1 - x;
			}
			if (y <= displacement) {
				top = displacement + 1 - y;
			}
			if (x >= getWidth() - displacement) {
				right = displacement + getWidth() - x;
			}
			if (y >= getHeight() - displacement) {
				bottom = displacement + getHeight() - y;
			}
			return dummycontext.getImageData(left, top, right - left + 1,
					bottom - top + 1);
		}
	}

	// ----Protected and private methods for initialisation and
	// drawing------------

	/**
	 * Initialisation method which creates the canvas. Also initialises and adds
	 * MouseHandlers to the canvas. If the browser doesn't support HTML5 canvas,
	 * adds a message stating this to the RootPanel and returns.
	 * 
	 * @param height
	 *            The height of the canvas to be created
	 * @param width
	 *            The width of the canvas to be created
	 */
	protected void createCanvas(int height, int width) {
		// Create the canvas
		canvas = Canvas.createIfSupported();

		// If canvas wan't created, add a label to the root panel stating this
		// and return.
		if (canvas == null) {
			RootPanel
					.get()
					.add(new Label(
							"Sorry, your browser doesn't support the HTML5 Canvas element"));
			return;
		}

		// Initialise canvas
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
					x = getValidCoord(x);
					y = getValidCoord(y);
					context.putImageData(getTool(x, y, toolImage),
							Math.max(x - displacement, 1),
							Math.max(y - displacement, 1));
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
									}
								}
							});
				}
				startDefineMixingStep(event.getX(), event.getY());
			}
		});

		canvas.addMouseUpHandler(new MouseUpHandler() {

			/*
			 * When the left mouse button is released, drawing is ended
			 */
			@Override
			public void onMouseUp(MouseUpEvent event) {
				removeMouseMoveHandler();
				stopDefineMixingStep(event.getX(), event.getY());

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
				stopDefineMixingStep(event.getX(), event.getY());
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

	/**
	 * Initialises the distribution of the drawing area
	 * 
	 * @post {@code distribution} is initialised
	 */
	abstract protected void initialiseDistribution();

	/**
	 * Draws the border around the drawing area
	 * 
	 * @post The outline of the geometry has been drawn on the {@code canvas}
	 */
	abstract protected void drawGeometryOutline();

	/**
	 * Colours the pixel(s) corresponding to coordinates ({@code x}, {@code y}).
	 * Also updates the internal representation accordingly.
	 * 
	 * @param x
	 *            The horizontal position of the mouse click relative to the
	 *            top-left corner of the {@code canvas}
	 * @param y
	 *            The vertical position of the mouse click relative to the
	 *            top-left corner of the {@code canvas}
	 * @param colour
	 *            The colour to fill the pixel with
	 * 
	 * @post The cell of the {@code internalRepresenationVector} corresponding
	 *       to the coordinates ({@code x}, {@code y}) has been updated, and the
	 *       corresponding pixels on the canvas have been coloured
	 */
	abstract protected void fillPixel(int x, int y, CssColor colour);

	/**
	 * Colours the pixel(s) corresponding to coordinates ({@code x}, {@code y}).
	 * Also updates the internal representation accordingly.
	 * 
	 * @param x
	 *            The horizontal position of the mouse click relative to the
	 *            top-left corner of the {@code canvas}
	 * @param y
	 *            The vertical position of the mouse click relative to the
	 *            top-left corner of the {@code canvas}
	 * 
	 * @post The cell of the {@code internalRepresenationVector} corresponding
	 *       to the coordinates ({@code x}, {@code y}) has been updated, and the
	 *       corresponding pixels on the canvas have been coloured with
	 *       {@code currentColor}
	 */
	protected void fillPixel(int x, int y) {
		fillPixel(x, y, currentColor);
	}

	/**
	 * Returns whether the position ({@code x}, {@code y}) is inside the drawing
	 * area on the {@code canvas}.
	 * 
	 * @return {@code true} if coordinates (x, y) are inside the drawing area.
	 *         {@code false} otherwise.
	 */
	abstract protected boolean isInside(int x, int y);

	/**
	 * Draws a line manually, filling a single pixel at a time. Can't use the
	 * standard 'drawLine()' method for this purpose, since this makes it
	 * impossible to update internal representation.
	 * 
	 * Algorithm obtained from the Internet. Source:
	 * http://tech-algorithm.com/articles
	 * /drawing-line-using-bresenham-algorithm/
	 * 
	 * @param x1
	 *            x-coordinate, relative to the canvas element, of the start
	 *            point of the line
	 * @param y1
	 *            y-coordinate, relative to the canvas element, of the start
	 *            point of the line
	 * @param x2
	 *            x-coordinate, relative to the canvas element, of the end point
	 *            of the line
	 * @param y2
	 *            y-coordinate, relative to the canvas element, of the end point
	 *            of the line
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
			int x3 = getValidCoord(x1);
			int y3 = getValidCoord(y1);
			context.putImageData(getTool(x3, y3, toolImage),
					Math.max(x3 - displacement, 1),
					Math.max(y3 - displacement, 1));
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

	/**
	 * Together with {@code stopDefineMixingStep()}, this function checks whether
	 * the protocol should be updated by adding a new {@code Step}. This
	 * particular function only stores the coordinates of the press event, to
	 * calculate the distance travelled when the mouse or the user's finger is
	 * lifted from the canvas.
	 * 
	 * @param mouseX
	 *            x-coordinate of the mouseEvent.
	 */
	protected void startDefineMixingStep(int mouseX, int mouseY) {
		// TODO: Only execute if the user actually wants to define a protocol
		// step (but when is that?)
		swipeStartX = mouseX;
		swipeStartY = mouseY;
	}

	/**
	 * Together with {@code startDefineMixingStep()}, this function checks whether
	 * the protocol should be updated by adding a new {@code Step}. This
	 * particular function should be implemented by geometries to detail when and
	 * how a new {@code Step} should be defined.
	 * 
	 * @param mouseX
	 *            x-coordinate of the mouseEvent.
	 * @param mouseY
	 *            y-coordinate of the mouseEvent.
	 */
	protected abstract void stopDefineMixingStep(int mouseX, int mouseY);
	
	/**
	 * Should return the direction and wall of the current swiping movement.
	 * should return null if the swipe is not a valid swipe.
	 * 
	 * This method should additionally draw a graphic to display that a swiping motion is in progress
	 * 
	 * @param mouseX The x-coordinate of the current mouse position.
	 * @param mouseY The y-coordinate of the current mouse position.
	 */
	protected abstract MixingStep determineSwipe(int mouseX, int mouseY);
	
	/**
	 * Returns a CssColor object with the gray scale colour corresponding to the
	 * given value
	 * 
	 * @param value
	 *            The value which determines the colour; 0 means black and 1
	 *            means white
	 * @return The CssColor object with the gray scale colour corresponding to
	 *         {@code value}
	 */
	protected CssColor getColour(double value) {
		int colourCode = (int) Math.round(value * 255);
		return CssColor.make(colourCode, colourCode, colourCode);
	}
	
	/**
	 * Draws an image located in the map war/img.
	 * 
	 * @param name the name of the image file itself
	 * @param locationX the desired left location of the image
	 * @param locationY the desired top location of the image
	 */
	protected void drawImage(String name, int locationX, int locationY){
		Image image = new Image("/img/" + name + ".png");
		image.getElement().getStyle().setLeft(locationX, Unit.PX);
		image.getElement().getStyle().setTop(locationY, Unit.PX);
		ImageElement imgelem = ImageElement.as(image.getElement());
		//context.drawImage(imgelem, locationX, locationY);
	}
	
	/**
	 * clears the canvas between locationX and locationX + sizeX, 
	 * and locationY and locationY + sizeY
	 * 
	 * @param locationX the minimum x-value of the data that needs to be cleared
	 * @param locationY the minimum y-value of the data that needs to be cleared
	 * @param sizeX the width of the to be cleared section
	 * @param sizeY the height of the to be cleared section
	 */
	protected void clear(int locationX, int locationY, int sizeX, int sizeY){
		
	}

	/**
	 * Creates a dummy canvas and returns it context to keep for clipping of the
	 * drawing tool image.
	 * 
	 * @param width
	 *            The width of the dummy canvas, equals to the width of the
	 *            drawing tool
	 * @param height
	 *            The width of the dummy canvas, equals to the width of the
	 *            drawing tool
	 * @param img
	 *            The ImageData belonging to the drawing tool
	 * 
	 * @return The context of this canvas, containing the drawing tool image
	 */
	private Context2d getDummyContext(int width, int height, ImageData img) {
		Canvas dummycanvas = Canvas.createIfSupported();

		// Initialise canvas
		if (dummycanvas != null) {
			dummycanvas.setWidth(width + "px");
			dummycanvas.setCoordinateSpaceWidth(width);
			dummycanvas.setHeight(height + "px");
			dummycanvas.setCoordinateSpaceHeight(height);
		}

		Context2d dummyContext = dummycanvas.getContext2d();
		dummyContext.putImageData(img, 0, 0);

		return dummyContext;
	}

	/**
	 * Converts a value to a valid coordinate, meaning the a value representing
	 * the top left pixel of a 'grid-pixel'
	 * 
	 * @param c
	 *            The coordinate to convert
	 * 
	 * @return The valid coordinate belonging to input c
	 */
	protected int getValidCoord(int c) {
		return ((c - 1) / factor) * factor + 1;
	}

	// --Public methods for general use---------------------------------
	/**
	 * Sets the given distribution as the current distribution, and draws it on
	 * the canvas
	 * 
	 * @param dist
	 *            The distribution to be set and drawn
	 */
	public void drawDistribution(Distribution dist) {
		drawDistribution(dist.getVector());
	}

	/**
	 * Sets the given distribution as the current distribution, and draws it on
	 * the canvas
	 * 
	 * @param dist
	 *            The distribution to be set and drawn
	 */
	abstract public void drawDistribution(double[] dist);
	
	protected ArrayList<StepAddedListener> stepAddedListeners = new ArrayList<StepAddedListener>();
	
	public interface StepAddedListener {
		public void onStepAdded(MixingStep step);
	}
	
	public void addStepAddedListener(StepAddedListener l) {
		stepAddedListeners.add(l);
	}
	
	public void removeStepAddedListener(StepAddedListener l) {
		stepAddedListeners.remove(l);
	}
}
