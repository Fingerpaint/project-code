package nl.tue.fingerpaint.client.model;

import nl.tue.fingerpaint.client.model.Geometry.StepAddedListener;

import org.junit.Test;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the class {@link RectangleGeometry}. TODO: all tests fail
 * in this class, so it needs some serious rework!
 * 
 * @author Group Fingerpaint
 */
public class RectangleGeometryTest extends GWTTestCase {

	/*
	 * !IMPORTANT
	 * 
	 * For some reason the constructor of Geometry throws an exception when it
	 * is called in these test-cases. The line 'context.createImageData(size,
	 * size)' in the method 'setDrawingTool' in the class 'Geometry' throws this
	 * exception. This doesn't happen when running the application in
	 * development or production mode, so I've no idea what the problem is.
	 * Since all of the test cases in this test class start by creating a new
	 * Geometry object, all the tests fail.
	 * 
	 * IMPORTANT!
	 */

	// The instance of the RectangleGeometry class to test.
	private RectangleGeometry geom;

	private final int clientHeight = 400;
	private final int clientWidth = 600;

	/**
	 * Ugly hack courtesy of Femke and Thom
	 */
	private boolean mixingStepAdded = false;

	/**
	 * Test to determine whether all variables are correctly initialised after
	 * creating a new RectangleGeometry.
	 */
	@Test
	public void testRectangleGeometry() {
		geom = new RectangleGeometry(clientHeight, clientWidth);

		assertEquals("Length of representation vector", 240 * 400,
				geom.getDistribution().length);
		int[] dist = geom.getDistribution();

		for (int i = 0; i < dist.length; i++) {
			assertEquals("Initial distribution", 255, dist[i]);
		}
		assertNotNull(geom.getCanvas());
		assertEquals("Initial drawing color",
				CssColor.make("black").toString(), geom.getColor().toString());
	}

	/**
	 * Tests whether the end of a swipe in the canvas correctly does or does not
	 * generate a new mixing step.
	 */
	@Test
	public void testStopDefineMixingStep() {
		int width = new RectangleGeometry(clientHeight, clientWidth).getWidth();
		int height = new RectangleGeometry(clientHeight, clientWidth)
				.getHeight();

		// TODO: Add factor everywhere.
		testStopDefineMixingStep("T", true, true, true, 100, 140,
				Geometry.TOP_OFFSET, 1.0);
		testStopDefineMixingStep("-T", true, true, false, 100, 60,
				Geometry.TOP_OFFSET, 1.0);
		testStopDefineMixingStep("T step size 10", true, true, true, 100, 140,
				Geometry.TOP_OFFSET, 10.0);
		testStopDefineMixingStep("T step size 12.5", true, true, false, 100,
				60, Geometry.TOP_OFFSET, 12.5);
		testStopDefineMixingStep("T step size 1.25", true, true, false, 100,
				60, Geometry.TOP_OFFSET, 1.25);
		testStopDefineMixingStep("B", true, false, true, 100, 60,
				Geometry.TOP_OFFSET + height + 10, 1.0);
		testStopDefineMixingStep("-B", true, false, false, 100, 140,
				Geometry.TOP_OFFSET + height + 10, 1.0);
		testStopDefineMixingStep("On border TR", false, true, true,
				Geometry.X_OFFSET + width - 30, Geometry.X_OFFSET + width,
				Geometry.TOP_OFFSET, 1.0);
		testStopDefineMixingStep("On border BL", false, false, true,
				Geometry.X_OFFSET - 30, Geometry.X_OFFSET, Geometry.TOP_OFFSET
						+ height, 1.0);
		testStopDefineMixingStep("Just inside TR", true, true, true,
				Geometry.X_OFFSET + width - 30, Geometry.X_OFFSET + width - 1,
				Geometry.TOP_OFFSET + 1, 1.0);
		testStopDefineMixingStep("Just inside BR", true, false, false,
				Geometry.X_OFFSET - 30, Geometry.X_OFFSET, Geometry.TOP_OFFSET
						+ height, 1.0);
		testStopDefineMixingStep("Just inside TL", true, true, true,
				Geometry.X_OFFSET + 30, Geometry.X_OFFSET + 1,
				Geometry.TOP_OFFSET + 1, 1.0);
		testStopDefineMixingStep("Just inside BL", true, false, false,
				Geometry.X_OFFSET + 30, Geometry.X_OFFSET + 1,
				Geometry.TOP_OFFSET + height - 1, 1.0);
		testStopDefineMixingStep("Swipe in middle", false, true, true, 100,
				140, height / 2, 1.0);
		testStopDefineMixingStep("Left top outside", false, true, true, 10,
				-10, -5, 1.0);
		testStopDefineMixingStep("Right bottom outside", false, true, true,
				width + 10, width - 40, height + 20, 1.0);
	}

	/**
	 * Tests whether implementation is correct.
	 * 
	 * @param message
	 *            Textual description of the test case.
	 * @param shouldBeCalled
	 *            Stores whether a new step should be created (and added to the
	 *            protocol).
	 * @param top
	 *            Indicates whether the top wall should be moved.
	 * @param clockwise
	 *            Indicates whether the movement is in clockwise direction.
	 * @param startX
	 *            Starting x-coordinate when the swipe is initiated.
	 * @param startY
	 *            Starting y-coordinate when the swipe is initiated.
	 * @param endX
	 *            Final x-coordinate when the swipe is terminated.
	 * @param stepSize
	 *            Desired step size for the {@code Step}.
	 */
	public void testStopDefineMixingStep(String message,
			boolean shouldBeCalled, boolean top, boolean clockwise, int startX,
			int endX, int startY, double stepSize) {
		geom = new RectangleGeometry(clientHeight, clientWidth);

		StepAddedListener stl = setUpStepAddedListener(message, top, clockwise,
				stepSize);
		geom.addStepAddedListener(stl);
		geom.startDefineMixingStep(startX, startY);
		geom.stopDefineMixingStep(endX, startY);

		assertEquals(message + " should be called", shouldBeCalled,
				mixingStepAdded);
		mixingStepAdded = false;
	}

	/**
	 * 
	 */
	@Test
	public void testDetermineSwipe() {
		geom = new RectangleGeometry(clientHeight, clientWidth);

		// simulate a horizontal swipe to the right, just barely over the
		// threshold
		geom.swipeStartX = 50;
		geom.swipeStartY = 50;
		geom.stopDefineMixingStep(geom.swipeStartX
				+ RectangleGeometry.SWIPE_THRESHOLD + 1, geom.swipeStartY);

		// TODO test the result
	}

	/**
	 * Tests if the resetButton creates a completely white canvas (internal
	 * representation)
	 */
	@Test
	public void testResetDist() {
		geom = new RectangleGeometry(clientHeight, clientWidth);
		// make the canvas black
		geom.setDistribution(new int[96000]);// using the initialisation
												// value of 0
		// reset the canvas to white
		geom.resetDistribution();
		// verify the result
		int[] dist = geom.getDistribution();
		// check all indices
		for (int i = 0; i < dist.length; i++) {
			assertEquals(255, dist[i]);
		}
	}

	private StepAddedListener setUpStepAddedListener(final String message,
			final boolean top, final boolean clockwise, final double stepSize) {
		StepAddedListener stl = new StepAddedListener() {

			@Override
			public void onStepAdded(MixingStep step) {
				step.setStepSize(stepSize);
				mixingStepAdded = true;
				assertEquals(message + " is top", top, step.isTopWall());
				assertEquals(message + " is clockwise", clockwise,
						step.isClockwise());
				assertEquals(message + " has stepsize " + stepSize, stepSize,
						step.getStepSize());
			}
		};

		return stl;
	}

	/**
	 * Returns the module name for the GWT test.
	 */
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
