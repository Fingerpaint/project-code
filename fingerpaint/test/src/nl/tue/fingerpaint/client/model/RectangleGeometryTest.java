package nl.tue.fingerpaint.client.model;

import nl.tue.fingerpaint.client.model.Geometry.StepAddedListener;
import nl.tue.fingerpaint.shared.model.MixingStep;
import nl.tue.fingerpaint.shared.model.RectangleMixingStep;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the class {@link RectangleGeometry}.
 * 
 * @author Group Fingerpaint
 */
public class RectangleGeometryTest extends GWTTestCase {

protected RectangleGeometry geom;
	
	/** arbitrary height, needed to initialise the geometry */
	public final int testClientHeight = 400;
	/** arbitrary width, needed to initialise the geometry */
	public final int testClientWidth = 600;

	protected boolean listenerFired = false;
	
	/**
	 * Initialises the geometry for a new test case
	 */
	public void  initialise(){
		geom = new RectangleGeometry(testClientHeight, testClientWidth);
	}
	
	//-----Unit tests----------------------------------------------------------
	
	/**
	 * A test to check whether a new geometry is initialised correctly.
	 */
	@Test
	public void testInitCanvas() {
		initialise();
		
		//check for correct length
		assertEquals("The canvas should have a size of 96000", 96000, geom.getDistribution().length);
		
		//inspect a random cell, since inspecting all cells takes too long
		assertEquals("canvas cells should be white initially", 255, geom.getDistribution()[4512]);
	}

	/**
	 * tests whether the drawing area is large enough
	 */
	@Test
	public void testIsInsideDrawingArea(){
		initialise();
		assertTrue("coordinates (1, 1) should be inside the canvas",geom.isInsideDrawingArea(1, 1));
		assertTrue("coordinates (1, height) should be inside the canvas",geom.isInsideDrawingArea(1, geom.getHeight()));
		assertTrue("coordinates (width, 1) should be inside the canvas",geom.isInsideDrawingArea(geom.getWidth(), 1));
		assertTrue("coordinates (width, height) should be inside the canvas",geom.isInsideDrawingArea(geom.getWidth(), geom.getHeight()));
	}
	
	/**
	 * tests if the canvas correctly detects when the cursor is not in the drawing area
	 */
	@Test
	public void testIsOutsideDrawingArea(){
		initialise();
		assertFalse("coordinates (0, 1) should be outside the canvas",geom.isInsideDrawingArea(0, 1));
		assertFalse("coordinates (1, height+1) should be outside the canvas",geom.isInsideDrawingArea(1, geom.getHeight()+1));
		assertFalse("coordinates (width, 0) should be outside the canvas",geom.isInsideDrawingArea(geom.getWidth(), 0));
		assertFalse("coordinates (width+1, height) should be outside the canvas",geom.isInsideDrawingArea(geom.getWidth()+1, geom.getHeight()));
	}
	
	/**
	 * tests if the geometry correctly detects when the cursor is in the top wall area
	 */
	@Test
	public void testIsInsideTopWall(){
		initialise();
		assertTrue("coordinates (1, -1) should be inside the top wall",geom.isInsideTopWall(1, -1));
		assertTrue("coordinates (1, 1-wallheight) should be inside the top wall",geom.isInsideTopWall(1, 1-geom.HEIGHT_OF_WALL));
		assertTrue("coordinates (width, -1) should be inside the top wall",geom.isInsideTopWall(geom.getWidth(), -1));
		assertTrue("coordinates (width, 1-wallheight) should be inside the top wall",geom.isInsideTopWall(geom.getWidth(), 1-geom.HEIGHT_OF_WALL));
	}
	
	/**
	 * tests if the geometry correctly detects when the cursor is in the bottom wall area
	 */
	@Test
	public void testIsInsideBottomWall(){
		initialise();
		assertTrue("coordinates (1, height+1) should be inside the bottom wall",geom.isInsideBottomWall(1, geom.getHeight()+1));
		assertTrue("coordinates (1, height+wallheight-1) should be inside the bottom wall",geom.isInsideBottomWall(1, geom.getHeight()+geom.HEIGHT_OF_WALL-1));
		assertTrue("coordinates (width, height+1) should be inside the bottom wall",geom.isInsideBottomWall(geom.getWidth(), geom.getHeight()+1));
		assertTrue("coordinates (width, height+wallheight-1) should be inside the bottom wall",geom.isInsideBottomWall(geom.getWidth(), geom.getHeight()+geom.HEIGHT_OF_WALL-1));
	}
	
	//-----Integration tests---------------------------------------------------
	
	/**
	 * tests if rectangleGeometry can recognise mixing steps properly
	 */
	@Test
	public void testMixingSteps(){
		initialise();
		int Xl = 1;
		int Xr = geom.getWidth();
		// geom.isInsideWall(x, y);
	}

//	/**
//	 * 
//	 * @param message
//	 * @param top
//	 * @param clockwise
//	 * @param stepSize
//	 * @return
//	 */
//	private StepAddedListener setUpStepAddedListener(final String message,
//			final boolean top, final boolean clockwise, final double stepSize) {
//		StepAddedListener stl = new StepAddedListener() {
//			@Override
//			public void onStepAdded(MixingStep step) {
//				RectangleMixingStep step = (RectangleMixingStep) newStep;
//				step.setStepSize(stepSize);
//				listenerFired  = true;
//				assertEquals(message + " is top", top, step.isTopWall());
//				assertEquals(message + " is clockwise", clockwise,
//						step.isClockwise());
//				assertEquals(message + " has stepsize " + stepSize, stepSize,
//						step.getStepSize());
//			}
//		};
//
//		return stl;
//	}
	
	/**
	 * Returns the module name for the GWT test.
	 */
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
