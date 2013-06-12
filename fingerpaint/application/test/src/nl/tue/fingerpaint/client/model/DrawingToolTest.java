package nl.tue.fingerpaint.client.model;

import java.util.ArrayList;

import nl.tue.fingerpaint.client.model.drawingtool.CircleDrawingTool;
import nl.tue.fingerpaint.client.model.drawingtool.DrawingTool;
import nl.tue.fingerpaint.client.model.drawingtool.SquareDrawingTool;
import nl.tue.fingerpaint.shared.utils.Colour;

import org.junit.Test;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the abstract class {@link DrawingTool}, and its extending
 * classed {@link SquareDrawingTool} and {@link CircleDrawingTool}.
 * 
 * @author Group Fingerpaint
 */
public class DrawingToolTest extends GWTTestCase {

	private ArrayList<DrawingTool> tools;
	private Context2d context;

	/**
	 * Test whether the radius of the drawing tools is being set correctly
	 */
	@Test
	public void testRadius() {
		init();
		for (DrawingTool t : tools) {
			assertEquals("Initial radius of tool " + tools.indexOf(t), 3,
					t.getRadius());
		}
		int[] radius = new int[] { -1, 0, 5, 10 };
		int[] expected = new int[] { 0, 0, 5, 10 };
		for (int i = 0; i < radius.length; i++) {
			for (DrawingTool t : tools) {
				t.setRadius(radius[i]);
				assertEquals("Radius of tool " + tools.indexOf(t)
						+ " after setting radius " + radius[i], expected[i],
						t.getRadius());
			}
		}
	}

	/**
	 * Test whether the image data of the drawing tools is generated correctly
	 */
	@Test
	public void testGetTool() {
		init();
		final int size = 11;
		final Colour col = Colour.BLACK;
		ImageData data = getImageData(size);
		for (DrawingTool t : tools) {
			ImageData result = t.getTool(data, col);
			assertEquals("Width of drawing tool " + tools.indexOf(t), size,
					result.getWidth());
			assertEquals("Height of drawing tool " + tools.indexOf(t), size,
					result.getHeight());
			for (int w = 0; w < result.getWidth(); w++) {
				for (int h = 0; h < result.getHeight(); h++) {
					boolean pass = equalsColour(col, result, w, h)
							|| isTransparent(result, w, h);
					assertTrue(
							"Pixel (" + w + ", " + h + ") of tool "
									+ tools.indexOf(t)
									+ " right colour or not in picture", pass);
				}
			}

		}
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

	// --- PRIVATE PART --------------------------------------------------
	/**
	 * Set up method for initialising the drawing tools.
	 */
	private void init() {
		tools = new ArrayList<DrawingTool>();
		tools.add(new SquareDrawingTool(3));
		tools.add(new CircleDrawingTool(3));

		Canvas canvas = Canvas.createIfSupported();
		canvas.setWidth(500 + "px");
		canvas.setCoordinateSpaceWidth(500);
		canvas.setHeight(500 + "px");
		canvas.setCoordinateSpaceHeight(500);
		context = canvas.getContext2d();

	}

	/**
	 * Return a square piece of image data with width and height {@code size}
	 * 
	 * @param size
	 *            The width and height of the piece of image data
	 * @return An ImageData object of the requested size
	 */
	private ImageData getImageData(int size) {
		return context.createImageData(size, size);
	}

	/**
	 * Returns whether the colour of a certain pixel of an ImageData object
	 * equals the given colour.
	 * 
	 * @param col
	 *            The colour to test for
	 * @param result
	 *            The piece of ImageData
	 * @param x
	 *            The x-coordinate of the pixel
	 * @param y
	 *            The y-coordinate of the pixel
	 * @return {@code true} if the red, green and blue values of the two colours
	 *         are the same, {@code false} otherwise.
	 */
	private boolean equalsColour(Colour col, ImageData result, int x, int y) {
		return col.getBlue() == result.getBlueAt(x, y)
				&& col.getRed() == result.getRedAt(x, y)
				&& col.getGreen() == result.getGreenAt(x, y);
	}

	/**
	 * Returns whether a certain pixel of an ImageData object is transparent or
	 * not.
	 * 
	 * @param result
	 *            The piece of ImageData
	 * @param x
	 *            The x-coordinate of the pixel
	 * @param y
	 *            The y-coordinate of the pixel
	 * @return {@code true} if this pixel has alpha value 0, {@code false}
	 *         otherwise.
	 */
	private boolean isTransparent(ImageData result, int x, int y) {
		return result.getAlphaAt(x, y) == 0;
	}
}
