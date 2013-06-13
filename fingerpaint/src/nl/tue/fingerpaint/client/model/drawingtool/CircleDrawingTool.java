package nl.tue.fingerpaint.client.model.drawingtool;

import nl.tue.fingerpaint.shared.utils.Colour;

import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.ImageData;

/**
 * A {@code CircleDrawingTool} is a {@link DrawingTool} with a circular shape.
 * Users can thus use this drawing tool to draw circles on the canvas.
 * 
 * @author Group Fingerpaint
 */
public class CircleDrawingTool extends DrawingTool {

	// --Constructors-------------------------------------------
	/**
	 * Constructs a circle drawing tool with radius r
	 * 
	 * @param r
	 *            The radius of the drawing tool
	 */
	public CircleDrawingTool(int r) {
		super(r);
	}

	// --Private and protected methods for internal use---------------------
	/**
	 * Fills all the pixels in the data array belonging to this pixel with
	 * regard to the grid. That is, when we have a factor of 3 for example,
	 * then every pixel is shown on the canvas as  3x3 = 9 pixels. This
	 * function then fills those 9 pixels.
	 * 
	 * @param data
	 *            The canvas pixel array that holds the canvas data
	 * @param x
	 *            The x coordinate to fill
	 * @param y
	 *            The y coordinate to fill
	 * @param factor
	 *            The factor the canvas is enlarged with
	 * @param col
	 *            The colour to fill the pixels with
	 * @param width
	 *            The width of the ImageData object belonging to canvas pixel
	 *            array {@code data}
	 */
	private void fillEntirePixel(CanvasPixelArray data, int x, int y,
			int factor, int col, int width) {
		for (int w = factor * x; w < factor * x + factor; w++) {
			for (int h = factor * y; h < factor * y + factor; h++) {
				int index = (h * width + w) * 4;
				fillPixel(data, index, col, 255);
			}
		}

	}

	// --Public methods for general use--------------------------
	/**
	 * Returns an ImageData object representing the circle drawing tool.
	 * 
	 * @param img
	 *            The ImageData object to draw the drawing tool on
	 * @param colour
	 *            The colour to create the drawing tool with
	 */
	@Override
	public ImageData getTool(ImageData img, Colour colour) {
		int width = img.getWidth();
		int factor = width / (radius * 2 + 1);
		int x = radius;
		int y = radius;
		CanvasPixelArray data = img.getData();
		int col = colour.getRed();

		int i = 0, j = radius;
		while (i <= j) {

			for (int w = x - j; w <= x + j; w++) {
				fillEntirePixel(data, w, y + i, factor, col, width);
			}
			for (int w = x - j; w <= x + j; w++) {
				fillEntirePixel(data, w, y - i, factor, col, width);
			}
			for (int w = x - i; w <= x + i; w++) {
				fillEntirePixel(data, w, y + j, factor, col, width);
			}
			for (int w = x - i; w <= x + i; w++) {
				fillEntirePixel(data, w, y - j, factor, col, width);
			}

			i++;
			j = (int) (Math.sqrt(radius * radius - i * i) + 0.5);
		}

		return img;
	}

}