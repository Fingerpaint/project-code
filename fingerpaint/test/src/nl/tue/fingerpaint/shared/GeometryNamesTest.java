package nl.tue.fingerpaint.shared;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * Test class to test if geometryNames correctly matches long lames with short names
 * 
 * @author Group Fingerpaint
 */
public class GeometryNamesTest extends GWTTestCase {

	/**
	 * Tests if getShortName returns the correct short name
	 */
	@Test
	public void testGetShort() {
		//test known longs
		assertEquals(GeometryNames.getShortName(GeometryNames.CIRC_LONG),GeometryNames.CIRC_SHORT);
		assertEquals(GeometryNames.getShortName(GeometryNames.RECT_LONG),GeometryNames.RECT_SHORT);
		assertEquals(GeometryNames.getShortName(GeometryNames.SQR_LONG),GeometryNames.SQR_SHORT);
		assertEquals(GeometryNames.getShortName(GeometryNames.JOBE_LONG),GeometryNames.JOBE_SHORT);
		//test an unknown long
		assertEquals("getShortName should return null for an unknown string",
				GeometryNames.getShortName("This is a long, I am sure"), null);
	}
	
	/**
	 * Tests if getLongName returns the correct long name
	 */
	@Test
	public void testGetLong() {
		//test known shorts
		assertEquals(GeometryNames.getLongName(GeometryNames.CIRC_SHORT),GeometryNames.CIRC_LONG);
		assertEquals(GeometryNames.getLongName(GeometryNames.RECT_SHORT),GeometryNames.RECT_LONG);
		assertEquals(GeometryNames.getLongName(GeometryNames.SQR_SHORT),GeometryNames.SQR_LONG);
		assertEquals(GeometryNames.getLongName(GeometryNames.JOBE_SHORT),GeometryNames.JOBE_LONG);
		//test an unknown short
		assertEquals("getLongName should return null for an unknown string",
				GeometryNames.getShortName("This is short"), null);
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}
}
