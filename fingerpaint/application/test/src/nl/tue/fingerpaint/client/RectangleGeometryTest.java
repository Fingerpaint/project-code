package nl.tue.fingerpaint.client;

import nl.tue.fingerpaint.client.Geometry.stepAddedListener;
import nl.tue.fingerpaint.client.RectangleGeometry;

import org.junit.Test;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.touch.client.Point;
import com.google.gwt.user.client.Window;

/*
 * GWT jUnit test for the class RectangleGeometry in the package 
 * com.google.gwt.fingerpaint.paintingcanvas.client.
 * 
 * @author Tessa Belder
 */
public class RectangleGeometryTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

	// The instance of the RectangleGeometry class to test.
	RectangleGeometry geom;

	// Points to test on the borders of the geometry
	Point[] borderTests = new Point[] { new Point(1, 1), new Point(1, 240),
			new Point(400, 1), new Point(400, 240) };

	// Places in the internalRepresentationVector that correspond to the points
	// in the borderTests array.
	int[] borderResults = new int[] { 400 * 239, 0, 400 * 240 - 1, 399 };

	// Points to test within the geometry
	Point[] innerTests = new Point[] { new Point(2, 2), new Point(36, 66),
			new Point(73, 136), new Point(276, 97) };

	// Places in the internalRepresentationVector that correspond to the points
	// in the innerTests array.
	int[] innerResults = new int[] { 238 * 400 + 1, 174 * 400 + 35,
			104 * 400 + 72, 143 * 400 + 275 };

	/*
	 * Test to determine whether all variables are correctly initialised after
	 * creating a new RectangleGeometry.
	 */
	@Test
	public void testRectangleGeometry() {
		geom = new RectangleGeometry(Window.getClientHeight(),
				Window.getClientWidth());
		assertEquals("Length of representation vector", 240 * 400,
				geom.getDistribution().getVector().length);
		for (int i = 0; i < geom.getDistribution().getVector().length; i++) {
			geom.getDistribution().getVector()[i] = 0;
		}
		assertNotNull(geom.getCanvas());
		assertEquals("Initial drawing color",
				CssColor.make("black").toString(), geom.getColor().toString());
	}

	/*
	 * Test to check whether the internal representation of the rectangular
	 * canvas is updated correctly whenever something is drawn on the canvas.
	 */
	@Test
	public void testDrawPixel() {
		geom = new RectangleGeometry(240, 400);

		// Test whether drawing pixels black is done correct.
		geom.setColor(CssColor.make("black"));

		for (int i = 0; i < 4; i++) {
			geom.fillPixel((int) borderTests[i].getX(),
					(int) borderTests[i].getY());
			assertEquals("BorderTest " + i, 1.0,
					geom.getDistribution().getVector()[borderResults[i]]);

			geom.fillPixel((int) innerTests[i].getX(),
					(int) innerTests[i].getY());
			assertEquals("InnerTest " + i, 1.0,
					geom.getDistribution().getVector()[innerResults[i]]);
		}

		// Test whether drawing pixels white is done correct.
		geom.setColor(CssColor.make("white"));

		for (int i = 0; i < 4; i++) {
			geom.fillPixel((int) borderTests[i].getX(),
					(int) borderTests[i].getY());
			assertEquals("BorderTest " + i, 0.0,
					geom.getDistribution().getVector()[borderResults[i]]);

			geom.fillPixel((int) innerTests[i].getX(),
					(int) innerTests[i].getY());
			assertEquals("InnerTest " + i, 0.0,
					geom.getDistribution().getVector()[innerResults[i]]);
		}
	}
	
	@Test
	public void testStopDefineMixingStep() {
		stepAddedListener stl = new stepAddedListener() {
			
			@Override
			public void onStepAdded(MixingStep step) {
				verifyMixingStep(step);
			}
		};
		
		geom.addStepAddedListener(stl);
		
		geom.startDefineMixingStep(60);
		geom.stopDefineMixingStep(100, 10);
	}
	
	private void verifyMixingStep(MixingStep step) {
		
	}

	@Test
	public void testStartDefineMixingStep() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddStepAddedListener() {
		
	}
}
