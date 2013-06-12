package nl.tue.fingerpaint.client.model.drawingtool;

import nl.tue.fingerpaint.shared.utils.Colour;

import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.ImageData;

/**
 * A {@code SquareDrawingTool} is a {@link DrawingTool} with a square shape.
 * Users can thus use this drawing tool to draw squares on the canvas.
 * 
 * @author Group Fingerpaint
 */
public class SquareDrawingTool extends DrawingTool {

	// --Constructors-------------------------------------------
	/**
	 * Constructs a square drawing tool with a size of {@code r}.
	 * 
	 * @param r
	 *            The height (and width) of the drawing tool.
	 */
	public SquareDrawingTool(int r) {
		super(r);
	}

	// --Public methods for general use--------------------------
	/**
	 * Returns an ImageData object representing the square drawing tool
	 * 
	 * @param img
	 *            The ImageData object to draw the drawing tool on
	 * @param colour
	 *            The colour to create the drawing tool with
	 */
	@Override
	public ImageData getTool(ImageData img, Colour colour) {
		CanvasPixelArray data = img.getData();

		int width = img.getWidth();
		int height = img.getHeight();
		int col = colour.getRed();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int index = (y * width + x) * 4;
				fillPixel(data, index, col, 255);
			}
		}

		return img;
	}

}
