package nl.tue.fingerpaint.client;

import java.io.Serializable;

import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.touch.client.Point;

/**
 * An abstract class representing a concentration distribution
 * 
 * @author Group Fingerpaint
 */
public abstract class Distribution implements Serializable {

	/**
	 * Auto-generated UID for the serialisation.
	 */
	private static final long serialVersionUID = -2090726524128904891L;
	/**
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
	 * Puts the concentration distribution from the given ImageData in the
	 * representation vector
	 * 
	 * @param img
	 *            The ImageData belonging to the canvas;
	 * @param width
	 *            The width of the canvas;
	 */
	public void setDistribution(ImageData img, int factor) {
		CanvasPixelArray data = img.getData();
		int width = img.getWidth();
		int height = img.getHeight();
		int index;
		for (int y = height - factor; y >=0 ; y-=factor) {
			for (int x = 0; x < width; x +=factor) {
				index = (y * width + x) * 4;
				System.out.println(x +" " +y +" " +index);
				setValue(x / factor, y / factor, (double) data.get(index) / 255);
				System.out.println(data.get(index));
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
