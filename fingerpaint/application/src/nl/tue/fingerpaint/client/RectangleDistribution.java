package nl.tue.fingerpaint.client;

import com.google.gwt.touch.client.Point;

/**
 * An class representing a concentration distribution belonging to a rectangular
 * geometry
 * 
 * @author Group Fingerpaint
 */
public class RectangleDistribution extends Distribution {

	// --Constructors------------------------------------------------
	/**
	 * Constructs a new distribution belonging to a rectangular geometry
	 */
	public RectangleDistribution() {
		super();
	}

	/**
	 * Constructs a new distribution belonging to a rectangular geometry,
	 * corresponding to the double array d
	 * 
	 * @param d
	 *            The distribution to be set initially
	 */
	public RectangleDistribution(double[] d) {
		super(d);
	}

	// --Getters and Setters------------------------------------------
	/**
	 * Returns the index in the representation vector belonging to the
	 * coordinates ({@code x}, {@code y}) Note: Coordinates (0, 0) represent the
	 * top left corner of the canvas, and coordinates (399, 239) the bottom
	 * right corner.
	 * 
	 * <pre>
	 * 0 <=
	 * {@code x} < 400
	 * 
	 * <pre>
	 * 0 <= {@code y} < 240
	 * 
	 * @param x
	 *            The x-coordinate relative to the top-left corner of the canvas
	 * @param y
	 *            The y-coordinate relative to the top-left corner of the canvas
	 * 
	 * @return The index of {@code representationVector} which corresponds to
	 *         coordinates ({@code x},
	 * {@code y})
	 */
	@Override
	protected int getIndex(int x, int y) {
		return x + 400 * (239 - y);
	}

	/**
	 * Returns a Point object with the coordinates corresponding to the given
	 * index
	 * 
	 * <pre>
	 * 0 <= {@code index} < 96000
	 * 
	 * @param index
	 *            The index in {@code representationVector} to get the
	 *            corresponding coordinates of
	 * 
	 * @return The coordinates corresponding to index {@code index} of
	 * {@code representationVector}
	 * 
	 */
	@Override
	Point getCoordinates(int index) {
		return new Point(index % 400, 239 - index / 400);
	}

	// --Protected and private methods for initialisation and internal
	// use----------
	/**
	 * Creates vector (array) of length 240 * 400. Initialises colour to all
	 * white (1)
	 * 
	 * @post {@code representationVector} is initialised
	 */
	@Override
	protected void initialiseRepresentation() {
		representationVector = new double[96000];
		for (int i = 0; i < 96000; i++) {
			representationVector[i] = 1;
		}
	}

	/**
	 * Initialises the internal representation of the drawing area
	 * 
	 * @param dist
	 *            The representation to initialise to
	 * 
	 * @post {@code representationVector} is initialised to {@code dist}
	 */
	protected void initialiseRepresentation(double[] dist) {
		representationVector = new double[96000];
		for (int i = 0; i < 96000; i++) {
			representationVector[i] = dist[i];
		}
	}
}
