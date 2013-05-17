package nl.tue.fingerpaint.client;

import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.touch.client.Point;

public abstract class Distribution {

	/*
	 * Internal representation of the geometry
	 */
	protected double[] representationVector;

	// ----Constructors----------------------------------------
	/**
	 * Constructs a new distribution
	 */
	public Distribution() {
		initialiseRepresentation();
	}

	/**
	 * Constructs a new distribution corresponding to the double array dist
	 * 
	 * @param dist
	 *            The distribution to be set initially
	 */
	public Distribution(double[] dist) {
		initialiseRepresentation(dist);
	}

	// ----Getters and Setters------------------------------------
	/**
	 * Returns the representation vector
	 * 
	 * @return The representation vector of this geometry
	 */
	public double[] getVector() {
		return this.representationVector;
	}

	/**
	 * Sets the given value to the index of the {@code representationVector},
	 * corresponding to the coordinates ({@code x}, {@code y})
	 * 
	 * @param x
	 *            The x-coordinate relative to the top-left corner of the canvas
	 * @param y
	 *            The y-coordinate relative to the top-left corner of the canvas
	 * @param value
	 *            The value to be set
	 */
	public void setValue(int x, int y, double value) {
		this.representationVector[getIndex(x, y)] = value;
	}

	/**
	 * Returns the colour-value, between 0 and 1, corresponding to the
	 * coordinates ({@code x}, {@code y})
	 * 
	 * @param x
	 *            The x-coordinate relative to the top-left corner of the canvas
	 * @param y
	 *            The y-coordinate relative to the top-left corner of the canvas
	 * @return The value of {@code representationVector} from the index
	 *         responding to coordinates ({@code x}, {@code y})
	 */
	public double getValue(int x, int y) {
		return this.representationVector[getIndex(x, y)];
	}

	/**
	 * Puts the concentration distribution from the given ImageData in the
	 * representation vector
	 * 
	 * @param img
	 *            The ImageData belonging to the canvas;
	 * @param width
	 *            The width of the canvas;
	 */
	public void setDistribution(ImageData img, int width) {
		CanvasPixelArray data = img.getData();
		int x = 0;
		int y = 0;
		for (int i = 0; i < data.getLength(); i += 4) {
			setValue(x, y, data.get(i));
			x = (x + 1) % width;
			if (x == 0) {
				y++;
			}
		}
	}

	/**
	 * Returns a Point object with the coordinates corresponding to the given
	 * index
	 * 
	 * @param index
	 *            The index in {@code representationVector} to get the
	 *            corresponding coordinates of
	 * 
	 * @return The coordinates corresponding to index {@code index} of
	 *         {@code representationVector}
	 * 
	 */
	abstract Point getCoordinates(int index);

	// ----Protected methods for initialisation and private use----
	/**
	 * Initialises the internal representation of the drawing area
	 * 
	 * @post {@code representationVector} is initialised
	 */
	abstract protected void initialiseRepresentation();

	/**
	 * Initialises the internal representation of the drawing area
	 * 
	 * @param dist
	 *            The representation to initialise to
	 * 
	 * @post {@code representationVector} is initialised to {@code dist}
	 */
	abstract protected void initialiseRepresentation(double[] dist);

	/**
	 * Returns the index in the representation vector belonging to the
	 * coordinates ({@code x}, {@code y}).
	 * 
	 * @param x
	 *            The x-coordinate relative to the top-left corner of the canvas
	 * @param y
	 *            The y-coordinate relative to the top-left corner of the canvas
	 * 
	 * @return The index of {@code representationVector} which corresponds to
	 *         coordinates ({@code x}, {@code y})
	 */
	abstract protected int getIndex(int x, int y);

}
