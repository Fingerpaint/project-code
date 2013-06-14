package nl.tue.fingerpaint.client.model;

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
	
	/**
	 * Initialises the geometry for a new test case
	 */
	public void  initialise(){
		geom = new RectangleGeometry(testClientHeight, testClientWidth, 240, 400);
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
		
		//test all four corner cases
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
		
		//test all four sides
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
		
		//test all four corner cases
		assertTrue("coordinates (1, -1) should be inside the top wall",geom.isInsideTopWall(1, -1));
		assertTrue("coordinates (1, 1-wallheight) should be inside the top wall",geom.isInsideTopWall(1, 1-geom.HEIGHT_OF_WALL));
		assertTrue("coordinates (width-1, -1) should be inside the top wall",geom.isInsideTopWall(geom.getWidth()-1, -1));
		assertTrue("coordinates (width-1, 1-wallheight) should be inside the top wall",geom.isInsideTopWall(geom.getWidth()-1, 1-geom.HEIGHT_OF_WALL));
	}
	
	/**
	 * tests if the geometry correctly detects when the cursor is in the bottom wall area
	 */
	@Test
	public void testIsInsideBottomWall(){
		initialise();
		
		//test all four corner cases
		assertTrue("coordinates (1, height+1) should be inside the bottom wall",geom.isInsideBottomWall(1, geom.getHeight()+1));
		assertTrue("coordinates (1, height+wallheight-1) should be inside the bottom wall",geom.isInsideBottomWall(1, geom.getHeight()+geom.HEIGHT_OF_WALL-1));
		assertTrue("coordinates (width-1, height+1) should be inside the bottom wall",geom.isInsideBottomWall(geom.getWidth()-1, geom.getHeight()+1));
		assertTrue("coordinates (width-1, height+wallheight-1) should be inside the bottom wall",geom.isInsideBottomWall(geom.getWidth()-1, geom.getHeight()+geom.HEIGHT_OF_WALL-1));
	}
	
	//-----Integration tests---------------------------------------------------
	
	/**
	 * tests if rectangleGeometry can recognise mixing steps properly
	 */
	@Test
	public void testMixingSteps(){
		initialise();
		
		//these four values represent four points
		int Xl = 1;
		int Xr = geom.getWidth()-1;
		int Yt = -1;
		int Yb = geom.getHeight()+1;
		
		//check if the four points used for testing are in the correct wall
		assertTrue("(" + Xl + ", " + Yt + ") should be in the top wall",geom.isInsideTopWall(Xl, Yt));
		assertTrue("(" + Xr + ", " + Yt + ") should be in the top wall",geom.isInsideTopWall(Xr, Yt));
		assertTrue("(" + Xl + ", " + Yb + ") should be in the bottom wall",geom.isInsideBottomWall(Xl, Yb));
		assertTrue("(" + Xr + ", " + Yb + ") should be in the bottom wall",geom.isInsideBottomWall(Xr, Yb));
		
		// test mixing steps for each combination of top/bottom wall and clockwise/counterclockwise
		testStep(true, false);
		testStep(false, true);
		testStep(true, true);
		testStep(false, false);
	}
	
	/**
	 * Helper method to test an individual mixing step, 
	 * tests if geometry correctly sets topWall and clockwise for its mixing steps
	 * 
	 * @param topWall true if a top wall mixing step should be tested, false for a bottom wall step
	 * @param clockwise true if the mixing movement should be clockwise, false otherwise
	 */
	protected void testStep(boolean topWall, boolean clockwise){
		initialise();
		int x1; //the x-coordinate of the dragStart
		int x2; //the x-coordinate of the dragEnd
		int y;
		if(topWall){
			y = -1;
		}else{
			y = geom.getHeight()+1;
		}
		if(topWall ^ clockwise){ //a movement to the left
			x1 = geom.getWidth()-1;
			x2 = 1;
		}else{ //a movement to the right
			x1 = 1;
			x2 = geom.getWidth()-1;
		}
		//now input the wall movement in RectangleGeometry
		//and retrieve the resultant MixingStep
		geom.startDefineMixingStep(x1, y);
		RectangleMixingStep testStep = (RectangleMixingStep) geom.determineSwipe(x2, y);
		
		assertEquals("topWall should be " + topWall + " for this test, clockwise was " + clockwise,topWall, testStep.isTopWall());
		assertEquals("clockwise should be " + clockwise + " for this test, topWall was " + topWall, clockwise, testStep.isClockwise());
	}
	
	/**
	 * Returns the module name for the GWT test.
	 */
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
