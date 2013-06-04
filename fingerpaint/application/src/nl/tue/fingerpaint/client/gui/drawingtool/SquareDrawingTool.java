package nl.tue.fingerpaint.client.gui.drawingtool;


import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.ImageData;

/**
 * A class containing a square drawing tool
 * 
 * @author Group Fingerpaint
 */
public class SquareDrawingTool extends DrawingTool {

	// --Constructors-------------------------------------------
	/**
	 * Constructs a square drawing tool with radius r
	 * 
	 * @param r
	 *            The radius of the drawing tool
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
	public ImageData getTool(ImageData img, CssColor colour) {
		CanvasPixelArray data = img.getData();

		int width = img.getWidth();
		int height = img.getHeight();
		int col = colour.value().equals("black") ? 0 : 255;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int index = (y * width + x) * 4;
				fillPixel(data, index, col, 255);
			}
		}

		return img;
	}

}
